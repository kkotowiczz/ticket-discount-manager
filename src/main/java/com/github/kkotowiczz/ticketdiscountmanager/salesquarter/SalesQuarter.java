package com.github.kkotowiczz.ticketdiscountmanager.salesquarter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;

@Entity
public class SalesQuarter {
  static Map<String, Long> FIRST_MONTH_OF_QUARTER = Map.of(
    "JANUARY", 1L,
    "APRIL", 2L,
    "JULY", 3L,
    "OCTOBER", 4L
  );

  @Id
  private Long quarterNumber;
  private Long numberOfTickets;

  public SalesQuarter() {}

  public void setQuarterNumber(Long quarterNumber) {
    if (quarterNumber < 1 || quarterNumber > 4) throw new IllegalArgumentException("Quarter must be in 1 - 4 range");
    this.quarterNumber = quarterNumber;
  }

  public void setNumberOfTickets(Long numberOfTickets) {
    this.numberOfTickets = numberOfTickets;
  }

  public Long getQuarterNumber() {
    return quarterNumber;
  }

  public Long getNumberOfTickets() {
    return numberOfTickets;
  }
}
