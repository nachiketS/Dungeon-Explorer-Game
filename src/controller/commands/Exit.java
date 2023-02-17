package controller.commands;

import dungeonmodel.AnyDungeon;

/**
 * A command to exit from the game.
 */
public class Exit implements DungeonExplorerCommand {
  @Override
  public String execute(AnyDungeon m) {
    m.makePlayerExitDungeon();
    return "You Won !!!";
  }
}
