import static org.junit.Assert.assertEquals;

import dungeonmodel.Treasure;
import misc.CustomRandom;
import org.junit.Test;

/**
 * This class contains all the unit tests for treasure.
 */
public class TreasureTest {
  @Test
  public void testGetRandomTreasure() {
    Treasure expected = Treasure.SAPPHIRE;
    assertEquals(expected, Treasure.getRandomTreasure(new CustomRandom()));
  }
}