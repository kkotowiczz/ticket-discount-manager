package com.github.kkotowiczz.ticketdiscountmanager.salesquarter;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.Map;


@RestController
@RequestMapping("tickets")
public class SalesQuarterController {
  private final SalesQuarterService service;

  public SalesQuarterController(SalesQuarterService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<String> createSaleQuarter(@RequestBody SalesQuarterCreatorDTO dto) {
    var optional = service.createSalesQuarter(dto);
    String responseBodyString = optional.isEmpty() ? "has been created" : optional.map(quarter -> quarter.getQuarterNumber() + " already exists").get();
    return optional.isEmpty() ? ResponseEntity.ok("Sales quarter " + responseBodyString) : ResponseEntity.badRequest().body("Sales quarter " + responseBodyString);

  }

  @GetMapping
  public ResponseEntity<Map<String, Long>> getSalesQuarter(@RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    return ResponseEntity.ok(service.getTicketsByWeek(date));
  }

  @PutMapping
  public ResponseEntity<String> adjustNumberOfTickets(@RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                 @RequestParam Long amount) {
    var quarterOptional = service.adjustTicketAmount(date, amount);
    var quarterBodyString = quarterOptional
      .map(quarter -> String.format("%d has been edited with total %d of tickets", quarter.getQuarterNumber(), quarter.getAmountOfTickets()))
      .orElse("does not exists");

    return quarterOptional.isPresent() ? ResponseEntity.ok("Sales Quarter " + quarterBodyString) : ResponseEntity.badRequest().body("Sales Quarter " + quarterBodyString);
  }
}
