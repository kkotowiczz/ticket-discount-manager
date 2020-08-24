CREATE TABLE sales_quarter (
    quarter_number integer PRIMARY KEY CHECK (quarter_number >= 1) CHECK (quarter_number <= 4),
    amount_of_tickets integer not null
);