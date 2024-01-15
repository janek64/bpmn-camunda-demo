# Payment Service
This service manages the balances of customers and the vouchers used in the option-booking process. It implements the Camunda job workers for all payment-related job types:

* `check-voucher-validity`: Verifies that a voucher with a provided code exists and has not been redeemed yet.
* `redeem-voucher`: Redeems a voucher for a customer.
* `check-customer-balance`: Verifies that a customer has enough balance to pay for a given option price.
* `deduct-price-from-balance`: Deducts the price of an option from the balance of a customer.

The application is implemented in Java 21, using the [Quarkus](https://quarkus.io) framework. It stores the customer and voucher data in a [PostgreSQL](https://www.postgresql.org/) database.

## Running the application
To start the application in development mode, ensure that Zeebe and the PostgreSQL database are available and execute the following command:

```shell
./mvnw quarkus:dev
```

The application will automatically create the required database schema as well as test data and will register its job worker implementations with Zeebe.

Once the application is running, you can visit its web pages to inspect the data:
* [http://localhost:7001/customers](http://localhost:7001/customers): Overview of all customers and their balance.
* [http://localhost:7001/vouchers](http://localhost:7001/vouchers): Overview of all vouchers and their redeem status.

## Building the Docker image
To build the JVM docker image of the application, execute the following commands:

```shell
./mvnw clean package
docker build -t payment-service -f src/main/docker/Dockerfile.jvm .
```