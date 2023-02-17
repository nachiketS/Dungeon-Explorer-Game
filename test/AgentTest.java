
import static org.junit.Assert.assertEquals;

import dungeonmodel.Dungeon;
import dungeonmodel.Location;
import dungeonmodel.Player;
import dungeonmodel.Treasure;
import java.util.HashMap;
import java.util.Map;
import misc.CustomRandom;
import org.junit.Before;
import org.junit.Test;


/**
 * A class to test all the public methods of the interface Agent.
 */
public class AgentTest {

  Player p1;
  Dungeon d1;
  Dungeon d2;

  /**
   * A setup method to set up all the object instances to be used for unit testing.
   */
  @Before
  public void setUp() {
    p1 = new Player("Steve");
    d1 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3));
    d2 = new Dungeon(5, 4, 1, false, p1,
        new CustomRandom(0));
  }

  @Test
  public void getName() {
    assertEquals("Steve", p1.getName());
  }

  @Test
  public void enterDungeon() {
    assertEquals(new Location(1, 2), d1.getPlayer().getLocation());
  }

  @Test
  public void getLocation() {
    assertEquals(new Location(1, 2), d1.getPlayer().getLocation());
  }

  @Test(expected = IllegalStateException.class)
  public void getLocationWhenNotInDungeon() {
    assertEquals(new Location(1, 2), p1.getLocation());
  }

  @Test
  public void testGetTreasures() {
    d1 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 20, 100, 1);
    assertEquals(new HashMap<>(
        Map.of(Treasure.DIAMOND, 0, Treasure.SAPPHIRE, 0, Treasure.RUBY, 0)), p1.getTreasures());
    d1.makePlayerMoveNorth();
    d1.makePlayerMoveNorth();
    d1.makePlayerPickTreasure();
    HashMap<Treasure, Integer> exp = new HashMap<>(Map.of(Treasure.DIAMOND, 0, Treasure.SAPPHIRE, 0,
        Treasure.RUBY, 0));
    exp.put(Treasure.DIAMOND, 4);
    assertEquals(exp, p1.getTreasures());
  }

  @Test
  public void testGetTreasuresWhenNotBegin() {
    assertEquals(new HashMap<>(
        Map.of(Treasure.DIAMOND, 0, Treasure.SAPPHIRE, 0, Treasure.RUBY, 0)), p1.getTreasures());
  }

  @Test
  public void testToString() {
    assertEquals("name='Steve',"
            + " currLoc=Cave: [1, 2], treasuresHeld={DIAMOND=0, SAPPHIRE=0, RUBY=0}",
        d1.getPlayer().toString());
  }

}