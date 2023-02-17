//todo remove begin ?
//todo check 100% treasures


package dungeonmodel;

import static dungeonmodel.Direction.east;
import static dungeonmodel.Direction.north;
import static dungeonmodel.Direction.south;
import static dungeonmodel.Direction.west;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A class to represent our Dungeon. This has all the methods which are expected from any dungeon.
 */
public class Dungeon implements AnyDungeon {
  private int interconnectivity;
  private int numRows;
  private int numCols;
  private boolean isWrapping;
  private Player player;
  private Graph graphGrid;
  private boolean inProgress;
  private List<Location> startEnd;
  private int difficulty;
  private static final int OTYUGHHEALTH = 2;
  private static final int DEFAULTDIFFICULTY = 2;
  private long seed;
  private int percentageOfTreasures;
  private int numberOfPits;
  private int numberOfRobbers;


  public Dungeon(int nr, int nc, int interconnectivity, boolean isWrapping, Agent p) {
    this(nr, nc, interconnectivity, isWrapping, p, new Random());
  }

  /**
   * A constructor for the dungeon class which takes random as an extra parameter. To be used for
   * testing
   *
   * @param nr                : int
   * @param nc                : int
   * @param interconnectivity : int
   * @param isWrapping        : boolean
   * @param p                 : Player
   * @param random            : Random
   */
  public Dungeon(int nr, int nc, int interconnectivity, boolean isWrapping, Agent p,
                 Random random, long seed, int percentageOfTreasures, int difficulty,
                 int numberOfPits, int numberOfRobbers) {
    if (nr <= 0) {
      throw new IllegalArgumentException("The number of rows cannot be zero or negative");
    }
    if (nc <= 0) {
      throw new IllegalArgumentException("The number of cols cannot be zero or negative");
    }
    if (interconnectivity < 0) {
      throw new IllegalArgumentException("The interconnectivity cannot be negative");
    }
    int maxNonWrappingEdges = nr * (nc - 1) + nc * (nr - 1) - (nr * nc - 1);
    if (!isWrapping && interconnectivity > maxNonWrappingEdges) {
      throw new IllegalArgumentException("Given interconnectivity is impossible for this graph");
    }
    if (isWrapping && interconnectivity > (maxNonWrappingEdges + nc + nr)) {
      throw new IllegalArgumentException("Given interconnectivity is impossible for this graph");
    }
    if (random == null) {
      throw new IllegalArgumentException("Random object cannot be null");
    }
    this.numberOfPits = numberOfPits;
    this.numberOfRobbers = numberOfRobbers;
    this.seed = seed;
    this.interconnectivity = interconnectivity;
    this.numCols = nc;
    this.numRows = nr;
    this.isWrapping = isWrapping;
    this.player = (Player) p;
    random.setSeed(this.seed);
    this.graphGrid = new Graph(nr, nc, interconnectivity, isWrapping, random);
    this.inProgress = false;
    startEnd = new ArrayList<>();
    this.player = new Player(p);
    this.percentageOfTreasures = percentageOfTreasures;
    this.difficulty = difficulty;
    this.begin(this.percentageOfTreasures, this.difficulty);
  }

  public Dungeon(int nr, int nc, int interconnectivity, boolean isWrapping, Agent p,
                 Random random, long seed, int percentageOfTreasures, int difficulty) {
    this(nr, nc, interconnectivity, isWrapping, p, random, seed, percentageOfTreasures,
        difficulty, 0, 0);
  }

  public Dungeon(int nr, int nc, int interconnectivity, boolean isWrapping, Agent p,
                 Random random) {
    this(nr, nc, interconnectivity, isWrapping, p, random, random.nextInt(100), 20, 1);
  }

  public Dungeon(int nr, int nc, int interconnectivity, boolean isWrapping, Random random) {
    this(nr, nc, interconnectivity, isWrapping, new Player("Steve"), random, random.nextInt(100),
        20, 1);
  }

