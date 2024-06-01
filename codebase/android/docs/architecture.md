# Architecture

## Legend

1. Composable

```mermaid
flowchart TB

SUC([Composable])

```

2. Class

```mermaid
flowchart TB

SUC(Class)

```

## Architecture Diagram

```mermaid
flowchart TB

subgraph PresentationLayer
direction TB
SC([Screen Composable])
SV(Screen ViewModel)
SUC([Screen UI Composable])
SUSE([Screen UI State And Events])
end

subgraph DomainLayer
D(UseCases)
end

SC-- UI Data and Event Handler -->SUC
SUC-- UI Events -->SC

SC-- Events -->SV
SV-- Data -->SC

SC-- Data -->SUSE
SUSE-- UI Data and Event Handler -->SC

SV  --> DomainLayer

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
