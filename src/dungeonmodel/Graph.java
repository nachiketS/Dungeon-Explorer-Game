package dungeonmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import misc.Util;

class Graph {
  private static final int MAXARROWSINLOCATION = 3;
  private static final int MAXTREASURESINLOCATION = 5;
  private static final int OTYUGHHEALTH = 2;
  int interconnectivity;
  int numRows;
  int numCols;
  boolean isWrapping;
  List<List<Location>> grid;
  List<Edge> remEdges;
  List<Edge> usedEdges;
  List<Location> startEnd;
  Random random;

  Graph(int nr, int nc, int interconnectivity, boolean isWrapping, Random random) {
    this.random = random;
    this.interconnectivity = interconnectivity;
    this.numCols = nc;
    this.numRows = nr;
    this.isWrapping = isWrapping;
    this.grid = new ArrayList<>();
    this.generateGrid();
    this.remEdges = generateAllEdges();
    this.usedEdges = selectValidEdges();
  }

  private void generateGrid() {
    for (int i = 0; i < numRows; i++) {
      ArrayList<Location> temp = new ArrayList<>();
      for (int j = 0; j < numCols; j++) {
        temp.add(new Location(i, j));
      }
      this.grid.add(temp);
    }
  }


  private List<Edge> generateAllEdges() {
    int r = this.numRows;
    int c = this.numCols;
    List<Edge> allEdges = new ArrayList<>();
    for (int i = 0; i < r; i++) {
      for (int j = 0; j < c; j++) {
        Coordinate north = new Coordinate(i - 1, j);
        Coordinate south = new Coordinate(i + 1, j);
        Coordinate east = new Coordinate(i, j + 1);
        Coordinate west = new Coordinate(i, j - 1);
        List<Coordinate> neighbours = new ArrayList<>(List.of(north, south, east, west));
        if (!isWrapping) {
          for (Coordinate nc : neighbours) {
            if (nc.xcoordinate >= 0 && nc.ycoordinate >= 0
                && nc.xcoordinate < r && nc.ycoordinate < c) {
              Location nl = this.grid.get(nc.xcoordinate).get(nc.ycoordinate);
              Edge e = new Edge(grid.get(i).get(j), nl);
              if (!allEdges.contains(e)) {
                allEdges.add(e);
              }
            }
          }
        } else {
          for (Coordinate nc : neighbours) {
            if (nc.xcoordinate < 0) {
              nc.xcoordinate = r - 1;
            }
            if (nc.ycoordinate < 0) {
              nc.ycoordinate = c - 1;
            }
            if (nc.xcoordinate >= r) {
              nc.xcoordinate = 0;
            }
            if (nc.ycoordinate >= c) {
              nc.ycoordinate = 0;
            }
            Location nl = this.grid.get(nc.xcoordinate).get(nc.ycoordinate);
            Edge e = new Edge(grid.get(i).get(j), nl);
            if (!allEdges.contains(e)) {
              allEdges.add(e);
            }
          }
        }
      }
    }
    return allEdges;
  }

  private Location find(Map<Location, Location> parent, Location location) {
    if (parent.get(location).equals(new Location(-1, -1))) {
      return location;
    }
    return find(parent, parent.get(location));
  }

  private void union(Map<Location, Location> parent, Location x, Location y) {
    parent.put(x, y);
  }

  private int isCycle(List<Edge> edges) {
    Map<Location, Location> parent = new HashMap<>();
    for (List<Location> l : this.grid) {
      for (Location loc : l) {
        parent.put(loc, new Location(new Coordinate(-1, -1)));
      }
    }
    for (Edge e : edges) {
      Location x = find(parent, e.src);
      Location y = find(parent, e.dest);

      if (x.equals(y)) {
        return 1;
      }
      union(parent, x, y);
    }
    return 0;
  }


