package dungeonmodel;

import java.util.Map;

/**
 * An interface for any kind of agent that will be navigating the dungeon.
 */
public interface Agent {
  /**
   * Get the name of the player.
   *
   * @return : String
   */
  String getName();

  /**
   * Get a copy of the location at which the player currently is.
   *
   * @return : Location object
   */
  AnyLocation getLocation();

  /**
   * Get a description of the player mentioning the player name, their location and the treasures
   * they are holding.
   *
   * @return : String.
   */
  @Override
  String toString();

  /**
   * Get a list of the Treasures that the player is currently holding.
   *
   * @return : List of Treasures.
   */
  Map<Treasure, Integer> getTreasures();

  /**
   * Pick arrows.
   */
  void pickArrows();

  /**
   * Get the number of arrows.
   *
   * @return: int get number of arrows held by the player
   */
  int getNumArrows();

}
