# Tool Rental Application

## Objective
To demonstrate the coding and testing of a simple tool rental application.

## Description
The Tool Rental application is a point-of-sale tool for a store, like Home Depot, that rents big tools.

## Build Instructions
### General Requirements
* Git

### Preliminary Instructions
1. Clone project
    ```
    git clone https://github.com/lincolnpowell/cardinal-financial-tool-rental-application.git
    ```
1. Navigate to project root
1. Continue to **Local Containerized Build** or **Local Maven Build** below

---

### Local Containerized Build - _Recommended_
#### Requirements
* [Docker Desktop](https://www.docker.com/products/docker-desktop)

#### Instructions
1. Build Docker image
    ```
    docker image build -t tool-rental-application -f ./docker/Dockerfile .
    ```
1. Run Docker container
    ```
    docker container run --rm -it -v "$(pwd)"/outputs:/outputs tool-rental-application bash
    ```
   **NOTE**: This project provides an /outputs directory used for generated artifacts such as logs.

---

### Local Maven Build
#### Requirements
* Maven 3.6+
* Java JDK 8+

#### Instructions
1. Execute Maven package phase
    ```
    mvn clean package
    ```
1. Execute generated JAR file
    ```
    java -jar ./target/tool-rental-application.jar
    ```

## User Guide
Upon program execution, a menu will be displayed prompting user input:
```
Please select one of the options:

1. Enter new rental agreement
0. Exit program

Selection:
```

* Press 1 to enter a new rental agreement
* Press 0 to exit the program
* Any other input will prompt an "Invalid input" error, redisplay the menu and prompt user input

Pressing 1 will prompt user with the following necessary information for checkout:
* Tool code
* Number of rental days
* Discount percent
* Checkout date
```
Selection: 1

Enter tool code: jakr

Enter number of rental days: 15

Enter discount percent: 30

Enter checkout date: 7/1/15
```

**NOTE**: The number of rental days **MUST** be 1 or greater.

**NOTE**: The discount percent value **MUST** be in the range of 0 to 100.

Upon successful submission, a generated Rental Agreement will be printed:
```
Tool code: JAKR

Tool type: Jackhammer

Tool brand: Ridgid

Rental days: 15

Check out date: 07/01/15

Due date: 07/16/15

Daily rental charge: $2.99

Charge days: 11

Pre-discount charge: $32.89

Discount percent: 30%

Discount amount: $9.87

Final charge: $23.02
```

## Project Requirements
### Initial (_Phase 1_)
#### Overview
Implement a Java solution to the following specification and a JUnit test suite with tests as specified.  The demonstration does not require a user interface.

#### Specification
The demonstration is to code and test a simple tool rental application.
* The application is a point-of-sale tool for a store, like Home Depot, that rents big tools.
* Customers rent a tool for a specified number of days.
* When a customer checks out a tool, a Rental Agreement is produced.
* The store charges a daily rental fee, whose amount is different for each tool type.
* Some tools are free of charge on weekends or holidays.
* Clerks may give customers a discount that is applied to the total daily charges to reduce the final charge.

##### Tools
The tools available for rental are as follows:

| Tool Type | Brand | Tool Code |
|---|---|---|
| Ladder | Werner | LADW |
| Chainsaw | Stihl | CHNS |
| Jackhammer | Ridgid | JAKR |
| Jackhammer | DeWalt | JAKD |

Each tool instance has the following attributes:
* Tool Code - Unique identifier for a tool instance
* Brand - The brand of the ladder, chain saw or jackhammer
* Tool Type - The type of tool

The Tool Type specifies the daily rental charge and the days for which the daily rental charge applies:

| Tool Type | Daily Charge | Weekday Charge | Weekend Charge | Holiday Charge |
|---|---|:---:|:---:|:---:|
| Ladder | $1.99 | X | X | |
| Chainsaw | $1.49 | X | | X |
| Jackhammer | $2.99 | X | | |

##### Holidays
There are only two (2) holidays in the calendar:
* Independence Day, July 4th - If falls on weekend, it is observed on the closest weekday (if Saturday, then Firday before; else, if Sunday, then Monday after)
* Labor Day - First Monday in September

##### Checkout
Checkout requires the following information to be provided:
* Tool code - See tool table above
* Rental day count - The number of days for which the customer wants to rent the tool. (e.g. 4 days)
* Discount percent - As a whole number, 0-100 (e.g. 20 = 20%)
* Check out date 

Checkout should throw an exception with an instructive, user-friendly message if
* Rental day count is not 1 or greater
* Discount percent is not in the range 0-100

Checkout generates a Rental Agreement instance with the following values.
* Tool code - Specified at checkout
* Tool type - From tool info
* Tool brand - From tool info
* Rental days - Specified at checkout
* Check out date  - Specified at checkout
* Due date - Calculated from checkout date and rental days
* Daily rental charge - Amount per day, specified by the tool type
* Charge days - Count of chargeable days, from day after checkout through and including due date, excluding “no charge” days as specified by the tool type
* Pre-discount charge - Calculated as charge days X daily charge (resulting total rounded half up to cents)
* Discount percent - Specified at checkout
* Discount amount - calculated from discount % and pre-discount charge (resulting amount rounded half up to cents)
* Final charge - Calculated as pre-discount charge - discount amount

Rental Agreement should include a method that can print the above values as text to the console like this:
```
Tool code: LADW  

Tool type: Ladder

…

Final charge: $9.99
```
with formatting as follows:

* Dates mm/dd/yy
* Currency $9,999.99
* Percents 99%

##### Tests
Your code must include JUnit tests to prove your solution is correct.

The proof should include the following scenarios:

| Checkout Terms | Test 1 | Test 2 | Test 3 | Test 4 | Test 5 | Test 6 |
|---|---|---|---|---|---|---|
| Tool Code | JAKR | LADW | CHNS | JAKD | JAKR | JAKR |
| Checkout Date | 9/3/15 | 7/2/20 | 7/2/15 | 9/3/15 | 7/2/15 | 7/2/20 |
| Rental Days | 5 | 3 | 5 | 6 | 9 | 4 |
| Discount | 101% | 10% | 25% | 0% | 0% | 50% |

## Issues
| Issue # | Status | Description|
|:---:|:---:|---|
| 1 | `Open` | Invalid tool code validation missing: Results in NullPointerException |
| 2 | `Open` | Invalid checkout date validation missing: Could result in thrown unchecked exception |