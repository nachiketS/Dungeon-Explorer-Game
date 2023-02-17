package dungeonmodel;

import java.util.List;
import java.util.Map;

/**
 * An interface to represent any type of Location. All the methods defined below are expected
 * from any location class that implements this interface.
 */
public interface AnyLocation {
  /**
   * A method to get the coordinate of the Location.
   *
   * @return coordinate
   */
  Coordinate getCoOrdinates();

  /**
   * A method to check if the Location is a cave or not.
   *
   * @return boolean
   */
  boolean isCave();

  /**
   * A method to get all the neighbors of a location in a list.
   *
   * @return : List of neighbors.
   */
  List<Location> getNeighbours();

  /**
   * A method to get the north neighbor of the Location.
   *
   * @return : Location to the north.
   */
  Location getNorth();

  /**
   * A method to get the south neighbor of the location.
   *
   * @return : location to the south.
   */
  Location getSouth();

  /**
   * A method to get the east neighbor of the location.
   *
   * @return : location to the east.
   */
  Location getEast();

  /**
   * A method to get the west neighbor of the location.
   *
   * @return : location to the west.
   */
  Location getWest();

  /**
   * Get the treasure list at the location.
   *
   * @return: List of treasures at the location
   */
  List<Treasure> getTreasureList();

  /**
   * Get the treasures as a map.
   *
   * @return: A map of treasure type and count
   */
  Map<Treasure, Integer> getTreasures();

  /**
   * Get the count of arrows at the location.
   *
   * @return: int count of arrows
   */
  int getArrows();

  /**
   * Get all the directions and neighbours of the current location.
   *
   * @return: the map of neighbours and directions
   */
  Map<Direction, Location> getNeighboursMap();

  /**
   * Get the monster in the location.
   *
   * @return: Monster
   */
  Monster getMonster();

  int getSmell();

  boolean hasMonster();

  boolean hasPit();

  boolean hasRobber();
}
