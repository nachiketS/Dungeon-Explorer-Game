package dungeonmodel;

import java.util.List;
import java.util.Map;

/**
 * An interface that represents the dungeon model for any dungeon explorer game.
 * A dungeon has location which can be caves or tunnels. Caves can hold treasures of either
 * Ruby, Sapphire or Diamond. The user has to enter the size of the grid to be created, the
 * interconnectivity, if the dungeon is wrapping and percentage of caves which should have a
 * treasure.
 */

public interface ReadOnlyDungeonModel {

  /**
   * The game can end one of two ways, either pressing "q" will quit the game or else if the player
   * is at the end cave in the dungeon.
   * Check if the player is at the end cave.
   *
   * @return : boolean
   */
  boolean isEnd();

  /**
   * Return a list of next possible moves to the user.
   * If any on the impossible moves are entered, the player wouldn't do anything.
   * "w" for moving up
   * "a" for moving left
   * "s" for moving down
   * "d" for moving right
   * "p" for picking up treasure
   * "q" for quitting game
   * "e" for ending when player is at exit cave
   *
   * @return : ArrayList of characters to enter to make the player take action.
   */
  List<String> nextMoves();

  /**
   * Get a list of all the caves which have treasure and the treasures that they hold along with
   * them.
   *
   * @return : Map of cave and list of treasures with all the caves and the treasures that those
   *          caves hold.
   */
  Map<Location, List<Treasure>> getListOfTreasures();


  /**
   * Get the treasure at a particular location.
   * Else return an empty List.
   *
   * @param l : Location to check treasure at.
   * @return : List of Treasure
   */
  List<Treasure> getTreasuresAtLocation(Location l);

  /**
   * Return a deep copy of the grid which is being used in the dungeon.
   *
   * @return : Two Dimensional Array List of Locations.
   */
  List<List<Location>> getGrid();

  /**
   * Return the interconnectivity which was entered.
   *
   * @return : interconnectivity as int.
   */
  int getInterconnectivity();

  /**
   * Return the is Wrapping argument which got set for the dungeon.
   *
   * @return : boolean
   */
  boolean isWrapping();

  /**
   * Get the number of rows.
   *
   * @return : int
   */
  int getNumRows();

  /**
   * Get the number of columns.
   *
   * @return : int
   */
  int getNumCols();

  /**
   * Get a copy of the player object currently in the dungeon.
   *
   * @return : Player
   */
  Agent getPlayer();

  /**
   * Get the distance between any two locations in the dungeon. Both the locations should be
   * present in the dungeon.
   *
   * @param src  : Location 1
   * @param dest : Location 2
   * @return : int, shortest maze distance possible between the two locations.
   */
  int getDistance(AnyLocation src, AnyLocation dest);

  /**
   * Get the start and end caves that have been set for this dungeon.
   * The start and end caves have a min distance of 5.
   *
   * @return: List of 2 elements. The first element is the start location and the second element is
   *          the end location.
   */
  List<Location> getStartEnd();

  /**
   * Returns if the game is currently in progress or not.
   *
   * @return boolean, true if the game is in progress else false.
   */
  boolean inProgress();

  /**
   * Returns the current state of the Dungeon model.
   *
   * @return String, description of the current state.
   */
  String getState();

  /**
   * Retrieve the treasures at the location.
   *
   * @param l Location in dungeon
   * @return: Get the map at the location
   */
  Map<Treasure, Integer> getTreasuresMapAtLocation(Location l);


  long getSeed();

  int getDifficulty();

  int getPercentageTreasures();

  int getNumberOfPits();

  int getNumberOfRobbers();
}
