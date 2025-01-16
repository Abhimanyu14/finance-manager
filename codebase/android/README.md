# Terminology

## Current Transaction

### In View Transaction Screen

- The transaction being viewed is called "current transaction".

### In Add Transaction Screen

- NA

## Original Transaction

### In View Transaction Screen

- If the current transaction is a refund transaction,
  then the transaction which was refunded is called "original transaction".

### In Add Transaction Screen

- If the current transaction is a refund transaction, the
  transaction which was refunded is called "original transaction".

## Refund Transactions

### In View Transaction Screen

- If the current transaction is an expense transaction and it has some refund transactions,
  then the transactions which were refunded are called "refund transactions".

## Current Account

### In Edit Account Screen

- The account being edited

# Responsibilities

## DAO

- Handles data from and to database.
- All atomic operations should be done at this layer.

## Repository

- Handles mapping data entities to domain layer model classes.
- Handles dispatcher switching to make all operations in lower above layers thread safe.

## Use-Cases
- 

## ViewModel
- 

## Screen Composable
- 

## Screen UI Composable
- 

## Component Composable
- 

# Ordering

## ViewModel constructor

Each group should be internally ordered in alphabetical order according to the data type, and then
according to the name.

1. Local parameters.
2. Private properties
3. Internal properties
4. Public properties
