# TM0723

## Summary
Redacted

Assumptions:
1. The user happens to be a programmer, so they run the application via the instructions below.
2. You run with the arguments in the correct order:
```
args[0] the toolCode
args[1] the rental day count
args[2] the discount percent as a whole number
args[3] the checkout date in M/d/uu format
```
3. Used in constant, same locale


### Build Instructions
```shell
./gradlew build
```

### Run Instructions
```shell
./gradlew run --args="JAKR 5 10 9/3/15" 
```

### Test Instructions
To run defined scenarios
```shell
./gradlew clean test --tests 'CheckoutEndToEndTests'
```

To run all tests
```shell
./gradlew clean test
```

#### Defined test scenarios checker
| Scenario            | 1                        | 2      | 3        | 4          | 5          | 6          |
|---------------------|--------------------------|--------|----------|------------|------------|------------|
| Tool Code           | JAKR                     | LADW   | CHNS     | JAKD       | JAKR       | JAKR       |
| Checkout Date       | 9/3/15                   | 7/2/20 | 7/2/15   | 9/3/15     | 7/2/15     | 7/2/20     |
| Rental Days         | 5                        | 3      | 5        | 6          | 9          | 4          |
| Discount            | 101%                     | 10%    | 25%      | 0%         | 0%         | 50%        |
|                     |                          |        |          |            |            |            |
| Tool Code           | Print discount exception | LADW   | CHNS     | JAKD       | JAKR       | JAKR       |
| Tool Type           |                          | Ladder | Chainsaw | Jackhammer | Jackhammer | Jackhammer |
| Tool Brand          |                          | Werner | Stihl    | DeWalt     | Ridgid     | Ridgid     |
| Rental Days         |                          | 3      | 5        | 6          | 9          | 4          |
| Check out date      |                          | 7/2/20 | 7/2/15   | 9/3/15     | 7/2/15     | 7/2/20     |
| Due date            |                          | 7/4/20 | 7/6/15   | 9/8/15     | 7/10/15    | 7/5/20     |
| Daily Rental Charge |                          | $1.99  | $1.49    | $2.99      | $2.99      | $2.99      |
| Number of weekdays  |                          | 1      | 2        | 3          | 6          | 1          |
| Number of weekends  |                          | 1      | 2        | 2          | 2          | 2          |
| Number of holidays  |                          | 1      | 1        | 1          | 1          | 1          |
| Charged weekday?    |                          | 1      | 1        | 1          | 1          | 1          |
| Charged weekend?    |                          | 1      | 0        | 0          | 0          | 0          |
| Charged holiday?    |                          | 0      | 1        | 0          | 0          | 0          |
| Charge days         |                          | 2      | 3        | 3          | 6          | 1          |
| Pre-discount charge |                          | $3.98  | $4.47    | $8.97      | $17.94     | $2.99      |
| Discount percent    |                          | 10%    | 25%      | 0%         | 0%         | 50%        |
| Discount amount     |                          | $0.40  | $1.12    | $0.00      | $0.00      | $1.50      |
| Final charge        |                          | $3.58  | $3.35    | $8.97      | $17.94     | $1.49      |
