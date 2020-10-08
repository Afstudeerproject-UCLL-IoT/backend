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
- The type of the message indicating if it's a message sent by the client or received by the client. And if it's for the feedback device or not.
- An example message

| Use case | Format | Type | Example |
| --- | --- | --- | --- |
| Registering a puzzle device | {EVENT}\_{DeviceType}\_{PuzzleName}\_{PuzzleSolution} | **Sent** | REGDEVP_ARDUINO_Puzzle1_666 |
| Registering a feedback device | {EVENT}\_{DeviceType} |  **Sent** | REGDEVF_ARDUINO_FEEDBACK |
| The player tries to solve the puzzle but failed | {EVENT}\_{PuzzleName}\_Solved\_False |  **Sent** | PATMPT_Puzzle1_Solved_False |
| The player solves the puzzle | {EVENT}\_{PuzzleName}\_Solved\_True |  **Sent** | PATMPT_Puzzle1_Solved_True |
| The registration details after registering a puzzle | {Event}\_{DeviceId}\_{DeviceType}\_{PuzzleName}\_{PuzzleSolution} | **Receive** | REGDET_1_ARDUINO_Puzzle1_666 |
| The new solution for a puzzle | {Event}\_{PuzzleName}\_{NewPuzzleSolution} | **Receive** | NEWSOL_Puzzle1_999 |
| The puzzle can be started | {Event}\_{PuzzleName} | **Receive** | STARTPZL_Puzzle1 |
| The active game has ended | {Event} |  **Receive** | GAME_ENDED |
| Feedback for failed puzzle attempt | {PuzzleName}\_Solved\_False |  **Receive / Feedback** | Puzzle1_Solved_False |
| Feedback for successfull puzzle attempt | {PuzzleName}\_Solved\_True |  **Receive / Feedback** | Puzzle1_Solved_True |

