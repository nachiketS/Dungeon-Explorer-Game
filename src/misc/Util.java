package misc;

import java.util.Random;

/**
 * A custom Util class which will have a static randomNumberGenerator method.
 * This method will return a random number in the given range.
 */
public class Util extends Random {
  //  private static Random r ;
  public static int randomNumberGenerator(int min, int max, Random r) {
    return r.nextInt(max - min + 1) + min;
  }
}
