package dungeonmodel;

import static dungeonmodel.Direction.north;
import static dungeonmodel.Direction.south;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;


/**
 * A class used to represent the most basic building block of the dungeon that is either a cave
 * or a tunnel.
 * A location can have at most 4 locations as neighbours
 * A location has a coordinate which is it's place in the 2d grid.
 */
public class Location implements AnyLocation {
  private List<Treasure> treasureList;
  Coordinate coordinate;
  Map<Direction, Location> neighbours;
  private int arrows;
  private Monster monster;
  private static final int MAXARROWSINLOCATION = 3;
  private int smell;
  private Map<Treasure, Integer> treasureMap;
  private boolean hasPit;
  private boolean sign;
  private boolean hasRobber;

  /**
   * A constructor to create a new Location.
   *
   * @param c : A coordinate object.
   */
  public Location(Coordinate c) {
    if (c == null) {
      throw new IllegalArgumentException("The coordinate cannot be null");
    }
    this.coordinate = c;
    treasureList = new ArrayList<>();
    treasureMap = new TreeMap<>();
    this.neighbours = new TreeMap<>();
    this.hasPit = false;
    this.sign = false;
    this.hasRobber = false;
  }

  /**
   * A constructor to create a new Location.
   *
   * @param x : The row in which the location belongs in the grid.
   * @param y : The column in which the location belongs in the grid.
   */
  public Location(int x, int y) {
    this.coordinate = new Coordinate(x, y);
    treasureList = new ArrayList<>();
    treasureMap = new TreeMap<>();
    this.neighbours = new TreeMap<>();
  }

  /**
   * A copy constructor for location that will create a copy of a location.
   *
   * @param l Location
   */
  public Location(Location l) {
    if (l == null) {
      throw new IllegalArgumentException("The incoming location cannot be null");
    }
    this.coordinate = new Coordinate(l.coordinate);
    this.treasureList = new ArrayList<>(l.treasureList);
    this.neighbours = l.getNeighboursMap();
    this.smell = l.smell;
    this.monster = l.monster;
    this.arrows = l.arrows;
    this.treasureMap = l.treasureMap;
    this.hasPit = l.hasPit;
    this.sign = l.sign;
    this.hasRobber = l.hasRobber;
  }

  /**
   * Create a copy of a location with its neighbours. The neighbours of the location however do not
   * have any more neighbours.
   *
   * @return : A copy of Location.
   */
  Location locationCopyWithoutNeighbours() {
    Location toRet = new Location(this.coordinate);
    toRet.treasureList = new ArrayList<>(this.treasureList);
    toRet.arrows = this.arrows;
    toRet.monster = this.monster;
    toRet.smell = this.smell;
    toRet.treasureMap = this.treasureMap;
    return toRet;
  }


  @Override
  public Coordinate getCoOrdinates() {
    return coordinate;
  }

  // package-private as only package classes especially Dungeon should have access to this method.
  void addEdge(Edge e) {
    if (e.dest.coordinate.xcoordinate - this.coordinate.xcoordinate == -1
        || e.dest.coordinate.xcoordinate - this.coordinate.xcoordinate > 1) {
      if (this.neighbours.get(north) != null) {
        throw new IllegalStateException("Location " + this + " already "
            + "has a neighbour to the north");
      }
      this.neighbours.put(north, e.dest);
    }
    if (e.dest.coordinate.xcoordinate - this.coordinate.xcoordinate == 1
        || e.dest.coordinate.xcoordinate - this.coordinate.xcoordinate < -1) {
      if (this.neighbours.get(south) != null) {
        throw new IllegalStateException("Location " + this + " already "
            + "has a neighbour to the south");
      }
      this.neighbours.put(south, e.dest);
    }
    if (e.dest.coordinate.ycoordinate - this.coordinate.ycoordinate == 1
        || e.dest.coordinate.ycoordinate - this.coordinate.ycoordinate < -1) {
      if (this.neighbours.get(Direction.east) != null) {
        throw new IllegalStateException("Location " + this + " already "
            + "has a neighbour to the Direction.east");
      }
      this.neighbours.put(Direction.east, e.dest);
    }
    if (e.dest.coordinate.ycoordinate - this.coordinate.ycoordinate == -1
        || e.dest.coordinate.ycoordinate - this.coordinate.ycoordinate > 1) {
      if (this.neighbours.get(Direction.west) != null) {
        throw new IllegalStateException("Location " + this + " already "
            + "has a neighbour to the Direction.west");
      }
      this.neighbours.put(Direction.west, e.dest);
    }
  }


  @Override
  public String toString() {
    if (isCave()) {
      return "Cave: " + this.coordinate.toString();
    }
    return "Tunnel: " + this.coordinate.toString();
  }

