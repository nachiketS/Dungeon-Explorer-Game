package controller.commands;


import dungeonmodel.AnyDungeon;
import dungeonmodel.Treasure;
import java.util.Map;

/**
 * A command for picking items from locations.
 */
public class Pick implements DungeonExplorerCommand {
  String move;

  /**
   * A constructor for the command pick.
   *
   * @param s incoming move
   */
  public Pick(String s) {
    if (s == null) {
      throw new IllegalArgumentException("Input String cannot be null");
    }
    this.move = s;
  }


  @Override
  public String execute(AnyDungeon m) {
    String toRet = new String();
    if (m == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    switch (this.move.toLowerCase()) {
      case "t":
        Map<Treasure, Integer> l = m.getPlayer().getLocation().getTreasures();
        m.makePlayerPickTreasure();
        toRet = String.valueOf(l);
        break;
      case "a":
        int numArr = m.getPlayer().getLocation().getArrows();
        m.makePlayerPickArrows();
        toRet = String.valueOf(numArr);
        break;
      case "q":
        return "quit";
      default:
        throw new IllegalArgumentException("Invalid action");
    }
    return ("Player picked " + toRet);
  }
}
