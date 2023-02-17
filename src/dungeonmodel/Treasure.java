package dungeonmodel;

import java.util.List;
import java.util.Random;
import misc.Util;



/**
 * An enum to represent Treasures.
 */
public enum Treasure {
  RUBY(1),
  SAPPHIRE(1),
  DIAMOND(1);

  private final int val;

  /**
   * A constructor to initialize Treasures with a certain value.
   *
   * @param value : int
   */
  Treasure(int value) {
    this.val = value;
  }

  public int getVal() {
    return this.val;
  }

  /**
   * A method to return a random treasure from all the available types of treasures.
   *
   * @param r : Random object which determines what random util to be used.
   * @return Treasure.
   */
  public static Treasure getRandomTreasure(Random r) {
    List<Treasure> pool = List.of(SAPPHIRE, RUBY, DIAMOND);
    int randomNumber = Util.randomNumberGenerator(0, pool.size() - 1, r);
    return pool.get(randomNumber);
  }
}