  // Only checking for coordinates here as the neighbours can get added and subtracted throughout
  // the code.
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Location)) {
      return false;
    }
    Location location = (Location) o;
    return Objects.equals(coordinate, location.coordinate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(coordinate);
  }

  //package-private as only package classes, esp dungeon should have access to this method.
  void clearTreasure() {
    this.treasureMap = new HashMap<>();
    this.treasureList = new ArrayList<>();
  }


  @Override
  public boolean isCave() {
    int entranceCounter = 0;
    if (this.neighbours.get(north) != null) {
      entranceCounter++;
    }
    if (this.neighbours.get(south) != null) {
      entranceCounter++;
    }
    if (this.neighbours.get(Direction.east) != null) {
      entranceCounter++;
    }
    if (this.neighbours.get(Direction.west) != null) {
      entranceCounter++;
    }
    return (entranceCounter != 2);
  }

  /**
   * Package-private method to add Treasure to a location.
   * Only classes within the package should be allowed to access this method, especially dungeon.
   *
   * @param t : Treasure to be added to the location.
   */
  void addTreasure(Treasure t) {
    this.treasureList.add(t);
    int count = treasureMap.containsKey(t) ? treasureMap.get(t) : 0;
    this.treasureMap.put(t, count + 1);
  }

  /**
   * This will return the neighbours of the current location. There is no access provided to
   * neighbours of neighbours.
   *
   * @return A list of neighbouring locations.
   */
  @Override
  public List<Location> getNeighbours() {

    return getNeighboursMap()
        .entrySet()
        .stream()
        .map(entry -> entry.getValue())
        .collect(Collectors.toList());
//
//    List<Location> neighbours = new ArrayList<>();
//    if (this.neighbours.get(north) != null) {
//      neighbours.add(this.getNeighboursMap().get(north));
//    }
//    if (this.neighbours.get(Direction.west) != null) {
//      neighbours.add(this.getNeighboursMap().get(west));
//    }
//
//    if (this.neighbours.get(Direction.east) != null) {
//      neighbours.add(this.getNeighboursMap().get(east));
//    }
//
//    if (this.neighbours.get(south) != null) {
//      neighbours.add(this.getNeighboursMap().get(south));
//    }
//    return neighbours;
  }

  @Override
  public Location getNorth() {
    return this.neighbours.get(north);
  }

  @Override
  public Location getSouth() {
    return this.neighbours.get(south);
  }

  @Override
  public Location getEast() {
    return this.neighbours.get(Direction.east);
  }

  @Override
  public Location getWest() {
    return this.neighbours.get(Direction.west);
  }

  @Override
  public List<Treasure> getTreasureList() {
    return this.treasureList;
  }

  @Override
  public Map<Treasure, Integer> getTreasures() {
    return this.treasureMap;
  }

  @Override
  public int getArrows() {
    return this.arrows;
  }

  void removeArrows() {
    this.arrows = 0;
  }


  /**
   * This method will create a copy of the neighbours map in such a way that neighbours of
   * neighbours are set to null. So anyone calling this method will not be able to access
   * neighbours' neighbours
   *
   * @return Map, Directions mapped to neighbours.
   */
  @Override
  public Map<Direction, Location> getNeighboursMap() {
    Map<Direction, Location> toRet = new TreeMap<>(Comparator.comparing(Enum::name));
    toRet.putAll(this.neighbours.entrySet()
        .stream()
        .collect(Collectors.toMap(Map.Entry::getKey,
            e -> e.getValue().locationCopyWithoutNeighbours())));
    return Collections.unmodifiableMap(toRet);

  }

  @Override
  public boolean hasMonster() {
    return this.monster != null;
  }

  @Override
  public Monster getMonster() {
    return this.monster;
  }

  public void addArrows() {
    this.arrows += 10;
  }

  void addMonster(Monster m) {
    if (this.hasMonster()) {
      throw new IllegalStateException("Monster exists in the location");
    }
    List<Location> visited = new ArrayList<>();
    this.monster = m;
    this.addSmell(2);
    for (Location n1 : this.getNeighboursReferences()) {
      n1.addSmell(2);
      for (Location l : n1.getNeighboursReferences()) {
        if (!visited.contains(l)) {
          visited.add(l);
          l.addSmell(1);
        }
      }
    }
  }

  void addSmell(int s) {
    this.smell += s;
  }

  void removeSmell(int s) {
    this.smell -= s;
  }

  void addArrow(int numArrows) {
    arrows = numArrows;
  }

  @Override
  public int getSmell() {
    return this.smell;
  }

  private List<Location> getNeighboursReferences() {
    List<Location> toRet = new ArrayList<>();
    for (Location l : this.neighbours.values()) {
      toRet.add(l);
    }
    return toRet;
  }

  Map<Direction, Location> getNeighboursMapReferences() {
    return this.neighbours;
  }

  boolean monsterTakeDamage(int damage) {
    boolean hit = this.monster.getHurt(damage);
    if (this.monster.getHealth() == 0) {
      this.removeMonster();
    }
    return hit;
  }

  private void removeMonster() {
    this.monster = null;
    this.removeSmell(2);
    List<Location> visited = new ArrayList<>();
    for (Location n1 : this.getNeighboursReferences()) {
      n1.removeSmell(2);
      for (Location l : n1.getNeighboursReferences()) {
        if (!visited.contains(l)) {
          visited.add(l);
          l.removeSmell(1);
        }
      }
    }
  }

  void addPit() {
    this.hasPit = true;
    for (Location n1 : this.getNeighboursReferences()) {
      n1.addSign();
    }
  }

  void addSign() {
    this.sign = true;
  }

  public boolean hasPit() {
    return this.hasPit;
  }

  public boolean hasSign() {
    return this.sign;
  }

  public void addRobber() {
    this.hasRobber = true;
  }

  public boolean hasRobber() {
    return this.hasRobber;
  }
}
