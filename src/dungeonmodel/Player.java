package dungeonmodel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A class to represent a player that moves through the dungeon.
 */
public class Player implements Agent {
  private static final int MAXARROWDIST = 5;
  private static final int MINARROWDIST = 1;
  private String name;
  private Location currLoc;
  private Map<Treasure, Integer> treasuresHeld;
  //  private ArrayList<Treasure> treasuresHeld;
  private boolean inDungeon;
  private List<Arrow> quiver;

  /**
   * A constructor method to initialize a player object.
   *
   * @param name : String as name of the player.
   */
  public Player(String name) {
    if (name.length() == 0 || name.isBlank()) {
      throw new IllegalArgumentException("The name cannot be blank");
    }
    this.name = name;
    treasuresHeld = new LinkedHashMap<>();
    treasuresHeld.put(Treasure.DIAMOND, 0);
    treasuresHeld.put(Treasure.SAPPHIRE, 0);
    treasuresHeld.put(Treasure.RUBY, 0);
    currLoc = new Location(-1, -1);
    inDungeon = false;
    this.quiver = new ArrayList<>(List.of(new Arrow(), new Arrow(), new Arrow()));
  }

  /**
   * A copy constructor to create a copy of the instance.
   *
   * @param incomingPlayer : A Player object.
   */
  public Player(Agent incomingPlayer) {
    Player p = (Player) incomingPlayer;
    this.name = p.name;
    this.treasuresHeld = p.treasuresHeld;
    this.currLoc = p.currLoc;
    this.inDungeon = p.inDungeon;
    this.quiver = p.quiver;
  }


  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public String toString() {
    if (!inDungeon) {
      return "name='" + name + '\''
          + ", Not in Dungeon"
          + ", treasuresHeld="
          + treasuresHeld;
    }
    return "name='" + name + '\''
        + ", currLoc=" + currLoc
        + ", treasuresHeld=" + treasuresHeld;
  }

  @Override
  public Map<Treasure, Integer> getTreasures() {
    return this.treasuresHeld;
  }

  @Override
  public AnyLocation getLocation() {
    if (!inDungeon) {
      throw new IllegalStateException("The player has not yet entered the dungeon");
    }
    return new Location(currLoc);
  }

  void enterDungeon(Location l) {
    this.inDungeon = true;
    this.currLoc = l;
  }


  void moveToNorth() {
    if (this.currLoc.neighbours.get(Direction.north) != null) {
      this.currLoc = this.currLoc.neighbours.get(Direction.north);
    }
  }


  void moveToSouth() {
    if (this.currLoc.neighbours.get(Direction.south) != null) {
      this.currLoc = this.currLoc.neighbours.get(Direction.south);
    }
  }


  void moveToEast() {
    if (this.currLoc.neighbours.get(Direction.east) != null) {
      this.currLoc = this.currLoc.neighbours.get(Direction.east);
    }
  }

  void moveToWest() {
    if (this.currLoc.neighbours.get(Direction.west) != null) {
      this.currLoc = this.currLoc.neighbours.get(Direction.west);
    }
  }

  void move(Direction dir) {
    if (this.currLoc.neighbours.get(dir) != null) {
      this.currLoc = this.currLoc.neighbours.get(dir);
    }
  }

  /**
   * A package private method to pick treasure if there is treasure at that location.
   * The treasure at that location will get added to player's treasures held.
   * Package private as this method must only be called by the dungeon, user shouldn't be able to
   * give treasure to the player.
   *
   * @param incomingTreasure : Treasure, the treasure that is to be taken by the player.
   */
  void pickTreasure(Treasure incomingTreasure, Integer count) {
    this.treasuresHeld.put(incomingTreasure,
        this.treasuresHeld.getOrDefault(incomingTreasure, 0) + count);
  }

  /**
   * Package-private method to add list of treasures to the player's treasure pool.
   *
   * @param t : List of treasures.
   */
  void pickTreasure(Map<Treasure, Integer> t) {
    for (Map.Entry<Treasure, Integer> entry : t.entrySet()) {
      pickTreasure(entry.getKey(), entry.getValue());
    }
  }

  /**
   * A method for player to pick arrows.
   */
  public void pickArrows() {
    for (int i = 0; i < this.currLoc.getArrows(); i++) {
      this.quiver.add(new Arrow());
    }
  }

  Arrow releaseArrow(Direction direction, int distance) {
    if (this.quiver.size() == 0) {
      throw new UnsupportedOperationException("Player out of arrows");
    }
    Arrow arr = this.quiver.remove(0);
    arr.fireArrow(currLoc, direction, distance);
    return arr;
  }

  @Override
  public int getNumArrows() {
    return quiver.size();
  }

  void teleportTo(Location l) {
    this.currLoc = l;
  }

  void removeTreasure() {
    this.treasuresHeld = new LinkedHashMap<>(Map.of(Treasure.DIAMOND, 0, Treasure.RUBY,
        0, Treasure.SAPPHIRE, 0));
  }
}