  public Dungeon(int nr, int nc, int interconnectivity, boolean isWrapping) {
    this(nr, nc, interconnectivity, isWrapping, new Player("Steve"), new Random(),
        new Random().nextInt(100), 20, 1);
  }

  public Dungeon(int numRows, int numCols, int interconnectivity, boolean wrapping, long seed) {
    this(numRows, numCols, interconnectivity, wrapping, new Player("steve"), new Random(),
        seed, 20, 1);
  }

  public Dungeon(int nr, int nc, int interconnectivity, boolean isWrapping, Agent p,
                 int percentageOfCaves, int difficulty) {
    this(nr, nc, interconnectivity, isWrapping, p, new Random(), new Random().nextInt(100),
        percentageOfCaves, difficulty);
  }

  public Dungeon(int nr, int nc, int interconnectivity, boolean isWrapping,
                 int percentageOfTreasures, int difficulty) {
    this(nr, nc, interconnectivity, isWrapping, new Player("arya"), new Random(),
        new Random().nextInt(100), percentageOfTreasures, difficulty);
  }

  private void begin(int percentageOfTreasures, int difficulty) {
    if (percentageOfTreasures > 100 || percentageOfTreasures < 0) {
      throw new IllegalArgumentException("Percentage of treasures should be between 0 - 100");
    }
    this.startEnd = this.graphGrid.getRandomizedStartEnd();
    player.enterDungeon(startEnd.get(0));
    addTreasure(percentageOfTreasures);
    addArrows(percentageOfTreasures);
    addMonsters(difficulty);
    this.graphGrid.addPits(this.numberOfPits);
    this.graphGrid.addRobbers(this.numberOfRobbers);
    this.inProgress = true;
  }


  private void addArrows(int percentageOfTreasures) {
    this.graphGrid.addArrows(percentageOfTreasures);
  }

  private void addMonsters(int percentageOfMonsters) {
    this.graphGrid.addMonsters(percentageOfMonsters);
  }

  @Override
  public boolean isEnd() {
    return this.player.getLocation().equals(startEnd.get(1));
  }

  @Override
  public List<String> nextMoves() {
    if (!inProgress) {
      throw new IllegalStateException("The game has not yet started");
    }
    ArrayList<String> moves = new ArrayList<>();
    AnyLocation inLocation = playersLocationInGrid();
    Location loc = (Location) inLocation;
    if (loc.getNeighboursMap().get(north) != null) {
      moves.add("w");
    }
    if (loc.getNeighboursMap().get(Direction.east) != null) {
      moves.add("d");
    }
    if (loc.getNeighboursMap().get(west) != null) {
      moves.add("a");
    }
    if (loc.getNeighboursMap().get(south) != null) {
      moves.add("s");
    }
    return moves;
  }

  private AnyLocation playersLocationInGrid() {
    Location loc = (Location) player.getLocation();
    return this.graphGrid.grid
        .get(loc.getCoOrdinates().xcoordinate).get(loc.getCoOrdinates().ycoordinate);
  }


  private void addTreasure(int percentageOfTreasures) {
    if (percentageOfTreasures == 0) {
      return;
    }
    this.graphGrid.addTreasure(percentageOfTreasures);
  }

  @Override
  public Map<Location, List<Treasure>> getListOfTreasures() {
    Map<Location, List<Treasure>> treasureMap = new HashMap<>();
    for (List<Location> row : this.graphGrid.grid) {
      for (Location l : row) {
        if (!getTreasuresAtLocation(l).isEmpty()) {
          treasureMap.put(l, getTreasuresAtLocation(l));
        }
      }
    }
    return treasureMap;
  }

  @Override
  public String makePlayerMoveNorth() {
    if (!this.inProgress) {
      throw new IllegalStateException("The game has ended");
    }
    if (player.getLocation().getNeighboursMap().get(north) == null) {
      throw new IllegalStateException("Can't move there");
    }
    player.moveToNorth();
    if (player.getLocation().hasMonster()) {
      if (this.getMonterAtLocation((Location) player.getLocation()).eat()) {
        this.inProgress = false;
        return "Monster";
      }
      return "Escape";
    }
    return "Player moved North";
  }

