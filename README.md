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
- The type of the message indicating if it's a message sent by the client or received by the client or intended for the feedback device.
- An example message

| Use case | Format | Type |
| --- | --- | --- | 
| Registering a puzzle device | {EVENT}\_{DeviceType}\_{PuzzleName}\_{PuzzleSolution} | **Sent** |
| **Example =>** | REGDEVP_ARDUINO_Puzzle1_666 |


| Use case | Format | Type |
| --- | --- | --- | 
| Registering the only feedback device | {EVENT} | **Sent** |
| **Example =>** | REGDEVF |


| Use case | Format | Type |
| --- | --- | --- | 
| The player tries to solve the puzzle but failed | {EVENT}\_{PuzzleName}\_{GameSessionId}\_false | **Sent** |
| **Example =>** | PATMPT_Puzzle1_123_false |


| Use case | Format | Type |
| --- | --- | --- | 
| The player solves the puzzle | {EVENT}\_{PuzzleName}\_{GameSessionId}\_true | **Sent** |
| **Example =>** | PATMPT_Puzzle1_123_true |


| Use case | Format | Type |
| --- | --- | --- | 
| The registration details after registering a puzzle | {Event}\_{DeviceId}\_{DeviceType}\_{PuzzleName}\_{PuzzleSolution} | **Receive** |
| **Example =>** | REGDET_1_ARDUINO_Puzzle1_666 |


| Use case | Format | Type |
| --- | --- | --- | 
| The new solution for a puzzle | {Event}\_{PuzzleName}\_{NewPuzzleSolution} | **Receive** |
| **Example =>** | NEWSOL_Puzzle1_999 |


| Use case | Format | Type |
| --- | --- | --- | 
| The game has started | {Event}\_{GameSessionId} | **Receive** |
| **Example =>** | STARTGAME_123 |


| Use case | Format | Type |
| --- | --- | --- | 
| The puzzle can be started | {Event}\_{PuzzleName} | **Receive** |
| **Example =>** | STARTPZL_Puzzle1 |


| Use case | Format | Type |
| --- | --- | --- | 
| The active game has ended | {Event} | **Receive** |
| **Example =>** | ENDGAME |


| Use case | Format | Type |
| --- | --- | --- | 
| Feedback for failed puzzle attempt | {Event}-{PuzzleName}\_Solved\_false | **Feedback** |
| **Example =>** | FEEDBACK_Puzzle1_Solved_false |


| Use case | Format | Type |
| --- | --- | --- | 
| Feedback for successfull puzzle attempt | {Event}-{PuzzleName}\_Solved\_true | **Feedback** |
| **Example =>** | FEEDBACK_Puzzle1_Solved_true |
