package dungeonmodel;


/**
 * An interface that represents the dungeon model for any dungeon explorer game.
 * A dungeon has location which can be caves or tunnels. Caves can hold treasures of either
 * Ruby, Sapphire or Diamond. The user has to enter the size of the grid to be created, the
 * interconnectivity, if the dungeon is wrapping and percentage of caves which should have a
 * treasure.
 */

public interface AnyDungeon extends ReadOnlyDungeonModel {



  String makePlayerMoveNorth();

  String makePlayerMoveSouth();

  String makePlayerMoveEast();

  String makePlayerMoveWest();

  void makePlayerPickTreasure();

  boolean makeplayerShootArrow(Direction dir, int dist);

  void makePlayerPickArrows();

  void makePlayerExitDungeon();

  String makePlayerMove(Direction north);

  boolean makePlayerMoveTo(Direction north);

}
