package com.github.kkotowiczz.ticketdiscountmanager.salesquarter;

import java.time.LocalDate;

public class SalesQuarterCreatorDTO {
  private Long numberOfTickets;
  private final LocalDate timestamp = LocalDate.now();

  public SalesQuarterCreatorDTO() {};

  public SalesQuarterCreatorDTO(Long numberOfTickets) {
    this.numberOfTickets = numberOfTickets;
  }

  public Long getNumberOfTickets() {
    return numberOfTickets;
  }

  public void setNumberOfTickets(Long numberOfTickets) {
    this.numberOfTickets = numberOfTickets;
  }

  public LocalDate getTimestamp() {
    return timestamp;
  }

}
