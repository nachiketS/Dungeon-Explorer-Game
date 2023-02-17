package dungeonmodel;

//import java.util.ArrayList;
//import java.util.List;

import java.util.Objects;

class Edge {
  Location src;
  Location dest;

  Edge(AnyLocation src, AnyLocation dest) {
    this.src = (Location) src;
    this.dest = (Location) dest;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Edge)) {
      return false;
    }
    Edge edge = (Edge) o;
    return ((edge.src.equals(this.src) || edge.src.equals(this.dest)) && (edge.dest.equals(this.src)
            || edge.dest.equals(this.dest)));
  }

  @Override
  public int hashCode() {
    return Objects.hash(src, dest);
  }

  @Override
  public String toString() {
    return src + " " + dest;
    //    return String.format("[%d, %d] -> [%d, %d]", src.x, src.y, dest.x, dest.y);
  }
}
