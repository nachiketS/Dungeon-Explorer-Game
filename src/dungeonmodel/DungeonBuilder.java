package dungeonmodel;

import java.util.Random;

/**
 * A Builder class for building dungeon.
 */
public class DungeonBuilder {
  private int interconnectivity;
  private int numRows;
  private int numCols;
  private boolean isWrapping;
  private Player player;
  private Random random;
  private int difficulty;
  private long seed;
  private int percentageOfTreasures;
  private int numberOfPits;
  private int numberOfRobbers;

  /**
   *  Constructor for initializing the dungeon builder.
   */
  public DungeonBuilder() {
    numRows = 4;
    numCols = 4;
    percentageOfTreasures = 20;
    seed = 1;
    difficulty = 1;
    random = new Random();
    isWrapping = false;
    player = new Player("steve");
    interconnectivity = 0;
    numberOfPits = 0;
    numberOfRobbers = 0;
  }

  ;

  public Dungeon buildDungeon() {
    return new Dungeon(numRows, numCols, interconnectivity, isWrapping, player, random, seed,
        percentageOfTreasures, difficulty, numberOfPits, numberOfRobbers);
  }

  public DungeonBuilder numRows(int numRows) {
    this.numRows = numRows;
    return this;
  }

  public DungeonBuilder numCols(int numCols) {
    this.numCols = numCols;
    return this;
  }

  public DungeonBuilder interconnectivity(int incoming) {
    this.interconnectivity = incoming;
    return this;
  }

  public DungeonBuilder isWrapping(boolean incoming) {
    this.isWrapping = incoming;
    return this;
  }

  public DungeonBuilder player(Player player) {
    this.player = player;
    return this;
  }

  public DungeonBuilder random(Random random) {
    this.random = random;
    return this;
  }

  public DungeonBuilder seed(long seed) {
    this.seed = seed;
    return this;
  }

  public DungeonBuilder percentageOfTreasures(int percentageOfTreasures) {
    this.percentageOfTreasures = percentageOfTreasures;
    return this;
  }

  public DungeonBuilder difficulty(int difficulty) {
    this.difficulty = difficulty;
    return this;
  }

  public DungeonBuilder numberOfPits(int incoming) {
    this.numberOfPits = incoming;
    return this;
  }


  public DungeonBuilder numberOfRobbers(int i) {
    this.numberOfRobbers = i;
    return this;
  }
}
