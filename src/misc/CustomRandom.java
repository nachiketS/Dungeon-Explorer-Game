package misc;

import java.util.Random;

/**
 * A class that is used while testing to get known values when Random gets called.
 * This method is overriding the nextInt method and will return the upperbound - 1 everytime
 * it is called.
 */
public class CustomRandom extends Random {
  int val;

  public CustomRandom(int val) {
    this.val = val;
  }

  public CustomRandom() {
    this.val = 0;
  }

  @Override
  public int nextInt(int num) {
    if (val >= num) {
      return num - 1;
    }
    return val;
  }
}