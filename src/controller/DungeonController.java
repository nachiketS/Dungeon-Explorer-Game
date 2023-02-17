package controller;

import dungeonmodel.AnyDungeon;

/**
 * An interface to represent controllers for dungeon.
 */
public interface DungeonController {

  /**
   * A method that allows you to play the game.
   *
   * @param m model
   */
  void playGame(AnyDungeon m);
}
