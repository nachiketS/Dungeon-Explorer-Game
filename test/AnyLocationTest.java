
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import dungeonmodel.Coordinate;
import dungeonmodel.Dungeon;
import dungeonmodel.Location;
import dungeonmodel.Player;
import java.util.List;
import misc.CustomRandom;
import org.junit.Before;
import org.junit.Test;


/**
 * A class to test all the public method of Any Location interface.
 */
public class AnyLocationTest {

  private Dungeon d1;
  private Location l1;

  /**
   * A setup method to set up all the object instances to be used for unit testing.
   */
  @Before
  public void setup() {
    Player p1;
    l1 = new Location(0, 0);
    p1 = new Player("Steve");
    d1 = new Dungeon(3, 3, 0, true, p1, new CustomRandom(3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorIllegalInput1() {
    new Location((Location) null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorIllegalInput2() {
    new Location((Coordinate) null);
  }

  @Test
  public void getCoordinate() {
    assertEquals(new Coordinate(0, 0), l1.getCoOrdinates());
  }

  @Test
  public void isCave() {
    assertTrue(l1.isCave());
  }

  @Test
  public void getNeighbours() {
    assertEquals(List.of(new Location(0, 2)), d1.getGrid().get(0).get(0).getNeighbours());
  }

  @Test
  public void getNorth() {
    assertEquals(new Location(2, 2), d1.getGrid().get(0).get(2).getNorth());
  }

  @Test
  public void getSouth() {
    assertEquals(new Location(1, 2), d1.getGrid().get(0).get(2).getSouth());
  }

  @Test
  public void getEast() {
    assertEquals(new Location(0, 0), d1.getGrid().get(0).get(2).getEast());
  }

  @Test
  public void getWest() {
    assertEquals(new Location(0, 1), d1.getGrid().get(0).get(2).getWest());
  }

  @Test
  public void testGetNeighboursMap() {
    assertEquals(new Location(0, 2), d1.getGrid().get(0).get(0).getWest());
  }
}