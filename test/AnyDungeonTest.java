import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import dungeonmodel.Agent;
import dungeonmodel.Direction;
import dungeonmodel.Dungeon;
import dungeonmodel.Location;
import dungeonmodel.Player;
import dungeonmodel.Treasure;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import misc.CustomRandom;
import org.junit.Before;
import org.junit.Test;


/**
 * A class to test all the public methods of the Any Dungeon interface.
 */
public class AnyDungeonTest {

  Dungeon d1;
  Dungeon d2;
  Player p1;
  Random r1;

  /**
   * A set up method to set up the object instances to be used for testing.
   */
  @Before
  public void setUp() {
    r1 = new CustomRandom(2);
    p1 = new Player("Steve");
    d1 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 20, 1);
    d2 = new Dungeon(5, 4, 1, false, p1,
        new CustomRandom(0));
  }

  @Test
  public void testConstructorWhenCorrect() {
    assertEquals(3, d1.getNumRows());
    assertEquals(3, d1.getNumCols());
    assertEquals(0, d1.getInterconnectivity());
    assertEquals(true, d1.isWrapping());
    assertEquals("Steve", p1.getName());
  }

  @Test
  public void getNumRows() {
    assertEquals(5, d2.getNumRows());
    assertEquals(3, d1.getNumRows());
  }

  @Test
  public void getNumCols() {
    assertEquals(4, d2.getNumCols());
    assertEquals(3, d1.getNumCols());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorIllegalRowsCols1() {
    new Dungeon(0, 3, 1, true, p1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorIllegalRowsCols2() {
    new Dungeon(3, 0, 1, true, p1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorIllegalRowsCols3() {
    new Dungeon(-1, 3, 1, true, p1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorIllegalRowsCols4() {
    new Dungeon(3, -3, 1, true, p1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorIllegalRandom() {
    new Dungeon(3, 3, 0, true, p1,
        null, 1, 20, 1);
  }


  @Test
  public void getInterconnectivity() {
    assertEquals(0, d1.getInterconnectivity());
    assertEquals(1, d2.getInterconnectivity());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNegativeInterconnectivity() {
    new Dungeon(3, 3, -1, true, p1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorHigherInterconnectivity() {
    new Dungeon(3, 3, 9, false, p1);
  }

  @Test
  public void nextMoves() {
    System.out.println(d2);
    System.out.println(d2.getStartEnd());
    System.out.println(d2.getPlayer().getLocation());
    System.out.println(d2.getPlayer().getLocation().getNeighboursMap());
    assertEquals(List.of("d", "a", "s"), d2.nextMoves());
  }

  @Test
  public void getListOfTreasures() {
    int percentageOfTreasures = 20;
    d1 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, percentageOfTreasures, 1);
    int numCaves = 0;
    for (List<Location> row : d1.getGrid()) {
      for (Location col : row) {
        if (col.isCave()) {
          numCaves++;
        }
      }
    }
    int numCavesWithTreasures = (int) Math.round(percentageOfTreasures * numCaves * 0.01);
    assertTrue(numCavesWithTreasures <= d1.getListOfTreasures().size());
  }

  @Test
  public void getListOfTreasuresWhen100() {
    int percentageOfTreasures = 100;
    d2 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, percentageOfTreasures, 1);
    int numCaves = 0;
    for (List<Location> row : d2.getGrid()) {
      for (Location col : row) {
        if (col.isCave()) {
          numCaves++;
        }
      }
    }
    int numCavesWithTreasures = (int) Math.round(percentageOfTreasures * numCaves * 0.01);
    assertTrue(numCavesWithTreasures <= d2.getListOfTreasures().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void addTreasureIllegalInputTest1() {
    new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 200, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void addTreasureIllegalInputTest2() {
    new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, -20, 1);
  }


  @Test
  public void getTreasuresAtLocation() {
    List<List<Location>> grid = d2.getGrid();
    d2 = new Dungeon(4, 4, 0, true, p1,
        new CustomRandom(1), 1, 20, 1);
    System.out.println(d2.getListOfTreasures());
    assertEquals(List.of(Treasure.RUBY, Treasure.RUBY),
        d2.getTreasuresAtLocation((Location) grid.get(0).get(1)));
    assertEquals(List.of(), d2.getTreasuresAtLocation(grid.get(0).get(0)));
  }

  @Test
  public void getGrid() {
    List<List<Location>> grid = d2.getGrid();
    assertEquals("[[Tunnel: [0, 0], Cave: [0, 1], Cave: [0, 2], Tunnel: [0, 3]], "
        + "[Cave: [1, 0], Cave: [1, 1], Tunnel: [1, 2], Tunnel: [1, 3]], "
        + "[Tunnel: [2, 0], Tunnel: [2, 1], Tunnel: [2, 2], Tunnel: [2, 3]], "
        + "[Tunnel: [3, 0], Tunnel: [3, 1], Tunnel: [3, 2], Tunnel: [3, 3]], "
        + "[Cave: [4, 0], Cave: [4, 1], Cave: [4, 2], Cave: [4, 3]]]", grid.toString());
  }

  @Test
  public void isWrapping() {
    assertEquals(true, d1.isWrapping());
    assertEquals(false, d2.isWrapping());
  }

  @Test
  public void testStartAndEndDist() {
    d2 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 20, 1);
    assertTrue(d1.getDistance(d1.getStartEnd().get(0), d1.getStartEnd().get(1)) >= 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testStartAndEndDistNull() {
    d2 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 20, 1);
    assertTrue(d1.getDistance(null, null) >= 5);
  }


  @Test
  public void testKruskalMinSpanningTree() {
    Dungeon d3 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 20, 1);
    System.out.println(d3);
    /*
     * A spanning tree has all the nodes connected by atleast one neighbour, i.e. no node is left,
        all nodes are covered.
     * The minimum edges required to connect all the nodes in a spanning tree is 1 less than the
     * number of nodes in the tree.
     * So if the total number of neighbours in the grid for all locations is twice of 1 less than
       number of locations, that would mean that the structure is a spanning tree.
     */
    List<List<Location>> grid = d3.getGrid();
    List<Location> unvisitedLocations = new ArrayList<>();
    for (List<Location> row : grid) {
      for (Location loc : row) {
        unvisitedLocations.add(loc);
      }
    }
    int numberOfNeighbours = 0;
    for (Location loc : unvisitedLocations) {
      numberOfNeighbours += loc.getNeighbours().size();
    }
    unvisitedLocations.remove(d3.getPlayer().getLocation());
    ArrayList<String> moves =
        new ArrayList<String>(List.of("w", "d", "a", "w", "s", "a", "w", "s", "s", "a", "s"));
    for (String move : moves) {
      switch (move) {
        case "w":
          d3.makePlayerMoveNorth();
          break;
        case "a":
          d3.makePlayerMoveWest();
          break;
        case "s":
          d3.makePlayerMoveSouth();
          break;
        case "d":
          d3.makePlayerMoveEast();
          break;
        default:
          break;
      }

      if (unvisitedLocations.contains(d3.getPlayer().getLocation())) {
        unvisitedLocations.remove(d3.getPlayer().getLocation());

      }
    }
    assertEquals(16, numberOfNeighbours);
  }

  @Test
  public void exactlyOneTreasure() {
    Dungeon d3 = new Dungeon(4, 4, 0, true, p1,
        new CustomRandom(0), 1, 20, 1);
    assertEquals(1, d3.getTreasuresAtLocation(d3.getGrid().get(0).get(1)).size());
  }

  @Test
  public void moreThanOneTreasurePerLocation() {
    Dungeon d1 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 50, 1);
    assertTrue(d1.getTreasuresAtLocation(d1.getGrid().get(0).get(0)).size() > 1);
  }

  @Test
  public void playerPickingUpTreasure() {
    d1 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 20, 1);
    Map<Location, List<Treasure>> cavesWithTreasures = d1.getListOfTreasures();
    Map<Treasure, Integer> exp = new HashMap<>(Map.of(Treasure.DIAMOND, 0, Treasure.SAPPHIRE,
        0, Treasure.RUBY, 0));
    exp.put(Treasure.DIAMOND, 4);
    assertEquals(new HashMap<>(Map.of(Treasure.DIAMOND, 4)),
        d1.getTreasuresMapAtLocation(d1.getGrid().get(1).get(2)));
    d1.makePlayerPickTreasure();
    assertEquals(exp, p1.getTreasures());
    assertEquals(new HashMap<>(), d1.getTreasuresMapAtLocation(d1.getGrid().get(1).get(2)));
  }

  @Test(expected = IllegalStateException.class)
  public void playerPickingUpTreasureWhenNull() {
    d1 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 20, 1);
    Map<Location, List<Treasure>> cavesWithTreasures = d1.getListOfTreasures();
    assertEquals(new HashMap<>(), d1.getTreasuresMapAtLocation(d1.getGrid().get(0).get(2)));
    d1.makePlayerMoveNorth();
    d1.makePlayerPickTreasure();
    assertEquals(new HashMap<>(), d1.getTreasuresMapAtLocation(d1.getGrid().get(0).get(2)));
    assertEquals(new HashMap<>(), p1.getTreasures());
  }


  @Test
  public void testIsWrappingByMovingPlayer() {
    d1 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 20, 1);
    assertEquals(new Location(1, 2), d1.getPlayer().getLocation());
    d1.makePlayerMoveNorth();
    assertEquals(new Location(0, 2), d1.getPlayer().getLocation());
    d1.makePlayerMoveNorth();
    assertEquals(new Location(2, 2), d1.getPlayer().getLocation());
  }

  @Test
  public void getPlayer() {
    assertEquals("name='Steve', currLoc=Cave: [1, 2],"
            + " treasuresHeld={DIAMOND=0, SAPPHIRE=0, RUBY=0}",
        d1.getPlayer().toString());
  }

  @Test
  public void getStartEnd() {
    d1 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 20, 1);
    assertEquals(List.of(new Location(1, 2), new Location(2, 0)), d1.getStartEnd());
  }

  @Test(expected = IllegalStateException.class)
  public void checkNonWrappingDungeon() {
    d2 = new Dungeon(3, 3, 0, false, p1,
        new CustomRandom(3), 1, 20, 1);
    d2.makePlayerMoveWest();
    assertEquals(new Location(2, 0), d2.getPlayer().getLocation());
  }

  @Test
  public void checkPlayerCanVisitAllLocationsInDungeon() {
    // Getting all the locations in the graph and then making the player move through a predefined
    // path. As the player visits a path, the location gets removed from the list of unvisited
    // locations. If the unvisitedLocations list is empty, that indicates that the player was able
    // to visit all locations.
    d1 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 20, 1);
    List<List<Location>> grid = d1.getGrid();
    List<Location> unvisitedLocations = new ArrayList<>();
    for (List<Location> row : grid) {
      for (Location loc : row) {
        unvisitedLocations.add(loc);
      }
    }
    unvisitedLocations.remove(d1.getPlayer().getLocation());
    ArrayList<String> moves =
        new ArrayList<String>(List.of("w", "d", "a", "w", "s", "a", "w", "s", "s", "a", "s"));
    for (String move : moves) {
      switch (move) {
        case "w":
          d1.makePlayerMoveNorth();
          break;
        case "a":
          d1.makePlayerMoveWest();
          break;
        case "d":
          d1.makePlayerMoveEast();
          break;
        case "s":
          d1.makePlayerMoveSouth();
          break;
        default:
          break;
      }
      if (unvisitedLocations.contains(d1.getPlayer().getLocation())) {
        unvisitedLocations.remove(d1.getPlayer().getLocation());
      }
    }
    assertTrue(unvisitedLocations.isEmpty());
  }

  @Test
  public void testIfPlayerCanMoveFromStartToEnd() {
    Dungeon d3 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 20, 1);
    System.out.println(d3);
    ArrayList<String> moves = new ArrayList<>(List.of("w", "a", "s", "a", "s"));
    assertEquals(d3.getStartEnd().get(0), d3.getPlayer().getLocation());
    for (String move : moves) {
      switch (move) {
        case "w":
          d3.makePlayerMoveNorth();
          break;
        case "a":
          d3.makePlayerMoveWest();
          break;
        case "d":
          d3.makePlayerMoveEast();
          break;
        case "s":
          d3.makePlayerMoveSouth();
          break;
        default:
          break;
      }
    }
    assertTrue(d3.isEnd());
  }

  @Test
  public void testInterconnectivityIntheCreatedGraphwhen0() {
    Dungeon d3 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 20, 1);
    /*
     * A spanning tree has all the nodes connected by atleast one neighbour, i.e. no node is left,
        all nodes are covered.
     * The minimum edges required to connect all the nodes in a spanning tree is 1 less than the
     * number of nodes in the tree.
     * So if the total number of neighbours in the grid for all locations is twice of 1 less than
       number of locations, that would mean that the structure is a spanning tree.
     */
    System.out.println(d3);
    List<List<Location>> grid = d3.getGrid();
    List<Location> unvisitedLocations = new ArrayList<>();
    for (List<Location> row : grid) {
      for (Location loc : row) {
        unvisitedLocations.add(loc);
      }
    }
    int numberOfNeighbours = 0;
    for (Location loc : unvisitedLocations) {
      numberOfNeighbours += loc.getNeighbours().size();
    }
    unvisitedLocations.remove(d3.getPlayer().getLocation());
    ArrayList<String> moves =
        new ArrayList<String>(List.of("w", "w", "s", "d", "a", "a", "w", "s", "s", "a"));
    for (String move : moves) {
      switch (move) {
        case "w":
          d3.makePlayerMoveNorth();
          break;
        case "a":
          d3.makePlayerMoveWest();
          break;
        case "d":
          d3.makePlayerMoveEast();
          break;
        case "s":
          d3.makePlayerMoveSouth();
          break;
        default:
          break;
      }
      if (unvisitedLocations.contains(d3.getPlayer().getLocation())) {
        unvisitedLocations.remove(d3.getPlayer().getLocation());

      }
    }
    assertEquals(16, numberOfNeighbours);

  }

  @Test
  public void testInterconnectivityIntheCreatedGraphwhenNonZero() {
    Dungeon d3 = new Dungeon(3, 3, 0, false, p1,
        new CustomRandom(0), 10, 20, 0);
    System.out.println(d3);
    /*
       * A spanning tree has all the nodes connected by atleast one neighbour, i.e. no node is left,
          all nodes are covered.
       * The minimum edges required to connect all the nodes in a spanning tree is 1 less than the
       * number of nodes in the tree.
       * So if the total number of neighbours in the grid for all locations is twice of 1 less than
         number of locations, that would mean that the structure is a spanning tree.
       * Further adding the interconnectivity*2 neighbours and asserting that the number is equal.
     */
    List<List<Location>> grid = d3.getGrid();
    List<Location> unvisitedLocations = new ArrayList<>();
    for (List<Location> row : grid) {
      for (Location loc : row) {
        unvisitedLocations.add(loc);
      }
    }

    int numberOfNeighbours = 0;
    for (Location loc : unvisitedLocations) {
      numberOfNeighbours += loc.getNeighbours().size();
    }
    unvisitedLocations.remove(d3.getPlayer().getLocation());
    ArrayList<String> moves =
        new ArrayList<String>(List.of("w", "w", "d", "s", "w", "d", "s", "s"));
    for (String move : moves) {
      switch (move) {
        case "w":
          d3.makePlayerMoveNorth();
          break;
        case "a":
          d3.makePlayerMoveWest();
          break;
        case "d":
          d3.makePlayerMoveEast();
          break;
        case "s":
          d3.makePlayerMoveSouth();
          break;
        default:
          break;
      }
      if (unvisitedLocations.contains(d3.getPlayer().getLocation())) {
        unvisitedLocations.remove(d3.getPlayer().getLocation());
      }
    }
    assertEquals(16, numberOfNeighbours);
  }

  @Test
  public void checkMonstersInDungeon() {
    d2 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 20, 3);
    int numCaves = 0;
    for (List<Location> row : d2.getGrid()) {
      for (Location col : row) {
        if (col.isCave()) {
          numCaves++;
        }
      }
    }
    int numCavesWithMonsters = 0;
    List<List<Location>> grid = d2.getGrid();
    for (List<Location> row : grid) {
      for (Location col : row) {
        if (col.hasMonster()) {
          numCavesWithMonsters++;
        }
      }
    }
    int numCavesWithTreasures = (int) Math.round(20 * numCaves * 0.01);
    int numCavesWithMonstersExpected = (int) Math.round(3 * 10 * numCaves * 0.01);
    assertTrue(numCavesWithTreasures <= d2.getListOfTreasures().size());
    assertTrue(numCavesWithMonstersExpected <= numCavesWithMonsters);
  }

  @Test
  public void checkNoMonsterInTheStartCave() {
    d2 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 20, 1);
    assertFalse(d2.getStartEnd().get(0).hasMonster());
  }

  @Test
  public void checkMonsterInEndCave() {
    d2 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 20, 1);
    assertTrue(d2.getStartEnd().get(1).hasMonster());
  }

  @Test
  public void checkAddedArrows() {
    int percentageOfTreasures = 20;
    int difficulty = 1;
    int numLocationsWithArrows = 0;
    d2 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, percentageOfTreasures, difficulty);
    for (List<Location> row : d2.getGrid()) {
      for (Location l : row) {
        if (l.getArrows() != 0) {
          numLocationsWithArrows++;
        }
      }
    }
    int minLocationsWithArrows = (int) Math.round(percentageOfTreasures * d2.getNumRows()
        * d2.getNumCols() * 0.01);
    assertTrue(minLocationsWithArrows <= numLocationsWithArrows);
  }


  @Test
  public void testMultipleMonstersSmellInteractionInWrappingDungeon() {
    Agent p1 = new Player("Steve");
    d2 = new Dungeon(3, 5, 0, true, p1,
        new CustomRandom(5), 1, 20, 2);
    System.out.println(d2);
    assertTrue(d2.getGrid().get(1).get(3).getMonster() != null);
    assertTrue(d2.getGrid().get(1).get(4).getMonster() != null);
    assertTrue(d2.getGrid().get(2).get(0).getMonster() != null);
    assertTrue(d2.getGrid().get(2).get(1).getMonster() != null);

    assertTrue(d2.getGrid().get(1).get(0).getSmell() >= 2);
    assertTrue(d2.getGrid().get(1).get(1).getSmell() >= 2);
    assertTrue(d2.getGrid().get(0).get(3).getSmell() >= 2);
    assertTrue(d2.getGrid().get(0).get(4).getSmell() >= 2);

    assertTrue(d2.getGrid().get(0).get(1).getSmell() == 1);
    assertTrue(d2.getGrid().get(0).get(2).getSmell() == 1);
    assertTrue(d2.getGrid().get(0).get(0).getSmell() == 1);
    assertTrue(d2.getGrid().get(2).get(3).getSmell() == 1);
    assertTrue(d2.getGrid().get(2).get(4).getSmell() == 1);
  }

  @Test
  public void testWrappingSmellInteraction() {
    Agent p1 = new Player("Steve");
    d2 = new Dungeon(3, 5, 0, true, p1,
        new CustomRandom(5), 1, 20, 2);
    System.out.println(d2);
    assertTrue(d2.getGrid().get(1).get(3).getMonster() != null);
    assertTrue(d2.getGrid().get(1).get(4).getMonster() != null);
    assertTrue(d2.getGrid().get(2).get(0).getMonster() != null);
    assertTrue(d2.getGrid().get(2).get(1).getMonster() != null);

    assertTrue(d2.getGrid().get(1).get(0).getSmell() >= 2);
    assertTrue(d2.getGrid().get(1).get(1).getSmell() >= 2);
    assertTrue(d2.getGrid().get(0).get(3).getSmell() >= 2);
    assertTrue(d2.getGrid().get(0).get(4).getSmell() >= 2);

    assertTrue(d2.getGrid().get(0).get(1).getSmell() == 1);
    assertTrue(d2.getGrid().get(0).get(2).getSmell() == 1);
    assertTrue(d2.getGrid().get(0).get(0).getSmell() == 1);
    assertTrue(d2.getGrid().get(2).get(3).getSmell() == 1);
    assertTrue(d2.getGrid().get(2).get(4).getSmell() == 1);
  }

  @Test
  public void testTheNumberOfMonstersInDungeonDifficulty1() {
    int numMonstersAdded = 0;
    for (List<Location> row : d1.getGrid()) {
      for (Location col : row) {
        if (col.hasMonster()) {
          numMonstersAdded++;
        }
      }
    }
    assertEquals(1, numMonstersAdded);
  }

  @Test
  public void testTheNumberOfMonstersInDungeonDifficulty2() {
    d1 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 20, 2);
    int numMonstersAdded = 0;
    for (List<Location> row : d1.getGrid()) {
      for (Location col : row) {
        if (col.hasMonster()) {
          numMonstersAdded++;
        }
      }
    }
    assertEquals(2, numMonstersAdded);
  }

  @Test
  public void testTheNumberOfMonstersInDungeonDifficulty3() {
    d1 = new Dungeon(3, 3, 0, true, p1,
        new CustomRandom(3), 1, 20, 3);
    int numMonstersAdded = 0;
    for (List<Location> row : d1.getGrid()) {
      for (Location col : row) {
        if (col.hasMonster()) {
          numMonstersAdded++;
        }
      }
    }
    assertEquals(4, numMonstersAdded);
  }

  @Test
  public void makePlayerMoveNorth() {
    System.out.println(d1);
    d1.makePlayerMoveNorth();
    assertEquals(d1.getPlayer().getLocation(), d1.getGrid().get(0).get(2));
  }

  @Test
  public void makePlayerMoveSouth() {
    System.out.println(d1);
    d1.makePlayerMoveNorth();
    assertEquals(d1.getPlayer().getLocation(), d1.getGrid().get(0).get(2));
    d1.makePlayerMoveSouth();
    assertEquals(d1.getPlayer().getLocation(), d1.getGrid().get(1).get(2));
  }

  @Test
  public void makePlayerMoveWest() {
    System.out.println(d1);
    d1.makePlayerMoveNorth();
    assertEquals(d1.getPlayer().getLocation(), d1.getGrid().get(0).get(2));
    d1.makePlayerMoveWest();
    assertEquals(d1.getPlayer().getLocation(), d1.getGrid().get(0).get(1));
  }

  @Test
  public void makePlayerMoveEast() {
    System.out.println(d1);
    d1.makePlayerMoveNorth();
    assertEquals(d1.getPlayer().getLocation(), d1.getGrid().get(0).get(2));
    d1.makePlayerMoveEast();
    assertEquals(d1.getPlayer().getLocation(), d1.getGrid().get(0).get(0));
  }

  @Test
  public void makePlayerPickTreasure() {
    System.out.println(d1.getState());
    Map<Treasure, Integer> expected = new HashMap<>(Map.of(Treasure.DIAMOND, 0,
        Treasure.RUBY, 0, Treasure.SAPPHIRE, 0));
    assertEquals(expected, d1.getPlayer().getTreasures());
    expected.put(Treasure.DIAMOND, 4);
    d1.makePlayerPickTreasure();
    assertEquals(expected, d1.getPlayer().getTreasures());
    assertEquals(new HashMap<>(),
        d1.getTreasuresMapAtLocation((Location) d1.getPlayer().getLocation()));
  }

  @Test
  public void makePlayerShootArrow() {
    d1.makePlayerMoveNorth();
    d1.makePlayerMoveWest();
    System.out.println(d1);
    System.out.println(d1.getState());
    assertEquals(d1.getGrid().get(2).get(0).getMonster().getHealth(), 2);
    System.out.println(d1.makeplayerShootArrow(Direction.south, 1));
    System.out.println(d1);
    assertEquals(d1.getGrid().get(2).get(0).getMonster().getHealth(), 1);

  }

  @Test
  public void monsterTakingDamage() {
    System.out.println(d1);
    System.out.println(d1.getState());
    d1.makePlayerMoveNorth();
    d1.makePlayerMoveWest();
    assertEquals(d1.getGrid().get(2).get(0).getMonster().getHealth(), 2);
    System.out.println(d1.makeplayerShootArrow(Direction.south, 1));
    System.out.println(d1);
    assertEquals(d1.getGrid().get(2).get(0).getMonster().getHealth(), 1);
  }

  @Test
  public void monsterDyingWrapping() {
    d1 = new Dungeon(4, 4, 2, true, p1, new CustomRandom(3), 1, 20, 2);
    System.out.println(d1);
    System.out.println(d1.getState());
    assertEquals(d1.getGrid().get(0).get(3).getMonster().getHealth(), 2);
    d1.makeplayerShootArrow(Direction.west, 1);
    assertEquals(d1.getGrid().get(0).get(3).getMonster().getHealth(), 1);
    d1.makeplayerShootArrow(Direction.west, 1);
    assertFalse(d1.getGrid().get(0).get(3).hasMonster());
  }

  @Test
  public void monsterDyingNonWrapping() {
    System.out.println(d1);
    System.out.println(d1.getState());
    d1.makePlayerMoveNorth();
    d1.makePlayerMoveWest();
    assertEquals(d1.getGrid().get(2).get(0).getMonster().getHealth(), 2);
    d1.makeplayerShootArrow(Direction.south, 1);
    assertEquals(d1.getGrid().get(2).get(0).getMonster().getHealth(), 1);
    d1.makeplayerShootArrow(Direction.south, 1);
    assertFalse(d1.getGrid().get(2).get(0).hasMonster());
  }

  @Test
  public void monsterDyingSmellRemoval() {

    System.out.println(d1);
    d1.makePlayerMoveNorth();
    System.out.println(d1);
    d1.makePlayerMoveWest();
    System.out.println(d1);
    System.out.println(d1.getGrid().get(2).get(0).getMonster().getHealth());
    assertEquals(d1.getGrid().get(2).get(0).getMonster().getHealth(), 2);
    d1.makeplayerShootArrow(Direction.south, 1);
    System.out.println(d1.getGrid().get(2).get(0).getMonster().getHealth());
    System.out.println(d1.getGrid().get(2).get(0).getMonster());
    d1.makeplayerShootArrow(Direction.south, 1);
    assertFalse(d1.getGrid().get(2).get(0).hasMonster());
    assertEquals(0, d1.getGrid().get(1).get(0).getSmell());
    assertEquals(0, d1.getGrid().get(1).get(1).getSmell());
  }

  @Test
  public void monsterTakingDamageWrappedDungeon() {
    d1 = new Dungeon(4, 4, 2, true, p1, new CustomRandom(3), 1, 20, 2);
    System.out.println(d1);
    System.out.println(d1.getState());
    assertEquals(d1.getGrid().get(0).get(3).getMonster().getHealth(), 2);
    d1.makeplayerShootArrow(Direction.west, 1);
    assertEquals(d1.getGrid().get(0).get(3).getMonster().getHealth(), 1);
    d1.makeplayerShootArrow(Direction.west, 1);
    assertFalse(d1.getGrid().get(0).get(3).hasMonster());
  }

  @Test
  public void monsterDyingAfterMoving() {
    d1 = new Dungeon(4, 4, 2, true, p1, new CustomRandom(3), 1, 20, 2);
    System.out.println(d1);
    System.out.println(d1.getState());
    assertEquals(d1.getGrid().get(0).get(3).getMonster().getHealth(), 2);
    d1.makeplayerShootArrow(Direction.west, 1);
    d1.makePlayerMoveWest();
    assertEquals(d1.getGrid().get(0).get(3).getMonster().getHealth(), 1);
    d1.makeplayerShootArrow(Direction.north, 1);
    assertFalse(d1.getGrid().get(0).get(3).hasMonster());
  }

  @Test
  public void testPlayerPickingArrows() {
    d1 = new Dungeon(4, 4, 2, true, p1, new CustomRandom(3), 1, 100, 2);
    assertEquals(3, d1.getPlayer().getNumArrows());
    d1.makePlayerPickArrows();
    assertEquals(6, d1.getPlayer().getNumArrows());
  }

  @Test
  public void testCurvingArrows() {

    System.out.println(d1);
    d1.makePlayerMoveNorth();
    d1.makePlayerMoveWest();
    assertEquals(2, d1.getGrid().get(2).get(0).getMonster().getHealth());
    d1.makeplayerShootArrow(Direction.south, 1);
    assertEquals(1, d1.getGrid().get(2).get(0).getMonster().getHealth());
    d1.makeplayerShootArrow(Direction.south, 1);
    assertFalse(d1.getGrid().get(2).get(0).hasMonster());
  }


  @Test
  public void testNumArrowsAdded() {
    int expectedNumLocationsWithArrows = (int) Math.round(d1.getNumRows() * d1.getNumCols() * 0.2);
    int actualNumLocaitonsWithArrows = 0;
    for (List<Location> row : d1.getGrid()) {
      for (Location col : row) {
        if (col.getArrows() != 0) {
          actualNumLocaitonsWithArrows++;
        }
      }
    }
    System.out.println(d1);
    assertTrue(expectedNumLocationsWithArrows <= actualNumLocaitonsWithArrows);
  }

  @Test
  public void testGetState() {
    System.out.println(d1.getState());
    assertEquals(new StringBuilder()
        .append("You are in a cave\n")
        .append("Paths lead to : [north]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 4 DIAMOND, press p to pickup.").toString(), d1.getState());
  }

  @Test
  public void checkMonsterEatingPlayer() {
    Dungeon d4 = new Dungeon(3, 4, 0, false, p1,
        new CustomRandom(0), 1, 20, 3);
    System.out.println(d4);
    d4.makePlayerMoveNorth();
    d4.makePlayerMoveNorth();
    d4.makePlayerMoveEast();
    assertFalse(d4.inProgress());
  }

  @Test
  public void checkMonsterNotEatingPlayer() {
    Dungeon d4 = new Dungeon(3, 4, 0, false, p1,
        new CustomRandom(1), 1, 20, 3);

    System.out.println(d4);
    assertEquals(2, d4.getGrid().get(0).get(1).getMonster().getHealth());
    d4.makeplayerShootArrow(Direction.north, 1);
    assertEquals(1, d4.getGrid().get(0).get(1).getMonster().getHealth());
    d4.makePlayerMoveNorth();
    assertTrue(d4.inProgress());
  }

  @Test
  public void testArrowMissingInvalidDistance() {

    System.out.println(d1);
    d1.makePlayerMoveNorth();
    d1.makePlayerMoveWest();
    d1.makeplayerShootArrow(Direction.south, 2);
    assertEquals(2, d1.getGrid().get(2).get(0).getMonster().getHealth());
    d1.makeplayerShootArrow(Direction.south, 1);
    assertEquals(1, d1.getGrid().get(2).get(0).getMonster().getHealth());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArrowDistance() {
    d1.makeplayerShootArrow(Direction.north, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidArrowDirection() {
    d1.makeplayerShootArrow(null, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDifficulty() {
    Dungeon d4 = new Dungeon(3, 4, 0, false, p1,
        new CustomRandom(1), 1, 20, 5);
  }
}
