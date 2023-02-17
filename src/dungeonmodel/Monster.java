package dungeonmodel;

/**
 * An interface that represents monster.
 */
public interface Monster {

  /**
   * A method upon calling which will hurt the monster.
   *
   * @param i The damage inflicted upon the monster
   * @return: Boolean if the monster gets hit
   */
  boolean getHurt(int i);

  /**
   * A method to get the health of the monster.
   *
   * @return: int health
   */
  int getHealth();

  /**
   * A method to make the monster eat the player.
   *
   * @return: boolean if the player gets eaten
   */
  boolean eat();
}

