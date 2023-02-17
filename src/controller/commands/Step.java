package controller.commands;

import static dungeonmodel.Direction.east;
import static dungeonmodel.Direction.north;
import static dungeonmodel.Direction.south;
import static dungeonmodel.Direction.west;

import dungeonmodel.AnyDungeon;

/**
 * A command to represent a step movement.
 */
public class Step implements DungeonExplorerCommandBool {

  private String command;

  public Step(String c) {
    this.command = c;
  }


  @Override
  public boolean execute(AnyDungeon m) {
    if (m == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    boolean msg;
    switch (command) {
      case "w":
        msg = m.makePlayerMoveTo(north);
        break;
      case "s":
        msg = m.makePlayerMoveTo(south);
        break;
      case "a":
        msg = m.makePlayerMoveTo(west);
        break;
      case "d":
        msg = m.makePlayerMoveTo(east);
        break;
      case "q":
        msg = false;
        break;
      default:
        throw new IllegalArgumentException("Illegal Move");
    }
    return msg;
  }
}

