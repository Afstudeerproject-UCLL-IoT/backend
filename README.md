# Backend
Spring boot backend for our IoT application

## Using the application

- Make sure you change the database details in de application properties. We are using PostgreSQL.
- Run the schema.sql
- Run or build the project
  - Building => gradlew clean build  
  - Running => gradlew bootRun

## Arduino events

An arduino device can interact with the backend by sending and receiving messages over a websocket. This table describes the nature of these messages:
- The format of the message so the backend can understand it and the client can parse it.
- The type of the message indicating if it's a message sent by the client or received by the client.
- An example message

| Use case | Format | Type | Example |
| --- | --- | --- | --- |
| Registering a puzzle device | {DeviceType}\_{PuzzleName}\_{PuzzleSolution} | **Sent** | ARDUINO_Puzzle1_666 |
| Registering a feedback device | {DeviceType} |  **Sent** | ARDUINO_FEEDBACK |
| The player tries to solve the puzzle but failed | {PuzzleName}\_Solved\_False |  **Sent** | Puzzle1_Solved_False |
| The player solves the puzzle | {PuzzleName}\_Solved\_True |  **Sent** | Puzzle1_Solved_True |
| The registration details after registering a puzzle | {Event}\_{DeviceId}\_{DeviceType}\_{PuzzleName}\_{PuzzleSolution} | **Receive** | RD-1-ARDUINO-Puzzle1-666 |
| The new solution for a puzzle | {Event}\_{PuzzleName}\_{NewPuzzleSolution} | **Receive** | NS-Puzzle1-999 |
| The puzzle can be started | {Event}\_{PuzzleName}\_START | **Receive** | PS-Puzzle1-START |
| Feedback for failed puzzle attempt | {PuzzleName}\_Solved\_False |  **Receive** | Puzzle1_Solved_False |
| Feedback for successfull puzzle attempt | {PuzzleName}\_Solved\_True |  **Receive** | Puzzle1_Solved_True |
