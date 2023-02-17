package controller;

import controller.commands.DungeonExplorerCommand;
import controller.commands.Exit;
import controller.commands.Move;
import controller.commands.Pick;
import controller.commands.ShootArrow;
import dungeonmodel.AnyDungeon;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

/**
 * A class to represent the text-based dungeon.
 */
public class DungeonConsoleController implements DungeonController {
  private final Appendable out;
  private final Scanner scan;
  private final Map<String, Function<AnyDungeon, DungeonExplorerCommand>> knownCommands;

  /**
   * A constructor class for the controller.
   *
   * @param in  Input
   * @param out Output
   */
  public DungeonConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.out = out;
    scan = new Scanner(in);


    knownCommands = new HashMap<>();
    knownCommands.put("m", d -> {
      String input = stringScanHelper(d.nextMoves().toString());
      if (input == null) {
        return null;
      }
      return new Move(input);
    });
    knownCommands.put("p", d -> {
      String input = stringScanHelper("pick t for treasure a for arrows");
      if (input == null) {
        return null;
      }
      return new Pick(input);
    });
    knownCommands.put("s", d -> {
      String dir = stringScanHelper("Select direction to shoot arrow " + d.nextMoves());
      if (dir == null) {
        return null;
      }
      String dist = stringScanHelper("Enter distance between [1-5]");
      if (dist == null) {
        return null;
      }
      return new ShootArrow(dir, dist);
    });
    knownCommands.put("e", d -> new Exit());
  }

  @Override
  public void playGame(AnyDungeon m) {
    if (m == null) {
      throw new IllegalArgumentException(" The model cannot be null");
    }
    while (m.inProgress()) {
      try {
        DungeonExplorerCommand c;
        out.append("\n").append(m.getState());
        out.append("\nSelect next move [m, p, s] : ");
        String in = scan.next();
        if (in.equalsIgnoreCase("q") || in.equalsIgnoreCase("quit")) {
          out.append("Game Ended.");
          return;
        }
        Function<AnyDungeon, DungeonExplorerCommand> cmd = knownCommands.getOrDefault(in.trim(),
                null);
        try {
          if (cmd == null) {
            throw new IllegalArgumentException("\nCommand not found");
          } else {
            c = cmd.apply(m);
            if (c == null) {
              out.append("Game Ended.");
              return;
            }
            String output = c.execute(m);
            if (output.equalsIgnoreCase("q")) {
              out.append("Game Ended.");
              return;
            }
            if (output.equals("Monster")) {
              out.append("Monster ate you !");
              return;
            }
            if (output.equals("Escape")) {
              out.append("You escaped the monster ");
              return;
            }
            out.append(output).append("\n");
          }
        } catch (NumberFormatException exc) {
          out.append("Not a valid number ");
        } catch (IllegalArgumentException | IllegalStateException
                | UnsupportedOperationException e) {
          out.append(e.getMessage()).append("\n");
        }
      } catch (IOException e) {
        throw new IllegalStateException("Append failed" + e);
      }
    }
  }

  private String stringScanHelper(String s) {
    try {
      out.append(s).append(" : ");
    } catch (IOException e) {
      throw new IllegalStateException("append failed");
    }
    String inp = scan.next();
    if (inp.equalsIgnoreCase("q")) {
      return null;
    }
    return inp;
  }



}


