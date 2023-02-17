package controller;

import controller.commands.DungeonExplorerCommandBool;
import controller.commands.GraphicalPick;
import controller.commands.GraphicalShootArrow;
import controller.commands.Move;
import controller.commands.Step;
import dungeonmodel.AnyDungeon;
import dungeonmodel.Coordinate;
import dungeonmodel.Direction;
import dungeonmodel.DungeonBuilder;
import dungeonmodel.Location;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import view.DungeonView;

/**
 * A graphical controller for the dungeon game. This controller is used to control the
 * game which will interact with the Graphical view and the model.
 */
public class DungeonGraphicalController extends MouseAdapter implements DungeonController,
    KeyListener, ActionListener {
  private AnyDungeon model;
  private final DungeonView view;
  private boolean pickInitiated;
  private boolean shootInitiated;
  private final HashMap<Integer, String> directions;

  /**
   * Constructor for the graphical controller. This has a model and a view.
   *
   * @param m : AnyDungeon model
   * @param v : DungeonView
   */
  public DungeonGraphicalController(AnyDungeon m, DungeonView v) {
    if (m == null) {
      throw new IllegalArgumentException("The model cannot be null");
    }
    if (v == null) {
      throw new IllegalArgumentException("The controller cannot be null");
    }
    this.model = m;
    this.view = v;
    this.pickInitiated = false;
    view.addActionListener(this);
    view.addKeyListener(this);
    view.addClickListener(this);
    Map<Integer, Function<AnyDungeon, DungeonExplorerCommandBool>> knownCommands = new HashMap<>();
    knownCommands.put(KeyEvent.VK_Q, d -> new GraphicalPick("asdl"));
    knownCommands.put(KeyEvent.VK_DOWN, d -> new Step("s"));
    knownCommands.put(KeyEvent.VK_UP, d -> new Step("w"));
    knownCommands.put(KeyEvent.VK_LEFT, d -> new Step("a"));
    knownCommands.put(KeyEvent.VK_RIGHT, d -> new Step("d"));
    knownCommands.put(KeyEvent.VK_A, d -> {
      if (this.pickInitiated) {
        this.pickInitiated = false;
        return new GraphicalPick("arrow");
      }
      return null;
    });
    knownCommands.put(KeyEvent.VK_T, d -> {
      if (this.pickInitiated) {
        this.pickInitiated = false;
        return new GraphicalPick("treasure");
      }
      return null;
    });
    directions = new HashMap();
    directions.put(KeyEvent.VK_DOWN, "s");
    directions.put(KeyEvent.VK_LEFT, "a");
    directions.put(KeyEvent.VK_RIGHT, "d");
    directions.put(KeyEvent.VK_UP, "w");
  }

  /**
   * A method for the play game method.
   *
   * @param m model : AnyDungeon
   */
  @Override
  public void playGame(AnyDungeon m) {
    view.updateModel(m);
  }

  private void newDungeonSize() {
    this.model = new DungeonBuilder()
        .numRows(model.getNumRows())
        .numCols(model.getNumCols())
        .interconnectivity(model.getInterconnectivity())
        .isWrapping(model.isWrapping())
        .percentageOfTreasures(model.getPercentageTreasures())
        .difficulty(model.getDifficulty())
        .seed(model.getSeed())
        .numberOfPits(model.getNumberOfPits())
        .numberOfRobbers(model.getNumberOfRobbers())
        .buildDungeon();
    this.view.newModel(this.model);
  }

  private void resetSameSeed() {
    this.model = new DungeonBuilder()
        .numRows(model.getNumRows())
        .numCols(model.getNumCols())
        .interconnectivity(model.getInterconnectivity())
        .isWrapping(model.isWrapping())
        .percentageOfTreasures(model.getPercentageTreasures())
        .difficulty(model.getDifficulty())
        .seed(model.getSeed())
        .numberOfPits(model.getNumberOfPits())
        .numberOfRobbers(model.getNumberOfRobbers())
        .buildDungeon();
    this.view.newModel(this.model);
  }

  @Override
  public void keyTyped(KeyEvent e) {
    //do nothing
  }

  @Override
  public void keyPressed(KeyEvent e) {
    StringBuilder msg = new StringBuilder();
    try {
      try {
        if (this.shootInitiated && directions.containsKey(e.getKeyCode())) {
          String dist = view.shootArrow();
          this.shootInitiated = false;
          if (new GraphicalShootArrow(directions.get(e.getKeyCode()), dist).execute(this.model)) {
            msg.append("You hear a grunt in the distance.");
          } else {
            msg.append("You shot an arrow into darkness");
          }
          view.refresh(msg.toString());
          return;
        }
      } catch (NumberFormatException nfe) {
        msg.append("\n Illegal distance");
        view.refresh(msg.toString());
        return;
      } catch (IllegalStateException | UnsupportedOperationException ise) {
        msg.append(ise.getMessage());
        view.refresh(msg.toString());
        return;
      }
      try {
        if (this.pickInitiated) {
          switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
              msg.append("Picked up ").append(model.getPlayer().getLocation().getArrows())
                  .append(" Arrows");
              new GraphicalPick("arrow").execute(model);
              break;
            case KeyEvent.VK_T:
              msg.append("Picked up ").append(model.getPlayer().getLocation().getTreasures());
              new GraphicalPick("treasure").execute(model);
              break;
            default:
              return;
          }
          view.refresh(msg.toString());
          return;
        }
      } catch (IllegalStateException ise) {
        msg.append(ise.getMessage());
        view.refresh(msg.toString());
        return;
      }
      switch (e.getKeyCode()) {
        case KeyEvent.VK_P:
          msg.append("Pick Initiated");
          this.pickInitiated = true;
          break;
        case KeyEvent.VK_S:
          msg.append("Shoot Initiated");
          this.shootInitiated = true;
          break;
        case KeyEvent.VK_UP:
          if (new Step("w").execute(model)) {
            msg.append("Player moved north");
          }
          view.setPreviousDirection(Direction.north);
          break;
        case KeyEvent.VK_DOWN:
          view.setPreviousDirection(Direction.south);
          if (new Step("s").execute(model)) {
            msg.append("Player moved south");
          }
          break;
        case KeyEvent.VK_LEFT:
          view.setPreviousDirection(Direction.west);
          if (new Step("a").execute(model)) {
            msg.append("Player moved west");
          }
          break;
        case KeyEvent.VK_RIGHT:
          view.setPreviousDirection(Direction.east);
          if (new Step("d").execute(model)) {
            msg.append("Player moved east");
          }
          break;
        default:
          return;
      }
      view.refresh(msg.toString());
    } catch (NullPointerException npe) {
      //do nothing
    } catch (IllegalStateException | IllegalArgumentException ise) {
      //donothing
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_P) {
      this.pickInitiated = false;
    }
    this.shootInitiated = false;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "resetNewSeed":
        newDungeonSize();
        break;
      case "resetSameSeed":
        resetSameSeed();
        break;
      case "custom":
        ArrayList<String> temp = (ArrayList) e.getSource();
        this.model = new DungeonBuilder()
            .numRows(Integer.parseInt(temp.get(0)))
            .numCols(Integer.parseInt(temp.get(1)))
            .interconnectivity(Integer.parseInt(temp.get(2)))
            .isWrapping(Boolean.parseBoolean(temp.get(3)))
            .percentageOfTreasures(Integer.parseInt(temp.get(4)))
            .difficulty(Integer.parseInt(temp.get(5)))
            .numberOfPits(Integer.parseInt(temp.get(6)))
            .numberOfRobbers(Integer.parseInt(temp.get(7)))
            .buildDungeon();
        this.view.newModel(this.model);
        break;
      default:
        return;
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Coordinate coordinates = (Coordinate) e.getSource();
    Location clickedLocation = new Location(coordinates.xcoordinate, coordinates.ycoordinate);
    Location playerLocation = (Location) this.model.getPlayer().getLocation();
    if (playerLocation.getNeighboursMap().containsValue(clickedLocation)) {
      Direction direction = playerLocation.getNeighboursMap().entrySet()
          .stream()
          .filter(entry -> Objects.equals(entry.getValue(), clickedLocation))
          .map(Map.Entry::getKey)
          .findFirst().get();
      switch (direction) {
        case north:
          view.refresh(new Move("w").execute(this.model));
          break;
        case south:
          view.refresh(new Move("s").execute(this.model));
          break;
        case west:
          view.refresh(new Move("a").execute(this.model));
          break;
        case east:
          view.refresh(new Move("d").execute(this.model));
          break;
        default:
          return;
      }
    }
  }
}