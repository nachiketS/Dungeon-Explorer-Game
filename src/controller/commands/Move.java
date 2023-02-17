package controller.commands;

import static dungeonmodel.Direction.east;
import static dungeonmodel.Direction.north;
import static dungeonmodel.Direction.south;
import static dungeonmodel.Direction.west;

import dungeonmodel.AnyDungeon;


/**
 * A command that makes the player move.
 */
public class Move implements DungeonExplorerCommand {
  private String command;

  public Move(String c) {
    this.command = c;
  }


  @Override
  public String execute(AnyDungeon m) {
    if (m == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    String msg;
    switch (command) {
      case "w":
        msg = m.makePlayerMove(north);
        break;
      case "s":
        msg = m.makePlayerMove(south);
        break;
      case "a":
        msg = m.makePlayerMove(west);
        break;
      case "d":
        msg = m.makePlayerMove(east);
        break;
      case "q":
        msg = "quit";
        break;
      default:
        throw new IllegalArgumentException("Illegal Move");
    }
    return msg;
  }
}
