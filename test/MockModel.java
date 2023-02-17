import dungeonmodel.Agent;
import dungeonmodel.AnyDungeon;
import dungeonmodel.AnyLocation;
import dungeonmodel.Direction;
import dungeonmodel.Location;
import dungeonmodel.ReadOnlyDungeonModel;
import dungeonmodel.Treasure;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Creating a mock model for testing.
 */
public class MockModel implements AnyDungeon, ReadOnlyDungeonModel {
  private Appendable log;

  public MockModel(Appendable log) {
    this.log = log;
  }

  @Override
  public boolean isEnd() {
    try {
      log.append("isEnd called\n");
    } catch (IOException e) {
      // do nothing
    }
    return false;
  }

  @Override
  public List<String> nextMoves() {
    try {
      log.append("nextMoves called\n");
    } catch (IOException e) {
      // do nothing
    }
    return null;
  }

  @Override
  public Map<Location, List<Treasure>> getListOfTreasures() {
    try {
      log.append("getListOfTreasures called\n");
    } catch (IOException e) {
      // do nothing
    }
    return null;
  }

  @Override
  public String makePlayerMoveNorth() {
    try {
      log.append("player move north called\n");
    } catch (IOException e) {
      // do nothing
    }
    return null;
  }

  @Override
  public String makePlayerMoveSouth() {
    try {
      log.append("player move south called\n");
    } catch (IOException e) {
      // do nothing
    }
    return null;
  }

  @Override
  public String makePlayerMoveEast() {
    try {
      log.append("player move east called\n");
    } catch (IOException e) {
      // do nothing
    }
    return null;
  }

  @Override
  public String makePlayerMoveWest() {
    try {
      log.append("player move west called\n");
    } catch (IOException e) {
      // do nothing
    }
    return null;
  }

  @Override
  public void makePlayerPickTreasure() {
    try {
      log.append("player pick treasure called\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public List<Treasure> getTreasuresAtLocation(Location l) {

    try {
      log.append("getTreasuresAtLocation called\n");
    } catch (IOException e) {
      // do nothing
    }
    return null;
  }

  @Override
  public List<List<Location>> getGrid() {
    try {
      log.append("getGrid called\n");
    } catch (IOException e) {
      // do nothing
    }
    return null;
  }

  @Override
  public int getInterconnectivity() {
    try {
      log.append("getInterconnectivity called\n");
    } catch (IOException e) {
      // do nothing
    }
    return 0;
  }

  @Override
  public boolean isWrapping() {
    try {
      log.append("isWrapping called\n");
    } catch (IOException e) {
      // do nothing
    }
    return false;
  }

  @Override
  public int getNumRows() {
    try {
      log.append("getNumRows called\n");
    } catch (IOException e) {
      // do nothing
    }
    return 0;
  }

  @Override
  public int getNumCols() {
    try {
      log.append("getNumCols called\n");
    } catch (IOException e) {
      // do nothing
    }
    return 0;
  }

  @Override
  public Agent getPlayer() {
    try {
      log.append("getPlayer called\n");
    } catch (IOException e) {
      // do nothing
    }
    return null;
  }

  @Override
  public int getDistance(AnyLocation src, AnyLocation dest) {
    try {
      log.append("isEnd called\n");
    } catch (IOException e) {
      // do nothing
    }
    return 0;
  }

  @Override
  public List<Location> getStartEnd() {
    try {
      log.append("getStartEnd called\n");
    } catch (IOException e) {
      // do nothing
    }
    return null;
  }

  @Override
  public boolean inProgress() {
    try {
      log.append("inProgress called\n");
    } catch (IOException e) {
      // do nothing
    }
    return false;
  }

  @Override
  public String getState() {
    try {
      log.append("getState called\n");
    } catch (IOException e) {
      // do nothing
    }
    return null;
  }

  @Override
  public boolean makeplayerShootArrow(Direction dir, int dist) {
    try {
      log.append("shoot arrow called in direction " + dir + " dist :" + dist + " called\n");
    } catch (IOException e) {
      // do nothing
    }
    return false;
  }

  @Override
  public void makePlayerPickArrows() {
    try {
      log.append("makePlayerPickArrows called\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void makePlayerExitDungeon() {
    try {
      log.append("makePlayerExit called\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public Map<Treasure, Integer> getTreasuresMapAtLocation(Location l) {

    try {
      log.append("getTreasuresMapAtLocation called\n");
    } catch (IOException e) {
      // do nothing
    }
    return null;
  }

  @Override
  public long getSeed() {
    try {
      log.append("getSeed called\n");
    } catch (IOException e) {
      // do nothing
    }
    return 0;
  }

  @Override
  public int getPercentageTreasures() {
    try {
      log.append("getPercentage called\n");
    } catch (IOException e) {
      // do nothing
    }
    return 0;
  }

  @Override
  public int getDifficulty() {
    try {
      log.append("getDifficulty called\n");
    } catch (IOException e) {
      // do nothing
    }
    return 0;
  }

  @Override
  public String makePlayerMove(Direction north) {
    try {
      log.append("make player move called, direction : " + north + " called\n");
    } catch (IOException e) {
      // do nothing
    }
    return null;
  }

  @Override
  public boolean makePlayerMoveTo(Direction north) {
    try {
      log.append("make player move called, direction : " + north + " called\n");
    } catch (IOException e) {
      // do nothing
    }
    return false;
  }

  @Override
  public int getNumberOfPits() {
    try {
      log.append("getNumberOfPitsCalled called\n");
    } catch (IOException e) {
      // do nothing
    }
    return 0;
  }

  @Override
  public int getNumberOfRobbers() {
    try {
      log.append("getNumberOfRobbers called\n");
    } catch (IOException e) {
      // do nothing
    }
    return 0;
  }
}
