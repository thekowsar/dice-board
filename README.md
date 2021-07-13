# Family Play Board

Spring boot project with Maven and JDK 8. This application used to play online game.


## Used Technology

- **Programming language**: Java
- **Framework**: Spring Boot
- **Doc**: Swagger
- **Automated build**: Apache Maven
- **Test**: Unit test, Integration Test

## Prerequisites

- **JDK 8**
- **Maven**

## How To Run

1. Go to the project root directory.
2. Open terminal in this project root directory.
3. Run the following command.
- `cd frontend1/ `
- `mvn clean install`
- `mvn spring-boot:run `

4. Project will run in **http://localhost:8480/app1/**
5. Here added Postmant script **vantura-dice-board.postman_collection.json**  to test services.


## API Doc
Swagger url "http://localhost:8480/app1/swagger-ui.html"


#### 1. Init board with player number
**Http Method:** POST

**URL:** http://localhost:8480/app1/v1/dice-board/01/player/number

**Request Body:**
```
'3'
```

**Response Body:**
```
{
    "data": {
        "playerList": [
            {
                "order": 1,
                "totalScore": 0,
                "lastScore": 0,
                "playerName": "Player 1",
                "doseDiceHolder": "No",
                "doseHitSix": "No",
                "sixAndNotFour": "No"
            },
            {
                "order": 2,
                "totalScore": 0,
                "lastScore": 0,
                "playerName": "Player 2",
                "doseDiceHolder": "No",
                "doseHitSix": "No",
                "sixAndNotFour": "No"
            },
            {
                "order": 3,
                "totalScore": 0,
                "lastScore": 0,
                "playerName": "Player 3",
                "doseDiceHolder": "No",
                "doseHitSix": "No",
                "sixAndNotFour": "No"
            }
        ],
        "boardStatus": "Initiate",
        "boardWinnerName": "",
        "boardWinnerId": 0,
        "playerNumber": 3
    },
    "userMessage": "Successfully board initiate"
}
```


**CURL Command:**
```
curl --location --request POST 'http://localhost:8480/app1/v1/dice-board/01/player/number' \
--header 'Content-Type: application/json' \
--data-raw '3'
```


#### 2. Start game by updating players name
**Http Method:** PUT

**URL:** http://localhost:8480/app1/v1/dice-board/02/start/board

**Request Body:**
```
{
        "playerList": [
            {
                "order": 1,
                "totalScore": 0,
                "lastScore": 0,
                "playerName": "Kowsar",
                "doseDiceHolder": "Yes",
                "doseHitSix": "No"
            },
            {
                "order": 2,
                "totalScore": 0,
                "lastScore": 0,
                "playerName": "Zara",
                "doseDiceHolder": "No",
                "doseHitSix": "No"
            },
            {
                "order": 3,
                "totalScore": 0,
                "lastScore": 0,
                "playerName": "Saifan",
                "doseDiceHolder": "No",
                "doseHitSix": "No"
            }
        ]
    }
```

**Response Body**

```
{
    "data": {
        "playerList": [
            {
                "order": 1,
                "totalScore": 0,
                "lastScore": 0,
                "playerName": "Kowsar",
                "doseDiceHolder": "Yes",
                "doseHitSix": "No",
                "sixAndNotFour": "No"
            },
            {
                "order": 2,
                "totalScore": 0,
                "lastScore": 0,
                "playerName": "Zara",
                "doseDiceHolder": "No",
                "doseHitSix": "No",
                "sixAndNotFour": "No"
            },
            {
                "order": 3,
                "totalScore": 0,
                "lastScore": 0,
                "playerName": "Saifan",
                "doseDiceHolder": "No",
                "doseHitSix": "No",
                "sixAndNotFour": "No"
            }
        ],
        "boardStatus": "Running",
        "boardWinnerName": "",
        "boardWinnerId": 0,
        "playerNumber": 3
    },
    "userMessage": "Successfully board started"
}
```


**CURL Command:**

```
curl --location --request PUT 'http://localhost:8480/app1/v1/dice-board/02/start/board' \
--header 'Content-Type: application/json' \
--data-raw '{
"playerList": [
{
"order": 1,
"totalScore": 0,
"lastScore": 0,
"playerName": "Kowsar",
"doseDiceHolder": "Yes",
"doseHitSix": "No"
},
{
"order": 2,
"totalScore": 0,
"lastScore": 0,
"playerName": "Zara",
"doseDiceHolder": "No",
"doseHitSix": "No"
},
{
"order": 3,
"totalScore": 0,
"lastScore": 0,
"playerName": "Saifan",
"doseDiceHolder": "No",
"doseHitSix": "No"
}
]
}'
```

#### 3. Roll Dice and get score
**Http Method:** GET

**URL:** http://localhost:8480/app1/v1/dice-board/03/roll/dice

**Response Body:**
```
{
    "data": {
        "playerList": [
            {
                "order": 1,
                "totalScore": 0,
                "lastScore": 5,
                "playerName": "Kowsar",
                "doseDiceHolder": "No",
                "doseHitSix": "No",
                "sixAndNotFour": "No"
            },
            {
                "order": 2,
                "totalScore": 0,
                "lastScore": 0,
                "playerName": "Zara",
                "doseDiceHolder": "Yes",
                "doseHitSix": "No",
                "sixAndNotFour": "No"
            },
            {
                "order": 3,
                "totalScore": 0,
                "lastScore": 0,
                "playerName": "Saifan",
                "doseDiceHolder": "No",
                "doseHitSix": "No",
                "sixAndNotFour": "No"
            }
        ],
        "boardStatus": "Running",
        "boardWinnerName": "",
        "boardWinnerId": 0,
        "playerNumber": 3
    },
    "userMessage": "Board is running"
}
```


**CURL Command:**
`curl --location --request GET 'http://localhost:8480/app1/v1/dice-board/03/roll/dice'`

#### 4. Restart Game
**Http Method:** PUT

**URL:** http://localhost:8480/app1/v1/dice-board/04/restart/board

**Response Body:**
```
{
    "data": {
        "playerList": [],
        "boardStatus": null,
        "boardWinnerName": null,
        "boardWinnerId": 0,
        "playerNumber": 0
    },
    "userMessage": "Board restarted Successfully"
}
```

**CURL Command**
```
curl --location --request PUT 'http://localhost:8480/app1/v1/dice-board/04/restart/board' \
--header 'Content-Type: application/json' \
--data-raw '{}'
```