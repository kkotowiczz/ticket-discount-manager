package com.github.kkotowiczz.ticketdiscountmanager.salesquarter;

import java.time.LocalDate;

public class SalesQuarterCreatorDTO {
  private Long amountOfTickets;
  private final LocalDate timestamp = LocalDate.now();

  public SalesQuarterCreatorDTO() {}

  public SalesQuarterCreatorDTO(Long amountOfTickets) {
    this.amountOfTickets = amountOfTickets;
  }

  public Long getAmountOfTickets() {
    return amountOfTickets;
  }

  public void setAmountOfTickets(Long amountOfTickets) {
    this.amountOfTickets = amountOfTickets;
  }

  public LocalDate getTimestamp() {
    return timestamp;
  }

}
