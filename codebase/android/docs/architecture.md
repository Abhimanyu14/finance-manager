# Architecture

## Legend

1. Composable

   ```mermaid

   flowchart TB
   CO([Composable])

   ```

2. Class

   ```mermaid
   flowchart TB
   C(Class)

   ```

## Architecture Diagram

```mermaid
flowchart TB
    subgraph PresentationLayer
        direction TB
        SC([Screen Composable])
        SV(Screen ViewModel)
        SUSEH([Screen UI State And Event Handler])
        SEH(Screen Event Handler)
        SUC([Screen UI Composable])

    end

    subgraph DomainLayer
        D(UseCases)
    end

    SC -- UI State --> SUC
    SUC -- UI Events --> SC
    SC -- Events --> SV
    SV -- Data --> SC
    SC -- ViewModel --> SUSEH
    SUSEH -- UI State and Event Handler --> SC
    PresentationLayer --> DomainLayer
    SUSEH -- "`1.ViewModel
    2. UI state
    3. events`" --> SEH

```

## Details

1. Screen UI Composable

    - Stateless Composable to render UI
    - Can be tested using Compose testing

2. Screen ViewModel

    - Data Holder
    - Can be tested using unit testing

3. Screen Composable

    - Logic placed here can not be tested

4. Screen UI State And Events

    - Logic placed here can not be tested
