package dungeonmodel;

import java.util.Map;

class Arrow implements Shootable {
  Location from;
  Location curr;
  Direction direction;
  int dist;
  private static final int MAXARROWDISTANCE = 5;
  private static final int MINARROWDISTANCE = 1;
  private static final int ARROWDAMAGE = 1;

  public Arrow() {
    from = null;
    curr = null;
    direction = null;
    dist = 0;
  }

  public Arrow(Location curr, Direction direction, int dist) {
    if (direction == null) {
      throw new IllegalArgumentException("The direction should be either n, e, w or s");
    }
    if (dist > 5 || dist < 1) {
      throw new IllegalArgumentException("Enter distance between 1 and 5 [1-5]");
    }
    this.from = null;
    this.curr = curr;
    this.direction = direction;
    this.dist = dist;
  }

  private void travelUnitDistance() {
    Direction newDirection = direction;
    Map<Direction, Location> neighboursMap = curr.getNeighboursMapReferences();
    Location nextLocation = null;
    if (curr.isCave()) {
      if (neighboursMap.get(direction) != null) {
        nextLocation = curr.getNeighboursMapReferences().get(direction);
      }
    } else {
      if (!curr.isCave()) {
        for (Direction d : neighboursMap.keySet()) {
          if (neighboursMap.get(d) != null && !neighboursMap.get(d).equals(from)) {
            nextLocation = neighboursMap.get(d);
            newDirection = d;
          }
        }
      }
    }
    this.from = curr;
    this.curr = nextLocation;
    this.direction = newDirection;
    if (curr != null && curr.isCave()) {
      dist--;
    }
  }

  //  @Override
  Location arrowStopsAt() {
    if (dist > 0) {
      this.from = this.curr;
      this.curr = this.curr.getNeighboursMapReferences().get(direction);
      if (this.curr.isCave()) {
        dist--;
      }
    }
    if (dist == 0) {
      return this.curr;
    }
    do {
      travelUnitDistance();
      if (this.curr == null) {
        return null;
      }
    }
    while (dist > 0);
    return (Location) this.curr;
  }

  @Override
  public String toString() {
    return new StringBuilder()
        .append("Arrow{")
        .append("from=")
        .append(from)
        .append(", curr=")
        .append(curr)
        .append(", direction=")
        .append(direction)
        .append(", dist=")
        .append(dist)
        .append(", MAXARROWDISTANCE=")
        .append(MAXARROWDISTANCE)
        .append(", MINARROWDISTANCE=")
        .append(MINARROWDISTANCE)
        .append('}').toString();
  }

  void fireArrow(Location curr, Direction dir, int dist) {
    if (dir == null) {
      throw new IllegalArgumentException("The direction should be either n, e, w or s");
    }
    if (dist > 5 || dist < 1) {
      throw new IllegalArgumentException("Enter distance between 1 and 5 [1-5]");
    }
    this.direction = dir;
    this.dist = dist;
    this.curr = curr;
  }

  int getDamage() {
    return this.ARROWDAMAGE;
  }
}