  private List<Edge> selectValidEdges() {
    List<Edge> usedEdges = new ArrayList<>();
    List<Edge> unusedEdges = remEdges;
    List<Edge> removedEdges = new ArrayList<>();

    while (!unusedEdges.isEmpty()) {
      int randomNumber = Util.randomNumberGenerator(0, unusedEdges.size() - 1, random);
      Edge randomEdge = unusedEdges.get(randomNumber);
      unusedEdges.remove(randomNumber);
      usedEdges.add(randomEdge);
      if (isCycle(usedEdges) == 1) {
        usedEdges.remove(randomEdge);
        removedEdges.add(randomEdge);
      } else {
        //selecting the src location with neighbor in the appropriate direction
        Location x = grid.get(randomEdge.src.coordinate.xcoordinate)
            .get(randomEdge.src.coordinate.ycoordinate);
        //assign a neighbor to the local copy of the location
        x.addEdge(randomEdge);
        //update the location in grid.
        grid.get(randomEdge.src.coordinate.xcoordinate).set(randomEdge.src.coordinate.ycoordinate,
            x);

        //selecting and updating the src location with neighbor in the opposite direction to above
        Location y = grid.get(randomEdge.dest.coordinate.xcoordinate)
            .get(randomEdge.dest.coordinate.ycoordinate);
        //assign a neighbor to the dest. location
        y.addEdge(new Edge(randomEdge.dest, randomEdge.src));
        //update the location in grid.
        grid.get(randomEdge.dest.coordinate.xcoordinate)
            .set(randomEdge.dest.coordinate.ycoordinate, y);
      }
    }
    // adding the interconnectivity edges that might cause cycles.
    for (int i = 0; i < interconnectivity; i++) {
      int randomNumber = Util.randomNumberGenerator(0, removedEdges.size() - 1, random);
      //      int randomNumber = (int) (Math.random() * removedEdges.size());
      Edge randomEdge = removedEdges.get(randomNumber);
      removedEdges.remove(randomEdge);
      usedEdges.add(randomEdge);
      Location x = grid.get(randomEdge.src.coordinate.xcoordinate)
          .get(randomEdge.src.coordinate.ycoordinate);
      x.addEdge(randomEdge);
      Location y = grid.get(randomEdge.dest.coordinate.xcoordinate)
          .get(randomEdge.dest.coordinate.ycoordinate);
      y.addEdge(new Edge(randomEdge.dest, randomEdge.src));
    }
    return usedEdges;
  }

  List<Location> getCaves() {
    List<Location> toRet = new ArrayList<>();
    for (Location l : getLocations()) {
      if (l.isCave()) {
        toRet.add(l);
      }
    }
    return toRet;
  }

  List<Location> getLocations() {
    // https://stackoverflow.com/questions/44251460/how-to-convert-2d-list-to-1d-list-with-streams
    return this.grid.stream().flatMap(Collection::stream).collect(Collectors.toList());
  }

  void addTreasure(int percentageOfTreasures) {
    if (percentageOfTreasures == 0) {
      return;
    }
    List<Location> caves = getCaves();
    int minCavesWithTreasures = (int) Math.round(percentageOfTreasures * caves.size() * 0.01);
    int numCavesToAddTreasuresTo = Util.randomNumberGenerator(
        minCavesWithTreasures, caves.size(), this.random);
    for (int i = 0; i < numCavesToAddTreasuresTo; i++) {
      int randomIndex = Util.randomNumberGenerator(0, caves.size() - 1, this.random);
      int numTreasuresInCave = Util.randomNumberGenerator(1, MAXTREASURESINLOCATION, this.random);
      for (int j = 0; j < numTreasuresInCave; j++) {
        caves.get(randomIndex).addTreasure(Treasure.getRandomTreasure(random));
      }
      caves.remove(randomIndex);
    }
  }

  void addArrows(int percentageOfArrows) {
    List<Location> locations = getLocations();
    int minLocationsWithArrows = (int) Math.round(percentageOfArrows * locations.size() * 0.01);
    int numLocationstoAddArrowsTo = Util.randomNumberGenerator(
        minLocationsWithArrows, minLocationsWithArrows, this.random);
    for (int i = 0; i < numLocationstoAddArrowsTo; i++) {
      int randomIndex = Util.randomNumberGenerator(0, locations.size() - 1, this.random);
      int numArrows = Util.randomNumberGenerator(1, MAXARROWSINLOCATION, this.random);
      locations.get(randomIndex).addArrow(numArrows);
      locations.remove(randomIndex);
    }
  }

