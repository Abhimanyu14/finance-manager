# Finance Manager

A simple finance manager app

## Features

1. Transactions - Create, Read, Update and Delete (CRUD)
2. Sources - CRUD
3. Categories - CRUD
4. TransactionFor - CRUD
5. Overview
6. Export to JSON
7. Import from JSON

## Tech Stack

1. Programming Language - Kotlin
2. UI - Jetpack Compose
3. Dependency Injection - Hilt
4. Database - Room
5. Preferences storage - DataStore
6. Architecture components - Lifecycle, ViewModel
7. Flow, Coroutines, KSP, Kotlin serialization
8. Navigaation - Compose Navigation
9. Design System - Adapted from Material 3
10. Testing - JUnit, Compose testing, Turbine

## Architecture

- Clean architecture
- Single activity architecture with Compose Navigations
- MVVM

## Modularization

At Top level, we can find two app modules namely `app` and `app-ui-catalog`.

- `app` - The main module for the app
- `app-ui-catalog` - UI catalog app to view design system components in a standalone app.

All features are inside a top level package `feature`.  
And the core parts of the app are inside the module named `core`.

`chart` - This module is used to render charts in the app like this,

<img src="screenshots/chart.png" width="250" />

### Core

1. `common` - For basic functionalities commonly used throughout the app  
   Examples
   - App Constants
   - Coroutine dispatcher providers
   - JSON reader and writer
   - String decoder
2. `database` - For all database operations - Dao, DataSource, Database migrations, etc.
3. `datastore` - For fetching and storing data from & to preferences in data store
4. `data` - For combining data from various data sources like datastore, database, local files, etc and expose repositiry and use-case for feature module usage.
5. `designsystem` - For theming using colors, shapes and typography.  
   Also has customized atomic UI components - `MyText`, `MyTab`, `MyLinearProgressIndicator`, etc.
6. `ui` - Contains higher level UI components built using custom atomic component and Material 3 components - `SelectAccountBottomSheet`, `OverviewCard`, `MyEmojiCircle`, etc.
7. `logger` - For logging in debug builds
8. `model` - Model classes and serializers
9. `navigation` - NavigationManger is used to control navigation from ViewModels.
10. `network` - WIP
11. `testing` - Test Runner, Dispatcher Rule and other testing helper methods.

### Features

1. `analysis`
2. `accounts`
3. `categories`
4. `home`
5. `settings`
6. `transactionfor`
7. `transactions`

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
