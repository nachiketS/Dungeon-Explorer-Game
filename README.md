# PDP Project 3 model.Dungeon
## About/Overview
A Role Playing Adventure Game in which you are a player stuck in a dungeon.
Your aim is to explore the Dungeon and find the exit. 
The Dungeon has many elements to it some beneficial and some harmful.
There are many treasures and arrows throughout the Dungeon. 
There are also Pits in the Dungeon which the player can fall into, there are thieves 
that will steal your treasures and there are Otyughs. Otyughs are smelly solitary creatures that eat
meat. If the player meets the otyugh, it will eat the player. The player
has crooked arrows that they can use to kill the Otyugh.

## List of Features
####The Dungeon
- All the interactions with the game happen through the dungeon.
- The num of rows and num of columns for the dungeon can be set.
- The interconnectivity can be set using the model.
- There is an option to select wrapping or non-wrapping dungeon.
- The percentage of treasures and arrows that are to be found in the dungeon can be selected.
- The difficulty level of the dungeon can be selected.
- The number of Pits to be added can be selected.
- The number of Robbers to be added can be entered.
- The difficulty level will influence the number of monsters getting added to the dungeon, the min. is 1 and the max. is 3

#### The Player
- Player or the actual agent that is entering the dungeon knows about its current location i.e. a cave or a tunnel.
- A player cannot move if the game hasn't started.
- The player cannot act if the game has not started.
- Player has the ability to move in four directions.
- The player has the ability to pick, he can pick anything that is present in the location (Arrows/Treasures).
- The player has the ability to shoot arrows in a specific direction with a specific distance.
- The arrows each cause one damage to the monster which has a total health of 2.
- The player has the ability to exit the dungeon and win the game if they reach the end cave.
 
#### The Location
- A location is a point in the 2D dungeon grid which can have at most 4 neighbours.
- A location with 2 entrances is identified as a tunnel and one with 1, 3 or 4 entrances is identified as a cave.
- A cave can contain both monsters and treasures.
- A cave with only once entrance can have a pit.
- A tunnel can have robber which can rob the player of their treasures.
- Arrows can be found in any location i.e. Caves and Tunnels.
- The game can only start and end at a cave.

#### The Monster
- A monster is present in dungeon only in caves.
- Monsters initially have health 2.
- Monsters can take damage from the players' crooked arrows.
- The monster if at full health can eat the player if it in the same location.
- The monster at half health can eat the player 50% of the times.

###The Arrow
- The player is equipped with 3 crooked arrows at the start of the game.
- The crooked arrow can always be shot with a direction and a distance.
- The arrow when travelling through caves will try to maintain its direction and hit the wall if there is no path in the desired direction.
- The arrow when travelling through tunnels will change its direction to whatever direction is the exit(since tunnel has only 1 entrance and 1 exit).
- The arrow has a damage associated with it and if it lands at the monsters location, then it will damage the monster.

##The Robber
- The robber hides in the tunnels and will steal the player's treasures if encountered

##The Pit
- Pits are spread throughout the dungeon in caves with one entrances, i.e. caves which are a dead end.
- If the player goes into a location with a pit, it will fall in the pit and die.
- There are sign boards which indicates a pit nearby.

## How to Run
Run the jar file in present in res folder from command line using the following command

```bash
java -jar Project4.jar 4,4,1,false,20,1
```
A 4x4 dungeon with interconnectivity 1 will be created.
This will be a non-wrapping dungeon.
Player will find treasures in atleast 20% of the caves and arrows in 20% of all the locations.
The difficulty is 1, i.e. there will be monsters in 10% of all the caves in the dungeon.
There is also a possibility that the dungeon might not get created because of the requirement that start and end caves must have a distance greater than 5.

## How to use the Program
1. Run the jar. with command line arguments as shown in how to run.
2. Make the player take action using any of the move from next possible moves which is displayed.
3. Player can pick treasures and arrows from a cave.
4. Player can shoot monsters present in dungeon using weapons collected while traversing the dungeon.
5. If you reach the end cave you will see a prompt to end the game by pressing "E" which will mean you won.
6. Enter "q" to quit the model.



## Description of Examples
1. Go to res/ folder
2. Execute the jar file on terminal using the command given in How to Run
3. A new GUI will be displayed.
4. The GUI shows a player in a cave, an info panel of the player details.
5. Press arrow keys to see the player move.
6. Press P + A to pick up arrows.
7. Press P + T to pick up treasures.
8. Press S + any direction key to see a new pop up of
9. The controller will display the current state of the player.
10. The player can change the configuration of the Dungeon through the 
11. 
12. 
13. The user is asked for input from a list of operations such as move, pick or shoot.
14. If user selects move, then user can move in four directions: east, west, north, south
15. If the user selects pick, then user can pick either treasures or weapons present at that specific location.
16. If the user selects shoot, then user can shoot in four directions: east, west, north, south and at a distance ranging from 1 to 5.
17. If the player reaches end location with full capacity monster gets killed.
18. If the player reaches end location with half capacity, then there is 50% probability of getting killed.
19. Entering "q" at any step will force quit the model.

## Design Changes
1. Added Pits to the Dungeon.
2. Added Robbers to the Dungeon.
3. Created a Graphical Controller.
4. Created a new Graphical View.
5. Created new commands for the graphical controller.


## Assumptions
1. Player cannot move until the game begins.
2. The max. number of treasures that can be added to a cave is 5.
3. The max. number of arrows that can be added to a location is 3.
4. Each crooked arrow deals 1 damage.
5. Pit can only exist in a cave with 1 entrance.
6. Thieves dwell in tunnels.
7. The thief will steal all treasures from the player
8. If a game is in progress, a new game cannot be started.

## Limitations
1. There is a possibility that a dungeon may not be created as the distance between start and end node cannot be greater than or equal to 5.
2. Only one player can play at a time.
3. Dungeon currently supports three types of treasures and one type of weapon.

## Citations
- [Scaling an Image](https://riptutorial.com/java/example/28299/how-to-scale-a-bufferedimage)
- [Image Overlay](https://coderedirect.com/questions/389014/overlay-images-in-java)
- [Stream filter lambda](https://www.baeldung.com/java-stream-filter-lambda)
- [Command Pattern](https://www.codeproject.com/Articles/12263/The-Command-Pattern-and-MVC-Architecture)
- [TreeMap in Java](https://stackoverflow.com/questions/571388/how-can-i-sort-the-keys-of-a-map-in-java)
- [Shortest path in an unweighted graph using BFS](https://www.geeksforgeeks.org/shortest-path-unweighted-graph/)
- [Kruskal's algorithm](https://en.wikipedia.org/wiki/Kruskal%27s_algorithm)
- [Design Patterns - MVC Pattern](https://www.tutorialspoint.com/design_pattern/mvc_pattern.html)
- [LinkedHashMap In Java](https://www.geeksforgeeks.org/linkedhashmap-class-java-examples/)