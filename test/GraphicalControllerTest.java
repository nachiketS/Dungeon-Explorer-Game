import static org.junit.Assert.assertEquals;

import controller.DungeonGraphicalController;
import dungeonmodel.AnyDungeon;
import dungeonmodel.Coordinate;
import dungeonmodel.Dungeon;
import dungeonmodel.Player;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import misc.CustomRandom;
import org.junit.Before;
import org.junit.Test;
import view.DungeonView;

/**
 * Class for graphical controller testing.
 */
public class GraphicalControllerTest {

  StringBuilder log;
  DungeonView view;
  AnyDungeon model;
  AnyDungeon actualModel;
  DungeonGraphicalController controller;

  /**
   * The method to set up the attributes required for testing.
   */
  @Before
  public void setup() {
    log = new StringBuilder();
    view = new MockView(log);
    model = new MockModel(log);
    actualModel = new Dungeon(6, 6, 2, false,
        new Player("nachiket"), new CustomRandom(0), 1, 20, 1);
    controller = new DungeonGraphicalController(model, view);
  }

  @Test
  public void testMenuButtonCustom() {
    controller.playGame(model);
    ActionEvent event = new ActionEvent(new ArrayList<String>(
        List.of("4", "4", "0", "false", "20", "1", "3", "3")), 1, "custom");
    controller.actionPerformed(event);
    String expected = new StringBuilder()
        .append("action listener controller added\n")
        .append("key listener controller added\n")
        .append("click listener controller added\n")
        .append("update model called\n")
        .append("new model called\n").toString();
    assertEquals(expected, log.toString());
  }

  @Test
  public void testPlayGame() {
    controller.playGame(model);
    String expected = String.format(new StringBuilder()
        .append("action listener controller added\n")
        .append("key listener controller added\n")
        .append("click listener controller added\n")
        .append("update model called\n").toString());
    assertEquals(expected, log.toString());
  }


  @Test
  public void testDirectionalKeyPressed() {
    DungeonGraphicalController controller = new DungeonGraphicalController(actualModel, view);
    controller.playGame(actualModel);
    KeyEvent eventUp = new KeyEvent(
        new JFrame(), 1, System.currentTimeMillis(), 0, KeyEvent.VK_UP, '↑');
    KeyEvent eventDown = new KeyEvent(
        new JFrame(), 1, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, '↑');
    KeyEvent eventLeft = new KeyEvent(
        new JFrame(), 1, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, '↑');
    KeyEvent eventRight = new KeyEvent(
        new JFrame(), 1, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, '↑');
    controller.keyPressed(eventUp);
    controller.keyPressed(eventDown);
    controller.keyPressed(eventLeft);
    controller.keyPressed(eventRight);
    String expected = new StringBuilder()
        .append("action listener controller added\n")
        .append("key listener controller added\n")
        .append("click listener controller added\n")
        .append("action listener controller added\n")
        .append("key listener controller added\n")
        .append("click listener controller added\n")
        .append("update model called\n")
        .append("set Previous Direction called\n")
        .append("refreshed, status = Player moved south\n")
        .append("set Previous Direction called\n")
        .append("refreshed, status = Player moved west\n")
        .append("set Previous Direction called\n")
        .append("refreshed, status = Player moved east\n").toString();
    assertEquals(expected, log.toString());
  }

  @Test
  public void testButtonExit() {
    controller.playGame(model);
    ActionEvent event = new ActionEvent(this, 1, "exit");
    controller.actionPerformed(event);
    String expected = new StringBuilder()
        .append("action listener controller added\n")
        .append("key listener controller added\n")
        .append("click listener controller added\n")
        .append("update model called\n").toString();
    assertEquals(expected, log.toString());
  }

