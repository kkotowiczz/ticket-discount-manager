package com.github.kkotowiczz.ticketdiscountmanager.salesquarter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;


@RestController
public class SalesQuarterController {
  private final SalesQuarterService service;

  public SalesQuarterController(SalesQuarterService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<?> createSaleQuarter(@RequestBody SalesQuarterCreatorDTO dto) {
    service.createSalesQuarter(dto);
    return ResponseEntity.created(URI.create(dto.getTimestamp().toString())).build();
  }

  @GetMapping
  public ResponseEntity<?> getSalesQuarter(@RequestParam LocalDate date) {
    // TODO implement
    return ResponseEntity.ok("ok");
  }
}
