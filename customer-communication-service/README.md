# Customer Communication Service
This service manages the notifications created for customers in the option-booking process. It implements the Camunda job workers for all notification-related job types:

* `create-invalid-voucher-notification`: Creates a new failure notification regarding the usage of an invalid voucher.
* `create-insufficient-credit-notification`: Creates a new failure notification regarding an insufficient balance for booking an option.
* `create-successful-booking-notification`: Creates a new success notification regarding an option booking.

The application is implemented in Java 21, using the [Quarkus](https://quarkus.io) framework. It stores the customer and notification data in a [PostgreSQL](https://www.postgresql.org/) database.

## Running the application
To start the application in development mode, ensure that Zeebe and the PostgreSQL database are available and execute the following command:

```shell
./mvnw quarkus:dev
```

The application will automatically create the required database schema as well as test data and will register its job worker implementations with Zeebe.

Once the application is running, you can visit its web pages to inspect the data:
* [http://localhost:7002/customers](http://localhost:7002/customers): Overview of all customers. From this page, you can navigate to the list of notifications for each customer.

## Building the Docker image
To build the JVM docker image of the application, execute the following commands:

```shell
./mvnw clean package
docker build -t customer-communication-service -f src/main/docker/Dockerfile.jvm .
```