  @Override
  public String makePlayerMoveSouth() {
    if (!this.inProgress) {
      throw new IllegalStateException("The game has ended");
    }
    if (player.getLocation().getNeighboursMap().get(south) == null) {
      throw new IllegalStateException("Can't move there");
    }
    player.moveToSouth();
    if (player.getLocation().hasMonster()) {
      if (this.getMonterAtLocation((Location) player.getLocation()).eat()) {
        this.inProgress = false;
        return "Monster";
      }
      return "Escape";
    }
    return "Player moved South";
  }

  @Override
  public String makePlayerMoveEast() {
    if (!this.inProgress) {
      throw new IllegalStateException("The game has ended");
    }
    if (player.getLocation().getNeighboursMap().get(east) == null) {
      throw new IllegalStateException("Can't move there");
    }
    player.moveToEast();
    if (player.getLocation().hasMonster()) {
      if (this.getMonterAtLocation((Location) player.getLocation()).eat()) {
        this.inProgress = false;
        return "Monster";
      }
      return "Escape";
    }
    return "Player moved East";
  }

  @Override
  public String makePlayerMoveWest() {
    if (!this.inProgress) {
      throw new IllegalStateException("The game has ended");
    }
    if (player.getLocation().getNeighboursMap().get(west) == null) {
      throw new IllegalStateException("Can't move there");
    }
    player.moveToWest();
    if (player.getLocation().hasMonster()) {
      if (this.getMonterAtLocation((Location) player.getLocation()).eat()) {
        this.inProgress = false;
        return "Monster";
      }
      return "Escape";
    }
    return "Player moved west";
  }

  @Override
  public String makePlayerMove(Direction direction) {
    if (!this.inProgress) {
      throw new IllegalStateException("Game not in Progress");
    }
    if (player.getLocation().getNeighboursMap().get(direction) == null) {
      throw new IllegalStateException("Can't move there");
    }

    player.move(direction);
    if (player.getLocation().hasMonster()) {
      if (this.getMonterAtLocation((Location) player.getLocation()).eat()) {
        this.inProgress = false;
        return "Monster";
      }
      return "Escape";
    }
    if (player.getLocation().hasPit()) {
      this.inProgress = false;
      return "Pit";
    }
    if (player.getLocation().hasRobber()) {
      this.robberStealsTreasure();
      return "Robber";
    }
    return "Player moved " + direction;
  }

  /**
   * A method to make player move. This takes in a direction and gives out
   * a boolean that represents the status of the move action.
   *
   * @param direction : Direction
   * @return : boolean
   */
  public boolean makePlayerMoveTo(Direction direction) {
    if (!this.inProgress) {
      throw new IllegalStateException("Game not in Progress");
    }
    if (player.getLocation().getNeighboursMap().get(direction) == null) {
      throw new IllegalStateException("Can't move there");
    }

    if (player.getLocation().hasRobber()) {
      this.robberStealsTreasure();
    }
    player.move(direction);
    if (player.getLocation().hasMonster()) {
      if (this.getMonterAtLocation((Location) player.getLocation()).eat()) {
        this.inProgress = false;
        return false;
      }
    }
    if (player.getLocation().hasPit()) {
      this.inProgress = false;
      return false;
    }
    return true;
  }

  @Override
  public void makePlayerPickTreasure() {
    if (!this.inProgress) {
      throw new IllegalStateException("The game has ended");
    }
    if (getTreasuresMapAtLocation((Location) player.getLocation()).isEmpty()) {
      throw new IllegalStateException("No treasures at the location");
    }
    player.pickTreasure(getTreasuresMapAtLocation((Location) player.getLocation()));
    getLocationInGrid((Location) player.getLocation()).clearTreasure();
  }

  private Location getLocationInGrid(Location incomingCopy) {
    return this.graphGrid.grid.get(incomingCopy.getCoOrdinates().xcoordinate)
        .get(incomingCopy.getCoOrdinates().ycoordinate);
  }

