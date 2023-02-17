import static org.junit.Assert.assertEquals;

import controller.DungeonConsoleController;
import dungeonmodel.Agent;
import dungeonmodel.AnyDungeon;
import dungeonmodel.Dungeon;
import dungeonmodel.Player;
import java.io.InputStreamReader;
import java.io.StringReader;
import misc.CustomRandom;
import org.junit.Before;
import org.junit.Test;

/**
 * A test class to test the controller public methods.
 */
public class DungeonControllerTest {
  private AnyDungeon model;
  private Readable input;
  private Appendable output;

  /**
   * A method to set up all the required instances for testing.
   */
  @Before
  public void setUp() {
    Player p1 = new Player("steve");
    input = new InputStreamReader(System.in);
    output = new StringBuilder();
    model = new Dungeon(4, 4, 2, false, p1, new CustomRandom(0), 1, 20, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void playGame() {
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(null);
  }

  @Test
  public void testMoveInput() {
    StringReader input = new StringReader("m q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    assertEquals(new StringBuilder()
            .append("\n")
            .append("You are in a cave\n")
            .append("Paths lead to : [east, south, west]\n")
            .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
            .append("Player's arrows : 3\n")
            .append("You found 1 arrows, press p to pickup.\n")
            .append("You found 1 SAPPHIRE, press p to pickup.\n")
            .append("Select next move [m, p, s] : [d, a, s] : Game Ended.").toString(),
        output.toString());
  }

  @Test
  public void testPickInput() {
    StringReader input = new StringReader("p q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    assertEquals(new StringBuilder()
            .append("\n")
            .append("You are in a cave\n")
            .append("Paths lead to : [east, south, west]\n")
            .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
            .append("Player's arrows : 3\n")
            .append("You found 1 arrows, press p to pickup.\n")
            .append("You found 1 SAPPHIRE, press p to pickup.\n")
            .append("Select next move [m, p, s] : "
                + "pick t for treasure a for arrows : Game Ended.").toString(),
        output.toString());
  }

  @Test
  public void testShootInput() {

    StringReader input = new StringReader("s d 2 q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : Select direction to shoot arrow [d, a, s] :")
        .append(" Enter distance between [1-5] : You hear a roar in the distance\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 2\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void nonMoveShootPickInput() {

    StringReader input = new StringReader("g q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : \n")
        .append("Command not found\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void testQuit() {

    StringReader input = new StringReader("q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void testMoveNonDirection() {

    StringReader input = new StringReader("m g q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : [d, a, s] : Illegal Move\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void testMoveDirectionNoPath() {

    StringReader input = new StringReader("m w q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : [d, a, s] : Can't move there\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void testMoveNorth() {

    StringReader input = new StringReader("m s m w q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : [d, a, s] : Player moved south\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, north, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("Select next move [m, p, s] : [w, d, a, s] : Player moved north\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void testMoveEast() {

    StringReader input = new StringReader("m a q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : [d, a, s] : Player moved west\n")
        .append("\n")
        .append("You are in a tunnel\n")
        .append("Paths lead to : [east, south]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void testMoveSouth() {

    StringReader input = new StringReader("m s q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : [d, a, s] : Player moved south\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, north, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void testMoveWest() {

    StringReader input = new StringReader("m a q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : [d, a, s] : Player moved west\n")
        .append("\n")
        .append("You are in a tunnel\n")
        .append("Paths lead to : [east, south]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void testPickNoTreasure() {

    StringReader input = new StringReader("p t q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : ")
        .append("pick t for treasure a for arrows :")
        .append(" Player picked {SAPPHIRE=1}\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=1, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void testPickNoArrows() {

    StringReader input = new StringReader("m s p a q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : [d, a, s] : Player moved south\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, north, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("Select next move [m, p, s] : ")
        .append("pick t for treasure a for arrows : No arrows to pick up\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, north, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void testPickArrow() {

    StringReader input = new StringReader("p a q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : ")
        .append("pick t for treasure a for arrows : Player picked 1\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 4\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void testPickTreasure() {

    StringReader input = new StringReader("p t q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : ")
        .append("pick t for treasure a for arrows : Player picked {SAPPHIRE=1}\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=1, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void testShootWhenLegal() {

    StringReader input = new StringReader("s d 2 q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : Select direction to shoot arrow [d, a, s] : ")
        .append("Enter distance between [1-5] : You hear a roar in the distance\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 2\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void testShootNonDirection() {

    StringReader input = new StringReader("s f 2 q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : ")
        .append("Select direction to shoot arrow [d, a, s] : ")
        .append("Enter distance between [1-5] : Invalid direction\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void testShootIllegalDistance() {

    StringReader input = new StringReader("s d -2 q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : ")
        .append("Select direction to shoot arrow [d, a, s] : ")
        .append("Enter distance between [1-5] : ")
        .append("Enter distance between 1 and 5 [1-5]\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 2\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void playerSmelling() {
    Agent p1 = new Player("steve");
    model = new Dungeon(4, 4, 2, false, p1, new CustomRandom(0), 1, 20, 3);
    StringReader input = new StringReader("q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("You smell grave danger nearby\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void playerKilledByOtyugh() {
    model = new Dungeon(4, 4, 2, false, new Player("SAD"), new CustomRandom(0), 1, 20, 3);
    StringReader input = new StringReader("m a m s 2 q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("You smell grave danger nearby\n")
        .append("Select next move [m, p, s] : [d, a, s] : Player moved west\n")
        .append("\n")
        .append("You are in a tunnel\n")
        .append("Paths lead to : [east, south]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You smell grave danger nearby\n")
        .append("Select next move [m, p, s] : [d, s] : Monster ate you !").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void playerAbleToExitDungeon() {
    model = new Dungeon(4, 4, 2, false, new Player("steve"), new CustomRandom(0), 1, 20, 3);
    StringReader input = new StringReader("s d 2 s d 2 m d m d m s m s m s e");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("You smell grave danger nearby\n")
        .append("Select next move [m, p, s] : ")
        .append("Select direction to shoot arrow [d, a, s] : ")
        .append("Enter distance between [1-5] : ")
        .append("You hear a roar in the distance\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 2\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("You smell grave danger nearby\n")
        .append("Select next move [m, p, s] : ")
        .append("Select direction to shoot arrow [d, a, s] : ")
        .append("Enter distance between [1-5] : ")
        .append("You hear a roar in the distance\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 1\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("You smell grave danger nearby\n")
        .append("Select next move [m, p, s] : [d, a, s] : Monster ate you !").toString();
    assertEquals(expected, output.toString());
  }

  @Test
  public void playerDodgingOtyugh() {
    AnyDungeon model2 = new Dungeon(3, 4, 0, false,
        new Player("p1"), new CustomRandom(1), 1, 20, 3);
    StringReader input = new StringReader("s w 1 m w q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model2);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [north, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 2 RUBY, press p to pickup.\n")
        .append("You smell grave danger nearby\n")
        .append("Select next move [m, p, s] : Select direction to shoot arrow [w, a, s] :")
        .append(" Enter distance between [1-5] : You hear a roar in the distance\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [north, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 2\n")
        .append("You found 2 RUBY, press p to pickup.\n")
        .append("You smell grave danger nearby\n")
        .append("Select next move [m, p, s] : [w, a, s] : You escaped the monster ").toString();
    assertEquals(expected, output.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void faililngAppendable() {

    StringReader input = new StringReader("s d 2 q");
    Appendable gameLog = new FailingAppendable();
    DungeonConsoleController c = new DungeonConsoleController(input, gameLog);
    c.playGame(model);

  }

  @Test
  public void playerShootingWhenOutOfArrows() {

    StringReader input = new StringReader("s d 2 q");
    DungeonConsoleController c = new DungeonConsoleController(input, output);
    c.playGame(model);
    String expected = new StringBuilder()
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 3\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : Select direction to shoot arrow [d, a, s] : ")
        .append("Enter distance between [1-5] : You hear a roar in the distance\n")
        .append("\n")
        .append("You are in a cave\n")
        .append("Paths lead to : [east, south, west]\n")
        .append("Player's Treasures : {DIAMOND=0, SAPPHIRE=0, RUBY=0}\n")
        .append("Player's arrows : 2\n")
        .append("You found 1 arrows, press p to pickup.\n")
        .append("You found 1 SAPPHIRE, press p to pickup.\n")
        .append("Select next move [m, p, s] : Game Ended.").toString();
    assertEquals(expected, output.toString());
  }
}