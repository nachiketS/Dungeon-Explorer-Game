package controller.commands;

import dungeonmodel.AnyDungeon;
import dungeonmodel.Direction;


/**
 * A command that allows the player to shoot an arrow.
 */
public class ShootArrow implements DungeonExplorerCommand {
  Direction dir;
  int distance;

  /**
   * A constructor for the shoot arrow command.
   *
   * @param dir  The incoming direction
   * @param dist The incoming distance
   */
  public ShootArrow(String dir, String dist) {
    switch (dir) {
      case "w":
        this.dir = Direction.north;
        break;
      case "a":
        this.dir = Direction.west;
        break;
      case "s":
        this.dir = Direction.south;
        break;
      case "d":
        this.dir = Direction.east;
        break;
      default:
        this.dir = null;
        throw new IllegalArgumentException("Invalid direction");
    }
    this.distance = Integer.parseInt(dist);
  }

  @Override
  public String execute(AnyDungeon m) {
    if (m == null) {
      throw new IllegalArgumentException("The model cannot be null");
    }
    if (m.makeplayerShootArrow(this.dir, this.distance)) {
      return "You hear a roar in the distance";
    }
    return "You hear the arrow whistling into the darkness";
  }
}
