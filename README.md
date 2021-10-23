# Halloween Candy Crush
Replica of the Candy Crush game for the Advance Programming of UI course. 

## How to run the app

### Using the .jar file
By just double clicking to execute the file, player will be able to start a new Halloween Candy Crush game.

### Using just the zip folder


## How to play
Since the project is based on the original Candy Crush game, the method of playing is basically the same one. 
We have just substituted the swipe interaction by a drag-and-drop one, since it was meant to be tested on our laptops. 

The main objective of the players should be reaching each available level's score goal, and in order to do that they will have to group at least three candies of the same type (by swiping two of them at a time) to make them crush and be replaced by new ones - which will lead to more ponential oportunities to crush candies. 

### Available candies
- **Normal candies:** Their only effect is disappearing if the user groups three or more items of the same type in a row or column using the drag and drop interaction. Types are: **eyeball**, **orange candy**, **pumpkin**, **round lolly** and **swirl lolly**.
- **Special candies:** there are four of these candies, which have a unique effect when interacting with them.
    - **Green poison:** It is generated when the user crushes 4 candies of the same type in a row. The user can activate this candy and make it crush after using it for creating a crush. When crushed, this item horizontally crushes the full row where it is placed.

    - **Red poison:** It is generated when the user crushes 4 candies of the same type in a column. This candy is activated the same way as Green Poison is activated. When crushed, this item vertically crushes the full column where it is placed.

    - **Firework:** It is generated when the user crushes 5 candies of the same type, no matter if it is vertically or horizontally. The user can activate this candy by swiping it with any other normal candy, it does not matter if that movement generates a new crush or not. When crushed, the item crushes all the candies of the same type of the other one involved in the interaction that are displayed on the board.

    - **Mummy:** it is generated when the user crushes 5 candies of the same type, but in an L or T shape. The user can activate this candy the same way Red and Green Poison are activated. When crushed, the item crushes a 3x3 square of candies around it. 

### Running the game
In order to play the game, the player will first find a welcome screen where they will have to insert their desired username (for testing purposes, you can insert “test”, since it is an already-created player with some progress made). In case the username is new, a new user will be created with only one unlocked level. 
After inserting the username and clicking on the “start” button or pressing ENTER, the level choice screen will be shown. When the user selects the desired level (by clicking the number button), the application will load the actual board of the game. 

In this moment, the player will be able to see their current score but also the goal score they will have to reach to go to the next level. In this screen, all the player has to do is try to swipe candies to group at least three of the same type, as we have mentioned before in this document. If candies are grouped correctly, they will crush and be replaced by new ones. If the player groups candies in any of the “special ways” we have gone through in the previous sections, special candies will be generated and ready to be used in the game. 

Every time the player crushes some candies, they gain points that allow them to reach the level goal. Doesn’t matter if the player wins or loses, they will be getting a notification from the application (in a dialog format) to make them aware. 

In case the player wins the game, the progress will be saved within the application making it accessible for future games. The player is now able to go back to the level choice screen and start the new available level. 
