package dungeonmodel;

import static misc.Util.randomNumberGenerator;

import java.util.Random;
import misc.CustomRandom;


class Otyugh implements Monster {
  Random random;
  int health;

  public Otyugh(int health, Random random) {
    this.health = health;
    this.random = random;
  }

  public Otyugh(int health) {
    this(health, new CustomRandom());
  }

  public Otyugh(Otyugh o) {
    this.health = o.health;
    this.random = o.random;
  }

  @Override
  public boolean getHurt(int i) {
    if (this.health > 0) {
      this.health -= 1;
      return true;
    }
    return false;
  }

  //  @Override
  public boolean eat() {
    if (this.health == 2) {
      return true;
    } else if (this.health == 1 && randomNumberGenerator(0, 2, random) == 0) {
      return true;
    }
    return false;
  }

  @Override
  public int getHealth() {
    return this.health;
  }
}
