package com.github.kkotowiczz.ticketdiscountmanager.salesquarter;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesQuarterRepository extends CrudRepository<SalesQuarter, Long> {
}
