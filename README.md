# Finance Manager

A simple finance manager app

# Features

1. Transaction - CRUD
2. Source - CRUD
3. Category - CRUD
4. Statistics

# Database

## Tables

1. Sources
2. Transactions
3. Income
4. Expense

## Data Structure

### Amount

- value: Double = 0.0
- unit: Unit = Unit.INR

### Category

- id: Int
- title: String
- description: String = ""
- children: List<Category>? = null

### MyTransaction

- id: Int
- title: String = ""
- description: String = ""
- amount: Amount
- category: Category
- creationTimestamp: Timestamp
- transactionTimestamp: Timestamp

### Source

- id: Int
- id: Int
- name: String
- type: SourceType = SourceType.CASH
- balanceAmount: Amount

### SourceType

- enum
  - CASH
  - BANK
  - E_WALLET

### Timestamp

- date: String
- time: String

### Unit

- enum
  - INR
  - USD

---
