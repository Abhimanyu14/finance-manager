# Finance Manager

A simple finance manager app

## Features

1. Transactions - Create, Read, Update and Delete (CRUD)
2. Sources - CRUD
3. Categories - CRUD
4. TransactionFor - CRUD
5. Overview
6. Local Data Backup
7. Scan & Pay - Experimental

## Database

### Tables

1. Transactions
2. Sources
3. Categories - Income, Expense and Investment
4. TransactionFor

### Data Structure

**Amount**

- value: Double = 0.0
- unit: Unit = Unit.INR

**Category**

- id: Int
- title: String
- description: String = ""
- children: List<Category>? = null

**MyTransaction**

- id: Int
- title: String = ""
- description: String = ""
- amount: Amount
- category: Category
- creationTimestamp: Timestamp
- transactionTimestamp: Timestamp

**Source**

- id: Int
- id: Int
- name: String
- type: SourceType = SourceType.CASH
- balanceAmount: Amount

**SourceType**

- enum
  - CASH
  - BANK
  - E_WALLET

**Timestamp**

- date: String
- time: String

**Unit**

- enum
  - INR
  - USD

---

## Unit Testing

**To generate Kover Unit test coverage HTML Report**
`./gradlew koverMergedHtmlReport`

---

**Commonly Used Commands**

1. Screen mirroring

`scrcpy -m1024`

2. Clean build

`./gradlew clean :app:assembleDebug`

3. Unit Test

`./gradlew testDebugUnitTest `