  void addMonsters(int difficulty) {
    List<Location> tempcaves = getCaves();
    int percentageOfMonsters;
    // for testing to add exact number of monsters
    if (difficulty <= 0) {
      startEnd.get(1).addMonster(new Otyugh(OTYUGHHEALTH, random));
      tempcaves.remove(startEnd.get(1));
      for (int i = 0; i < -difficulty; i++) {
        int randomIndex = Util.randomNumberGenerator(0, tempcaves.size() - 1, this.random);
        tempcaves.get(randomIndex).addMonster(new Otyugh(OTYUGHHEALTH, random));
        tempcaves.remove(randomIndex);
      }
      return;
    }
    if (difficulty > 3) {
      throw new IllegalArgumentException("Enter difficulty from 1 to 3");
    }
    if (difficulty == 1) {
      percentageOfMonsters = 10;
    } else if (difficulty == 2) {
      percentageOfMonsters = 30;
    } else {
      percentageOfMonsters = 50;
    }
    List<Location> caves = getCaves();
    int minCavesWithMonsters = (int) Math.round(percentageOfMonsters * caves.size() * 0.01);
    int numCavesToAddMonstersTo = minCavesWithMonsters;
    // removing start cave from the possible locations to add monster to
    caves.remove(startEnd.get(0));
    // adding monster to the end location and then removing it from the possible caves.
    startEnd.get(1).addMonster(new Otyugh(OTYUGHHEALTH, random));
    numCavesToAddMonstersTo--;
    caves.remove(startEnd.get(1));
    for (int i = 0; i < numCavesToAddMonstersTo; i++) {
      int randomIndex = Util.randomNumberGenerator(0, caves.size() - 1, this.random);
      caves.get(randomIndex).addMonster(new Otyugh(OTYUGHHEALTH, random));
      caves.remove(randomIndex);
    }
  }

  void addPits(int difficulty) {
    List<Location> caves = getCaves();
    caves.remove(startEnd.get(0));
    caves.remove(startEnd.get(1));
    caves.removeIf(Location::hasMonster);
    caves.removeIf(l -> l.getNeighbours().size() != 1);
    // adding monster to the end location and then removing it from the possible caves.
    for (int i = 0; i < Math.min(difficulty, caves.size()); i++) {
      int randomIndex = Util.randomNumberGenerator(0, caves.size() - 1, this.random);
      caves.get(randomIndex).addPit();
      caves.remove(randomIndex);
    }
  }

  void addRobbers(int numRobbers) {
    List<Location> tempLocations = this.getLocations();
    tempLocations.removeIf(l -> l.isCave());
    for (int i = 0; i < Math.min(numRobbers, tempLocations.size()); i++) {
      int randomIndex = Util.randomNumberGenerator(0, tempLocations.size() - 1, this.random);
      tempLocations.get(randomIndex).addRobber();
      tempLocations.remove(tempLocations.get(randomIndex));
    }
  }


  HashMap<AnyLocation, AnyLocation> pred = new HashMap<>();
  HashMap<AnyLocation, Integer> dist = new HashMap<>();


  //  https://www.geeksforgeeks.org/shortest-path-unweighted-graph/
  int printShortestDistance(AnyLocation s, AnyLocation dest) {
    // predecessor[i] array stores predecessor of
    // i and distance array stores distance of i
    // from s
    int v = this.numCols * this.numCols;

    if (!bfs(s, dest, v, pred, dist)) {
      return -1;
    }

    // LinkedList to store path
    LinkedList<AnyLocation> path = new LinkedList<>();
    AnyLocation crawl = dest;
    path.add(crawl);
    while (pred.get(crawl) != null) {
      path.add(pred.get(crawl));
      crawl = pred.get(crawl);
    }
    return dist.get(dest);
  }