  @Override
  public List<Treasure> getTreasuresAtLocation(Location l) {
    l = getLocationInGrid(l);
    List<Treasure> toRet = l.getTreasureList();

    return toRet;
  }

  @Override
  public Map<Treasure, Integer> getTreasuresMapAtLocation(Location l) {
    return getLocationInGrid(l).getTreasures();
  }

  @Override
  public List<List<Location>> getGrid() {
    List<List<Location>> result = new ArrayList<>(numRows);
    for (int i = 0; i < numRows; i++) {
      result.add(i, new ArrayList<>());
      for (int j = 0; j < numCols; j++) {
        result.get(i).add(j, new Location(graphGrid.grid.get(i).get(j)));
      }
    }
    return result;
  }

  @Override
  public int getInterconnectivity() {
    return interconnectivity;
  }

  @Override
  public boolean isWrapping() {
    return isWrapping;
  }

  @Override
  public int getNumRows() {
    return numRows;
  }

  @Override
  public int getNumCols() {
    return numCols;
  }

  @Override
  public Agent getPlayer() {
    return new Player(player);
  }

  @Override
  public String toString() {
    //    List<Edge> allEdges = getAllEdges();
    StringBuilder toRet = new StringBuilder();
    for (int i = 0; i < numRows; i++) {
      // first loop for all north connections
      for (int currCol = 0; currCol < numCols; currCol++) {
        if (graphGrid.grid.get(i).get(currCol).getNeighboursMap().get(north) == null) {
          toRet.append("   ");
        } else {
          toRet.append(" | ");
        }
      }
      toRet.append("\n");
      // second loop for all the horizontal.
      for (int j = 0; j < numCols; j++) {
        if (graphGrid.grid.get(i).get(j).getNeighboursMap().get(west) == null) {
          toRet.append(" ");
        } else {
          toRet.append("-");
        }
        if (graphGrid.grid.get(i).get(j).hasMonster()) {
          toRet.append("M");
        } else if (graphGrid.grid.get(i).get(j).equals(playersLocationInGrid())) {
          toRet.append("P");
        } else {
          toRet.append("O");
        }
        if (graphGrid.grid.get(i).get(j).getNeighboursMap().get(east) == null) {
          toRet.append(" ");
        } else {
          toRet.append("-");
        }
      }
      toRet.append("\n");
      // third loop for all the south connections.
      for (int currCol = 0; currCol < numCols; currCol++) {
        if (graphGrid.grid.get(i).get(currCol).getNeighboursMap().get(south) == null) {
          toRet.append("   ");
        } else {
          toRet.append(" | ");
        }
      }
      toRet.append("\n");
    }
    for (Edge e : graphGrid.remEdges) {
      toRet.append(String.format("[[%d, %d],[%d, %d]] \n", e.src.getCoOrdinates().xcoordinate,
          e.src.getCoOrdinates().ycoordinate, e.dest.getCoOrdinates().xcoordinate,
          e.dest.getCoOrdinates().ycoordinate));
    }
    return toRet.toString();
  }

  @Override
  public int getDistance(AnyLocation src, AnyLocation dest) {
    if (src == null || dest == null) {
      throw new IllegalArgumentException("neither src nor dest can be null");
    }
    if (src.getCoOrdinates().xcoordinate > this.numRows
        || src.getCoOrdinates().ycoordinate > this.numCols
        || dest.getCoOrdinates().xcoordinate > this.numRows
        || dest.getCoOrdinates().ycoordinate > this.numCols) {
      throw new IllegalArgumentException("both locations must be present in the dungeon.");
    }
    return this.graphGrid.printShortestDistance(src, dest);
  }

  @Override
  public List<Location> getStartEnd() {
    if (!inProgress) {
      throw new IllegalStateException("The game hasn't started yet. "
          + "Use begin method to start the game");
    }
    return startEnd;
  }

  @Override
  public boolean inProgress() {
    return inProgress;
  }

