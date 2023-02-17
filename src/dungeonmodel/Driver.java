package dungeonmodel;


import controller.DungeonConsoleController;
import controller.DungeonController;
import controller.DungeonGraphicalController;
import java.io.InputStreamReader;
import view.DungeonGraphicalView;
import view.DungeonView;

/**
 * A driver class that is used to demonstrate the model running.
 */
public class Driver {
  /**
   * The main method that actually demonstrates the model running.
   *
   * @param args : The set of arguments for dungeon size, interconnectivity, is Wrapping and
   *             percentage of caves with treasure.
   */
  public static void main(String[] args) {
    DungeonController controller;
    AnyDungeon model;
    DungeonView view;
    try {
      if (args.length == 0) {
        model = new DungeonBuilder()
            .numberOfPits(5)
            .numberOfRobbers(2)
            .buildDungeon();
        view = new DungeonGraphicalView((ReadOnlyDungeonModel) model);
        controller = new DungeonGraphicalController(model, view);
      } else {
        String[] arguments = args[0].split(",");
        int nr = Integer.valueOf(arguments[0]);
        int nc = Integer.valueOf(arguments[1]);
        int interconnectivity = Integer.valueOf(arguments[2]);
        boolean isWrapping = Boolean.valueOf(arguments[3]);
        int percentageOfCaves = Integer.valueOf(arguments[4]);
        int difficulty = Integer.valueOf(arguments[5]);
        model = new DungeonBuilder().buildDungeon();
        Readable input = new InputStreamReader(System.in);
        Appendable output = System.out;
        controller = new DungeonConsoleController(input, output);
      }
      controller.playGame(model);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}