package com.github.kkotowiczz.ticketdiscountmanager.salesquarter;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;


@RestController("/discount-tickets")
public class SalesQuarterController {
  private final SalesQuarterService service;

  public SalesQuarterController(SalesQuarterService service) {
    this.service = service;
  }

  @PostMapping("/")
  public ResponseEntity<?> createSaleQuarter(@RequestBody SalesQuarterCreatorDTO dto) {
    service.createSalesQuarter(dto);
    return ResponseEntity.created(URI.create(dto.getTimestamp().toString())).build();
  }

  @GetMapping("/")
  public ResponseEntity<?> getSalesQuarter(@RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    return ResponseEntity.ok(service.getTicketsByWeek(date));
  }

  @PutMapping("/")
  public ResponseEntity<?> adjustNumberOfTickets(@RequestParam(name = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                 @RequestParam Long amount) {
    service.adjustTicketAmount(date, amount);
    return ResponseEntity.ok("Quarter has been edited");
  }
}
