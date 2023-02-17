package controller.commands;

import dungeonmodel.AnyDungeon;


/**
 * A command for making the player pick treasure or arrows.
 */
public class GraphicalPick implements DungeonExplorerCommandBool {
  private String toPick;

  /**
   * A constructor for the command pick.
   *
   * @param toPick incoming move
   */
  public GraphicalPick(String toPick) {
    this.toPick = toPick;
  }

  @Override
  public boolean execute(AnyDungeon m) {
    if (this.toPick.equals("arrow")) {
      m.makePlayerPickArrows();
    } else if (this.toPick.equals("treasure")) {
      m.makePlayerPickTreasure();
    }
    return true;
  }
}
