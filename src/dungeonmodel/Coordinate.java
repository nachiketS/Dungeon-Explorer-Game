package dungeonmodel;

import java.util.Objects;

/**
 * A class which represents a coordinate that is used to point to anything in space, with x and y.
 */
public class Coordinate {
  public int xcoordinate;
  public int ycoordinate;

  public Coordinate(int xcoordinate, int ycoordinate) {
    this.xcoordinate = xcoordinate;
    this.ycoordinate = ycoordinate;
  }

  Coordinate(Coordinate c) {
    //Copy constructor
    this.xcoordinate = c.xcoordinate;
    this.ycoordinate = c.ycoordinate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Coordinate)) {
      return false;
    }
    Coordinate that = (Coordinate) o;
    return xcoordinate == that.xcoordinate && ycoordinate == that.ycoordinate;
  }

  @Override
  public int hashCode() {
    return Objects.hash(xcoordinate, ycoordinate);
  }

  @Override
  public String toString() {
    return String.format("[%d, %d]", xcoordinate, ycoordinate);
  }
}
