-- Create the table for storing customers
DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
    customer_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name text NOT NULL UNIQUE
);

-- Create the table for storing notifications
DROP TABLE IF EXISTS notification;
CREATE TABLE notification (
    notification_id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    notification_type text NOT NULL,
    title text NOT NULL,
    message text NOT NULL,
    creation_timestamp timestamp NOT NULL,
    customer_id uuid NOT NULL REFERENCES customer(customer_id)
);