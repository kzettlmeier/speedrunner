# speedrunner

## How to Run
In order to run the application you must have the Java 11 JDK installed and have you JAVA_HOME environment variable set 
to the home directory of Java (example: C:\Program Files\Java\jdk-11.0.2).

If you want to run unit tests you can run the following from the same directory as the pom.xml:
```bash
mvnw clean install -U
```

If you want to run the application you can run the following from the same directory as the pom.xml:
```bash
mvnw spring-boot:run
```

## Requirements of API
1. Add a game title
    - You can add a game title by making the following POST request to: http://localhost:8080/game with the below JSON 
    body, replacing {GAME_TITLE} & {CATEGORY_N} with their respective values. If a category doesn't yet exist it will 
    create the category along with the game.
     ```json
    {
        "title": "{GAME_TITLE}",
        "gameCategories": [
            {
                "category": {
                    "name": "{CATEGORY_1}"
                }
            },
            {
                "category": {
                    "name": "{CATEGORY_N}"
                }
            }
        ]
    }
    ```
2. Retrieve all game titles
    - You can retrieve all game titles by making a GET request to: http://localhost:8080/game.
3. Add a category to a game title. Note, games can have more than one category
    - You can add a category to a new game title by following number (1) above.  If the game is existing, you can add a 
    category by making a PUT request to http://localhost:8080/game/{GAME_TITLE} where {GAME_TITLE} is the title of the 
    existing game in which you want to add the category(ies) ({CATEGORY_N}) to.  Send along the following body in order to update the 
    game:
    ```json
    {
        "title": "{GAME_TITLE}",
        "gameCategories": [
            {
                "category": {
                    "name": "{CATEGORY_1}"
                }
            },
            {
                "category": {
                    "name": "{CATEGORY_N}"
                }
            }
        ]
    }
    ``` 
4. Retrieve all categories for a specified game title
    - You can retrieve all of the categories for a specific game title by making a GET request to 
    http://localhost:8080/{GAME_TITLE}.
5. Add a speedrun which must contain game title, category, player name, and duration
    - You can add a speedrun for game, category, player with a duration by making a POST request to 
    http://localhost:8080/speedrun with the below JSON body.  (NOTE: The game, user, and category must already exist).
    You can do GET calls to get existing game and user objects to insert into the JSON body for the request.
    ```json
    {
        "game": {
            "id": "{GAME_ID}",
            "title": "{GAME_TITLE}",
            "gameCategories": [
                {
                    "id": "{GAME_CATEGORY_ID_1}",
                    "category": {
                        "id": "{CATEGORY_ID_1}",
                        "name": "{CATEGORY_1}"
                    }
                }
            ]
        },
        "user": {
            "id": "{USER_ID}",
            "userName": "{USER_NAME}"
        },
        "category": {
            "id": "{CATEGORY_ID_N}",
            "name": "{CATEGORY_N}"
        },
        "duration": {DURATION_IN_SECONDS}
    }
    ```
6. Retrieve top speedrun times (player name, duration) for a specified game title and category
    - You can retrieve top speedrun times for a specified game and category by making a GET request to 
    http://localhost:8080/speedrun?gameTitle={GAME_TITLE}&category={CATEGORY}.  The results will be sorted in ASCENDING 
    duration order
7. Retrieve top speedrun times for each category (category, player name, duration) for a specified game title
    - You can retrieve the top speedrun times for a specific game by making a GET request to
    http://localhost:8080/speedrun?gameTitle={GAME_TITLE}.  The results will be sorted in ASCENDING duration order.
8. Retrieve speedrun times (game title, category, duration) for a specified player name
    - You can retrieve the top speedrun times for a specific user by making a GET request to
    http://localhost:8080/speedrun?userName={userName}.  The results will be sorted in ASCENDING duration order.