  // a modified version of BFS that stores predecessor
  // of each vertex in array pred
  // and its distance from source in array dist
  private boolean bfs(AnyLocation src, AnyLocation dest, int v,
                      HashMap<AnyLocation, AnyLocation> pred, HashMap<AnyLocation, Integer> dist) {
    // a queue to maintain queue of vertices whose adjacency list is to be scanned as per normal
    // BFS algorithm using LinkedList of Integer type

    // boolean array visited[] which stores the
    // information whether ith vertex is reached
    // at least once in the Breadth first search
    Map<AnyLocation, Boolean> visited = new HashMap<>();
    //    boolean visited[] = new boolean[v];

    // initially all vertices are unvisited
    // so v[i] for all i is false
    // and as no path is yet constructed
    // dist[i] for all i set to infinity
    for (int i = 0; i < this.numRows; i++) {
      for (int j = 0; j < this.numCols; j++) {
        visited.put(new Location(i, j), false);
        dist.put(new Location(i, j), Integer.MAX_VALUE);
        pred.put(new Location(i, j), null);
      }
    }
    LinkedList<AnyLocation> queue = new LinkedList<>();

    // now source is first to be visited and
    // distance from source to itself should be 0
    visited.put(src, true);
    dist.put(src, 0);
    queue.add(src);

    // bfs Algorithm
    while (!queue.isEmpty()) {
      AnyLocation u = queue.remove();
      for (Location c : grid.get(u.getCoOrdinates().xcoordinate)
          .get(u.getCoOrdinates().ycoordinate).getNeighbours()) {
        if (!visited.get(c)) {
          visited.put(c, true);
          dist.put(c, dist.get(u) + 1);
          pred.put(c, u);
          queue.add(c);

          // stopping condition (when we find our destination)
          if (c.equals(dest)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Making this package-private as it is needed in the Dungeon class.
   *
   * @return : List of 2 caves, [startCave, endCave]
   */
  List<Location> getRandomizedStartEnd() {
    List<Location> startCaves = this.getCaves();
    List<Location> caves = this.getCaves();
    while (!startCaves.isEmpty()) {
      int randomIndex = Util.randomNumberGenerator(0, startCaves.size() - 1, random);
      Location start = startCaves.get(randomIndex);
      startCaves.remove(randomIndex);
      List<Location> possibleEndCaves = new ArrayList<>();
      for (Location c : caves) {
        if (this.printShortestDistance(start, c) >= 5) {
          possibleEndCaves.add(c);
        }
      }
      if (!possibleEndCaves.isEmpty()) {
        int randomIndex2 = Util.randomNumberGenerator(0, possibleEndCaves.size() - 1, random);
        this.startEnd = new ArrayList<>(List.of(start, possibleEndCaves.get(randomIndex2)));
        return this.startEnd;
      }
    }
    throw new IllegalStateException("The given dungeon does not have any possible path "
        + "with length 5.");
  }

  public Graph(Graph in) {
    numRows = in.numRows;
    numCols = in.numCols;
    interconnectivity = in.interconnectivity;
    isWrapping = in.isWrapping;
    random = in.random;
    List<List<Location>> newGrid = new ArrayList<>();
    for (List<Location> row : in.grid) {
      ArrayList<Location> rowToAdd = new ArrayList<>();
      for (Location c : row) {
        rowToAdd.add(c.locationCopyWithoutNeighbours());
      }
      newGrid.add(rowToAdd);
    }
    grid = newGrid;
    for (Edge e : in.usedEdges) {
      Location x = grid.get(e.src.coordinate.xcoordinate)
          .get(e.src.coordinate.ycoordinate);
      //assign a neighbor to the local copy of the location
      x.addEdge(e);
      //update the location in g.grid.
      grid.get(e.src.coordinate.xcoordinate).set(e.src.coordinate.ycoordinate,
          x);

      //selecting and updating the src location with neighbor in the opposite direction to above
      Location y = grid.get(e.dest.coordinate.xcoordinate)
          .get(e.dest.coordinate.ycoordinate);
      //assign a neighbor to the dest. location
      y.addEdge(new Edge(e.dest, e.src));
      //update the location in g.grid.
      grid.get(e.dest.coordinate.xcoordinate)
          .set(e.dest.coordinate.ycoordinate, y);
    }
  }
}

