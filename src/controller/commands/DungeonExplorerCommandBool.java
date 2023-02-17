package controller.commands;

import dungeonmodel.AnyDungeon;

/**
 * A method that calls the appropriate method in the model.
 */
public interface DungeonExplorerCommandBool {
  public boolean execute(AnyDungeon m);

}
