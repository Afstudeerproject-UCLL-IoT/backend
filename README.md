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

| Use case | Format | Type | Example |
| --- | --- | --- | --- |
| Registering a puzzle device | {EVENT}\_{DeviceType}\_{PuzzleName}\_{PuzzleSolution} | **Sent** | REGDEVP_ARDUINO_Puzzle1_666 |
| Registering the only feedback device | {EVENT} | **Sent** | REGDEVF |
| The player tries to solve the puzzle but failed | {EVENT}\_{PuzzleName}\_{GameSessionId}\_false | **Sent** | PATMPT_Puzzle1_123_false |
| The player solves the puzzle | {EVENT}\_{PuzzleName}\_{GameSessionId}\_true | **Sent** | PATMPT_Puzzle1_123_true |
| The session is not idle | {EVENT} | **Sent** | ALIVE |
| The registration details after registering a puzzle | {Event}\_{DeviceId}\_{DeviceType}\_{PuzzleName}\_{PuzzleSolution} | **Receive** | REGDET_1_ARDUINO_Puzzle1_666 |
| The new solution for a puzzle | {Event}\_{PuzzleName}\_{NewPuzzleSolution} | **Receive** | NEWSOL_Puzzle1_999 |
| The game has started | {Event}\_{GameSessionId} | **Receive** | STARTGAME_123 |
| The puzzle can be started | {Event}\_{PuzzleName} | **Receive** | STARTPZL_Puzzle1 |
| The active game has ended | {Event} | **Receive** | ENDGAME |
| Feedback for failed puzzle attempt | {Event}-{PuzzleName}\_Solved\_false | **Feedback** | FEEDBACK_Puzzle1_Solved_false |
| Feedback for successfull puzzle attempt | {Event}-{PuzzleName}\_Solved\_true | **Feedback** | FEEDBACK_Puzzle1_Solved_true |

## Working points
- [ ] When devices reboot they lose their game state and when comming up can't know if their is a game currently going on where they are a part off. When devices reconnect check if a game is being played and if it's their turn and send the appropriate event.
- [ ] Don't make constructors private in the domain package so we don't need to have builders all over the place, the persistence layer can make use of the mapping tools provided by jooq but we force the Builder pattern for no reason.
- [ ] Make the domain class more OOP like with relations instead of simple POJO classes that are combinded in DTO's. When you do this you can also take input DTOS from the controllers/WebSocket and map them to a specific domain class. So don't take domain classes as input in Controllers for example. Now we have a simple domain so it can be argued that this is overkill. I don't really know.
- [ ] The tests are not up to date anymore so fix those.
- [ ] Exceptions vs ServiceActionResponse? I don't know maybe we should just use Exceptions and use a ControllerAdvice class for handling them.
- [ ] The controllers needs to check for all the possible ServiceErrors and do a correct translation to a http status code. Also provide those as a Response so that the swagger ui can display the different response types.
- [ ] Maybe use something like STOMP on top of WebSockets so we don't need to interfact as much with a lower level class like WebSockets, this is also recommended by the Spring docs.
