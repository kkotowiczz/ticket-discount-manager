package com.github.kkotowiczz.ticketdiscountmanager.salesquarter;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

@Service
public class SalesQuarterService {
  private final SalesQuarterRepository salesQuarterRepository;

  public SalesQuarterService(SalesQuarterRepository salesQuarterRepository) {
    this.salesQuarterRepository = salesQuarterRepository;
  }

  void createSalesQuarter(SalesQuarterCreatorDTO salesQuarterCreatorDTO) {
    var quarter = SalesQuarter.FIRST_MONTH_OF_QUARTER.get(salesQuarterCreatorDTO.getTimestamp().getMonth().firstMonthOfQuarter().toString());
    var optional = salesQuarterRepository.findById(quarter);
    if(optional.isPresent()) {
      throw new RuntimeException("Quarter already exists");
    }
    var salesQuarter = new SalesQuarter();
    salesQuarter.setQuarterNumber(quarter);
    salesQuarter.setNumberOfTickets(salesQuarterCreatorDTO.getNumberOfTickets());
    salesQuarterRepository.save(salesQuarter);
  }

  Map<String, Long> getTicketsByWeek(LocalDate date) {
    var quarter = SalesQuarter.FIRST_MONTH_OF_QUARTER.get(date.getMonth().firstMonthOfQuarter().toString());
    var numberOfWeeks = countNumberOfWeeks(date);
    var salesQuarter = salesQuarterRepository.findById(quarter);
    return divideNumberOfTicketsByWeeks(numberOfWeeks, salesQuarter.get().getNumberOfTickets());
  }

  private long countNumberOfWeeks(LocalDate date) {
    var firstMonthOfCurrentQuarter = date.getMonth().firstMonthOfQuarter().getValue();
    var year = date.getYear();
    var firstDayOfQuarter = LocalDate.of(year, firstMonthOfCurrentQuarter, 1);
    var lastDayOfQuarter = firstDayOfQuarter.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
    return ChronoUnit.WEEKS.between(firstDayOfQuarter, lastDayOfQuarter);
  }

  private Map<String, Long> divideNumberOfTicketsByWeeks(Long numberOfWeeks, Long ticketsQuantity) {
    if(ticketsQuantity == 0) throw new IllegalArgumentException("Tickets Quantity must be more than 0");

    var equalNumberOfTickets = ticketsQuantity / numberOfWeeks;
    var restOfTickets = ticketsQuantity % numberOfWeeks;

    var ticketsByWeekNumber =  new TreeMap<String, Long>((weekOne, weekTwo) -> {
      var numberOne = Integer.parseInt(weekOne.replaceAll("\\D+",""));
      var numberTwo = Integer.parseInt(weekTwo.replaceAll("\\D+",""));
      return numberOne - numberTwo;
    });

    for(int i = 1; i <= numberOfWeeks; i++) {
      ticketsByWeekNumber.put("week" + i, equalNumberOfTickets);
    }

    for(int i = 1; i <= restOfTickets; i++) {
      ticketsByWeekNumber.replace("week" + i, ticketsByWeekNumber.get("week" + i) + 1);
    }

    return ticketsByWeekNumber;
  }

  void adjustTicketAmount(LocalDate date, Long amount) {
    var quarterOptional = salesQuarterRepository.findById(SalesQuarter.FIRST_MONTH_OF_QUARTER.get(date.getMonth().firstMonthOfQuarter().toString()));
    if(quarterOptional.isPresent()) {
      var quarter = quarterOptional.get();

      if(quarter.getNumberOfTickets() + amount > 0) {
        quarter.setNumberOfTickets(quarter.getNumberOfTickets() + amount);
      } else {
       quarter.setNumberOfTickets(0L);
      }

      salesQuarterRepository.save(quarter);
    }
  }
}
