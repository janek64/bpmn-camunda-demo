# Option Booking Service
This service manages the catalogue of options used in the option-booking process as well as the bookings of options by customers created by it. It implements the Camunda job workers for all option-booking-related job types:

* `get-option-details`: Returns all details about a specific option, including name, price and duration.
* `book-option`: Creates a new option booking entry for a customer and an option.

The application is implemented in Java 21, using the [Quarkus](https://quarkus.io) framework. It stores the option and booking data in a [PostgreSQL](https://www.postgresql.org/) database.

## Running the application
To start the application in development mode, ensure that Zeebe and the PostgreSQL database are available and execute the following command:

```shell
./mvnw quarkus:dev
```

The application will automatically create the required database schema as well as test data and will register its job worker implementations with Zeebe.

Once the application is running, you can visit its web pages to inspect the data:
* [http://localhost:7000/options](http://localhost:7000/options): Overview of all available options.
* [http://localhost:7000/bookings](http://localhost:7000/bookings): Overview of all option bookings by customers.

## Building the Docker image
To build the JVM docker image of the application, execute the following commands:

```shell
./mvnw clean package
docker build -t option-booking-service -f src/main/docker/Dockerfile.jvm .
```