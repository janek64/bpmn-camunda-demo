-- Create the table for storing customers
DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
    customer_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name text NOT NULL UNIQUE
);

-- Create the table for options
DROP TABLE IF EXISTS option;
CREATE TABLE option (
    option_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name text NOT NULL UNIQUE,
    duration_hours integer NOT NULL,
    price_cent integer NOT NULL
);

-- Create the table for booking options
DROP TABLE IF EXISTS customer_books_option;
CREATE TABLE customer_books_option (
    customer_id uuid NOT NULL REFERENCES customer(customer_id),
    option_id uuid NOT NULL REFERENCES option(option_id),
    start_time timestamp NOT NULL,
    end_time timestamp NOT NULL,
    -- start_time is part of the primary key to allow booking the same option
    -- repeatedly - validation that no current booking exists needs to be done
    -- by the application
    PRIMARY KEY (customer_id, option_id, start_time)
);