package com.github.kkotowiczz.ticketdiscountmanager.salesquarter;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

@Service
public class SalesQuarterService {
  private final SalesQuarterRepository salesQuarterRepository;

  public SalesQuarterService(SalesQuarterRepository salesQuarterRepository) {
    this.salesQuarterRepository = salesQuarterRepository;
  }

  public void createSalesQuarter(SalesQuarterCreatorDTO salesQuarterCreatorDTO) {
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


  private long countNumberOfWeeks(SalesQuarterCreatorDTO salesQuarterCreatorDTO) {
    var firstMonthOfCurrentQuarter = salesQuarterCreatorDTO.getTimestamp().getMonth().firstMonthOfQuarter().getValue();
    var year = salesQuarterCreatorDTO.getTimestamp().getYear();
    var firstDayOfQuarter = LocalDate.of(year, firstMonthOfCurrentQuarter, 1);
    var lastDayOfQuarter = firstDayOfQuarter.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
    return ChronoUnit.WEEKS.between(firstDayOfQuarter, lastDayOfQuarter);
  }
}
