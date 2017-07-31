# orbitalMS3



Development progress 

UI/UX

Build on what we have from Milestone 2, several updates were made to enhance the UI/UX.

	Floating button on home page
This button gives direct access to User profile, Settings, PlayDemo and Developer information of the game. 

	Sound & Animation 
To list a few:  sparks and sound effects when a tile is pressed correctly, bomb effects and vibrations when pressed wrongly, shaking and freezing of the tiles……

	Renewed PlayDemo
The PlayDemo will automatically run when the game runs for the first time. The PlayDemo will also remind the user “Are you ready to start a real game?”. Upon confirmation from the user, he/she will be directed to select either single player or multiplayer game. The screen has been adjusted to completely resemble the actual game board, with all animations and effects in place. 

	Embedded game instructions
When the user press and hold buttons that indicates a particular type of game, such as “Easy”,”Extreme”,”Hurry-UP”,etc, a dialog with detailed instructions will appear to assist the user in understanding the game rules.

	Error handling
Alert dialog will show to remind the users when certain services are called. For instance, the user needs to sign in to be able to save data. Similarly, users will also be reminded that game ID and Internet connections are required to access the scoreboard, and to play multiplayer games that require real-time matching of players.  

Please check out our Project Video for more vivid display of user interface!







Features 

Features already implemented by Milestone 2 are:

	Countdown timer
	Replacement of tiles
	Life Tracker
Reduces life when user presses the tile in the wrong order or fail to press the UP button
	Personal high score tracker
	Background music


Additional/Updated features implemented are:

	Login & player profile 

When the player first installs and run the game, he/she will be promoted to choose between three login modes - Facebook, Google, and email with password. The user can also set his/her player name at this point.

The user can choose to go anonymous, where he/she does not login. However, game data will only be saved locally until he/she login using one of the abovementioned methods. 

To prevent user irritation, no prompts will be shown after the first run. Should the user wish to login or logout, or update his/her player name, he/she can do so anytime in Settings. 

	Changes on UP digit generation and grid sizes.

The default setting is random generation. Player can set his/her own UP digit for single player mode through Settings.

All games are now restricted to a 4x4 grid, with the exception of Team-UP(3x6 for each player) and Extreme (a growing grid)






	Single player mode
Single player mode offers 5 difficulty levels. The levels are designed with increasing difficulties and challenges, to entice the player as he/she progress. 

LEVEL NAME	FEATURES
	Number of lives	Hint*	Rotating gameboard	Freezing tiles**	Changing/Adding of UP digit(s)***	Growing grid****
LAME	Unlimited						
EASY	3					
MEDIUM	3					Change	
HARD	3						Add	
EXTREME	3					Add		

* The next tile will shake if the player cannot find it within 3 seconds.
**random tiles will be ‘frozen’. An extra 1 to 3 taps is required to release the tile.
***Depending on the difficulty level, either the UP digit will change, or a second UP digit will be added.A pop up window will appear to remind the player to avoid the number before the game resumes. 
****Starting with the standard 4x4, the grid it will grow to 4x6, and subsequently 6x6. 

	Multiplayer mode

To double the fun, there are 3 multiplayer modes available. 

UP&DOWN and hurryUP requires one player to invite another player to join the game. Matching of players is achieved through Firebase Realtime database. 

Team-UP can be played by two players simultaneously using when device. It can also be played by one single player. 

•	UP&DOWN
Match two players. This mode has no time limit. For the first round, player A starts from increasing numbers (1,2,3…) while player B starts from decreasing numbers (100,99, 98…). Depends on where they meet, the players will get corresponding scores. They then switch position (player A starts from decreasing numbers and player B starts from increasing numbers). Combine scores from two rounds of game and determine winner.

•	hurryUP
Match two players.They will play the same game at the same time. Challenge to see who reaches the target first.

•	Team-UP
Time limit is imposed. The screen will display a UP buttons on both ends of the screen. Each player is in charge of half the grid and one UP button. Which UP button is to be pressed depends on the location of the UP number, for example, if the UP number appears on lower half of the screen, then the player in charge of the lower UP button must press it. 


	Leaderboard
There is a separate leadership board for each difficulty level. Players are able to sync their scores at real-time and compare their scores with other players.




Testing

The feedback from potential users and our corresponding improvements are listed below.

	FEEDBACK	IMPROVEMENT
1	Hard to tell whether UP button has been pressed
	Added new animation where the game mascot will hold up a “UP” flag when the UP button is pressed. Special effects (sound, fireworks, etc) will be displayed.
2	The screen is not very dynamic. 
	Added in new animation and special effects.
3	Background music can be a bit distracting at time
	Added in the option to switch off background music 
4	Simple instructions to be provided under the ‘New Game’ tab as most people do not really check out the ‘How to Play’ tab despite being new to the game	Enhanced the PlayDemo, which automatically launch during first run,
Added a new feature where pressing and holding buttons that indicates a particular type of game, such as “Easy”,”Extreme”,”Hurry-UP”,etc, will open a dialog with detailed instructions to assist the user in understanding the game rules.

5	Game play itself is not too challenging and it gets boring after 30 seconds	Different modes ‘Lame, Easy, Medium, Hard, Extreme’ were created to make the game play more exciting and challenging.

6	Too many screens to click before actual game play	Shifted the settings for randomization of UP digit to the ‘Settings’ tab. 

7	Not very intuitive to have the ‘Continue’ button when there is no saved game to continue with
	Continue button will only be visible if there is a game already saved.

Reduced the number of buttons on home screen. Instead, functions can be accessed through a floating button, which makes the screen neater.

8	Game theme is quite cute and should make use of the mascot for other screens in the game	Integrated the mascot into game play through additional animations. 


Problems Faced

•	Rotation of grid
o	Due to the rotation of grid by 180 degrees, tapping of the tiles became incorrect (eg tapping the top left tile resulted in the bottom right tile being tapped instead), resulting in reading of wrong numbers.
o	Solution: A new set of code is implemented through using a Boolean value that detects if the grid has rotated. If the grid has rotated, the reading of the numbers of the tiles will be set differently accordingly. 
•	Real-time Multiplayer using Firebase
o	There were few example on using Firebase for games and being new to Firebase, it was difficult for us initially to understand how to make use of the features available to create a real-time game.
o	Solution: We managed to find an example for real-time multiplayer game that is implemented using JavaScript and used for web app. Through analyzing and understanding the code, we created our own real-time multiplayer game by getting some ideas from there.
•	Generation of the same grid for HurryUP
o	Initially for all other game play, generation of next numbers are set by random and varies from device to device.
o	Solution: We used Firebase to store the generated numbers where each device will then get the data from there to create the same generation of numbers.
•	Out of Memory (OOM) error
o	We were faced with OOM error after adding the different animations, resulting in the game stopping abruptly at any point during game play.
o	Solution: Images used in the game play were scaled down to smaller sizes and removed the images when the activity stops.
•	Leadership Board
o	There was little information available on creating a leadership board using Firebase on Android Studio.
o	Solution: Everything in the leadership board is written from scratch, by applying the skills we had learnt through creating the multiplayer game. Various errors had occurred but eventually debugged by us.



~END~