  @Override
  public String getState() {
    StringBuilder sb = new StringBuilder();
    if (player.getLocation().isCave()) {
      sb.append("You are in a cave");
    } else {
      sb.append("You are in a tunnel");
    }
    sb.append("\nPaths lead to : " + player.getLocation().getNeighboursMap().keySet());
    //    sb.append("Player's Location : " + player.getLocation());
    sb.append("\nPlayer's Treasures : " + this.getPlayer().getTreasures());
    sb.append("\nPlayer's arrows : " + this.getPlayer().getNumArrows());
    //    sb.append(getStartEnd());
    if (player.getLocation().getArrows() > 0) {
      sb.append("\nYou found " + this.getPlayer().getLocation().getArrows()
          + " arrows, press p to pickup.");
    }
    if (player.getLocation().getTreasures().values().stream().reduce(0, Integer::sum) > 0) {
      sb.append("\nYou found "
          + formatTreasures(getTreasuresMapAtLocation((Location) this.getPlayer().getLocation()))
          + " press p to pickup.");
    }
    if (player.getLocation().getSmell() > 0) {
      if (player.getLocation().getSmell() == 1) {
        sb.append("\nYou smell danger far away");
      } else {
        sb.append("\nYou smell grave danger nearby");
      }
    }
    if (player.getLocation().hasMonster()) {
      sb.append("\nYou found an Otyugh !!!");
      if (getMonterAtLocation((Location) player.getLocation()).eat()) {
        sb.append("\n Nom Nom Nom, The Otyugh ate you !!!");
        this.inProgress = false;
      } else {
        sb.append("\nYou dodged the injured Otyugh's attack.");
      }
    }
    if (player.getLocation().equals(this.startEnd.get(1))) {
      sb.append("\n Press E to exit the dungeon.");
    }

    return sb.toString();
  }

  private String formatTreasures(Map<Treasure, Integer> m) {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry e : m.entrySet()) {
      sb.append(e.getValue()).append(" ").append(e.getKey()).append(",");
    }
    return sb.toString();
  }


  public Monster getMonterAtLocation(Location l) {
    l = this.getLocationInGrid(l);
    return l.getMonster();
  }

  @Override
  public boolean makeplayerShootArrow(Direction dir, int dist) {
    if (!this.inProgress) {
      throw new IllegalStateException("The game has ended");
    }
    Arrow releasedArrow = this.player.releaseArrow(dir, dist);
    releasedArrow.curr = getLocationInGrid((Location) releasedArrow.curr);
    if (this.player.getLocation().getNeighboursMap().get(dir) == null) {
      throw new IllegalStateException("Arrow hit the wall. You wasted an arrow");
    }
    Location landingLocation = releasedArrow.arrowStopsAt();
    if (landingLocation != null && landingLocation.hasMonster()) {
      return getLocationInGrid(landingLocation).monsterTakeDamage(releasedArrow.getDamage());
    }
    return false;
  }

  @Override
  public void makePlayerPickArrows() {
    if (!this.inProgress) {
      throw new IllegalStateException("The game has ended");
    }
    if (this.getPlayer().getLocation().getArrows() == 0) {
      throw new IllegalStateException("No arrows to pick up");
    }
    this.player.pickArrows();
    getLocationInGrid((Location) this.getPlayer().getLocation()).removeArrows();
  }

  @Override
  public void makePlayerExitDungeon() {
    if (!this.inProgress) {
      throw new IllegalStateException("The game has ended");
    }
    if (!this.getPlayer().getLocation().equals(getStartEnd().get(1))) {
      throw new IllegalStateException("Not the ending dungeon.");
    }
    this.inProgress = false;
  }

  @Override
  public long getSeed() {
    return this.seed;
  }

  @Override
  public int getPercentageTreasures() {
    return this.percentageOfTreasures;
  }

  @Override
  public int getDifficulty() {
    return this.difficulty;
  }

  public void robberStealsTreasure() {
    this.player.removeTreasure();
  }

  @Override
  public int getNumberOfPits() {
    return this.numberOfPits;
  }

  @Override
  public int getNumberOfRobbers() {
    return this.numberOfRobbers;
  }
}
