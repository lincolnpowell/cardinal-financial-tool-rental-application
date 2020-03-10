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

## Issues
| Issue # | Status | Description|
|:---:|:---:|---|
| 1 | `Open` | Invalid tool code validation missing: Results in NullPointerException |
| 2 | `Open` | Invalid checkout date validation missing: Could result in thrown unchecked exception |