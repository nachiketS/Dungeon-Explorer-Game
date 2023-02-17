package controller.commands;

import dungeonmodel.AnyDungeon;

/**
 * An interface to represent a command that can be used in the dungeon model.
 */
public interface DungeonExplorerCommand {
  public String execute(AnyDungeon m);
}