  @Test
  public void keyPressedShootInitiated() {
    DungeonGraphicalController controller = new DungeonGraphicalController(actualModel, view);
    controller.playGame(actualModel);
    KeyEvent eventShoot = new KeyEvent(new JFrame(), 1,
        System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');
    KeyEvent eventUp = new KeyEvent(new JFrame(), 1,
        System.currentTimeMillis(), 0, KeyEvent.VK_UP, '↑');
    controller.keyPressed(eventShoot);
    controller.keyPressed(eventUp);
    String expected = new StringBuilder()
        .append("action listener controller added\n")
        .append("key listener controller added\n")
        .append("click listener controller added\n")
        .append("action listener controller added\n")
        .append("key listener controller added\n")
        .append("click listener controller added\n")
        .append("update model called\n")
        .append("refreshed, status = Shoot Initiated\n")
        .append("Shooting initiated\n")
        .append("refreshed, status = \n")
        .append(" Illegal distance\n").toString();
    assertEquals(expected, log.toString());
  }

  @Test
  public void keyPressedPickArrows() {
    controller.playGame(model);
    KeyEvent eventPick = new KeyEvent(new JFrame(), 1,
        System.currentTimeMillis(), 0, KeyEvent.VK_P, 'P');
    KeyEvent eventArrow = new KeyEvent(new JFrame(), 1,
        System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');
    controller.keyPressed(eventPick);
    controller.keyPressed(eventArrow);
    String expected = new StringBuilder()
        .append("action listener controller added\n")
        .append("key listener controller added\n")
        .append("click listener controller added\n")
        .append("update model called\n")
        .append("refreshed, status = Pick Initiated\n")
        .append("getPlayer called\n").toString();
    assertEquals(expected, log.toString());
  }

  @Test
  public void keyPressedPickTreasures() {
    DungeonGraphicalController controller = new DungeonGraphicalController(actualModel, view);
    controller.playGame(actualModel);
    KeyEvent eventPick = new KeyEvent(new JFrame(), 1, System.currentTimeMillis(),
        0, KeyEvent.VK_P, 'P');
    KeyEvent eventTreasure = new KeyEvent(new JFrame(), 1, System.currentTimeMillis(),
        0, KeyEvent.VK_T, 'T');
    controller.keyPressed(eventPick);
    controller.keyPressed(eventTreasure);
    String expected = new StringBuilder()
        .append("action listener controller added\n")
        .append("key listener controller added\n")
        .append("click listener controller added\n")
        .append("action listener controller added\n")
        .append("key listener controller added\n")
        .append("click listener controller added\n")
        .append("update model called\n")
        .append("refreshed, status = Pick Initiated\n")
        .append("refreshed, status = Picked up {SAPPHIRE=1}\n").toString();
    assertEquals(expected, log.toString());
  }

  @Test
  public void resetSameDungeonTest() {
    DungeonGraphicalController controller1 = new DungeonGraphicalController(actualModel, view);
    controller1.playGame(actualModel);
    ActionEvent event = new ActionEvent(new ArrayList<String>(List.of(
        "4", "4", "0", "false", "20", "1", "3", "3")), 1, "resetSameSeed");
    controller1.actionPerformed(event);
    String expected = new StringBuilder()
        .append("action listener controller added\n")
        .append("key listener controller added\n")
        .append("click listener controller added\n")
        .append("action listener controller added\n")
        .append("key listener controller added\n")
        .append("click listener controller added\n")
        .append("update model called\n")
        .append("new model called\n").toString();

    assertEquals(expected, log.toString());
  }

  @Test
  public void resetNewDungeonTest() {
    DungeonGraphicalController controller1 = new DungeonGraphicalController(actualModel, view);
    controller1.playGame(actualModel);
    ActionEvent event = new ActionEvent(this, 1, "resetNewSeed");
    controller1.actionPerformed(event);
    String expected = new StringBuilder()
        .append("action listener controller added\n")
        .append("key listener controller added\n")
        .append("click listener controller added\n")
        .append("action listener controller added\n")
        .append("key listener controller added\n")
        .append("click listener controller added\n")
        .append("update model called\n")
        .append("new model called\n").toString();

    assertEquals(expected, log.toString());
  }

  @Test
  public void testMouseClickOnPanel() {
    DungeonGraphicalController controller = new DungeonGraphicalController(actualModel, view);
    controller.playGame(actualModel);
    MouseEvent event = new MouseEvent(
        new JFrame(),
        1,
        System.identityHashCode(this),
        0,
        0,
        0,
        1,
        false,
        MouseEvent.BUTTON1);
    event.setSource(new Coordinate(0, 0));
    controller.mouseClicked(event);
    String expected = new StringBuilder()
        .append("action listener controller added\n")
        .append("key listener controller added\n")
        .append("click listener controller added\n")
        .append("action listener controller added\n")
        .append("key listener controller added\n")
        .append("click listener controller added\n")
        .append("update model called\n")
        .append("refreshed, status = Player moved west\n").toString();
    assertEquals(expected, log.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    DungeonGraphicalController controller = new DungeonGraphicalController(null, view);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullController() {
    DungeonGraphicalController controller = new DungeonGraphicalController(model, null);
  }
}
