-- Create the table for storing customers and their balance
DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
    customer_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name text NOT NULL UNIQUE,
    balance_cent bigint NOT NULL
);

-- Create the table for storing vouchers
DROP TABLE IF EXISTS voucher;
CREATE TABLE voucher (
    voucher_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    code text UNIQUE NOT NULL,
    -- ID of the customer who redeemed the voucher
    redeem_customer_id uuid NULL REFERENCES customer(customer_id),
    redeem_time timestamp NULL
);