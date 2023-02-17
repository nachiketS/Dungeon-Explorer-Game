package view;

import dungeonmodel.Direction;
import dungeonmodel.Location;
import dungeonmodel.ReadOnlyDungeonModel;
import dungeonmodel.Treasure;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A class extending JPanel which is used for the actual game display. This JPanel has a grid of
 * JLabels which have the game images.
 */
public class GamePanel extends JPanel {
  private ReadOnlyDungeonModel model;
  private JLabel[][] cells;
  private final Map<Set<Direction>, BufferedImage> imageHashMap;
  private final BufferedImage stench1;
  private final BufferedImage stench2;
  private BufferedImage arrow;
  private BufferedImage otyugh;
  private BufferedImage diamond;
  private BufferedImage emerald;
  private BufferedImage ruby;
  private BufferedImage black;
  private HashSet<Location> visitedLocations;
  private MouseListener mouseListener;
  private Direction previousDirection;
  private BufferedImage playerSouth;
  private BufferedImage playerWest;
  private BufferedImage playerNorth;
  private BufferedImage playerEast;
  private BufferedImage pit;
  private final BufferedImage sign;
  private final BufferedImage robber;

  /**
   * The constructor for creating a new GamePanel. All the resources get loaded in this method.
   *
   * @param model : ReadOnlyDungeonModel
   */
  public GamePanel(ReadOnlyDungeonModel model) {
    this.model = model;
    this.setLayout(new GridLayout(model.getNumRows(), model.getNumCols()));
    this.visitedLocations = new HashSet<>();
    this.imageHashMap = new HashMap<>();
    this.cells = new JLabel[model.getNumRows()][model.getNumCols()];
    try {
      this.stench1 = ImageIO.read(Objects.requireNonNull(getClass().getResource(
          "res/sprites3/stench01.png")));
      this.stench2 = ImageIO.read(Objects.requireNonNull(getClass().getResource(
          "res/sprites3/stench02.png")));
      this.arrow = ImageIO.read(Objects.requireNonNull(getClass().getResource(
          "res/sprites3/arrow.png")));
      this.otyugh = ImageIO.read(Objects.requireNonNull(getClass().getResource(
          "res/sprites3/otyugh.png")));
      this.ruby = ImageIO.read(Objects.requireNonNull(getClass().getResource(
          "res/sprites3/ruby.png")));
      this.diamond = ImageIO.read(Objects.requireNonNull(getClass().getResource(
          "res/sprites3/diamond.png")));
      this.emerald = ImageIO.read(Objects.requireNonNull(getClass().getResource(
          "res/sprites3/emerald.png")));
      this.playerSouth = ImageIO.read(Objects.requireNonNull(getClass().getResource(
          "res/sprites3/playerSouth.png")));
      this.playerWest = ImageIO.read(Objects.requireNonNull(getClass().getResource(
          "res/sprites3/playerWest.png")));
      this.playerEast = ImageIO.read(Objects.requireNonNull(getClass().getResource(
          "res/sprites3/playerEast.png")));
      this.playerNorth = ImageIO.read(Objects.requireNonNull(getClass().getResource(
          "res/sprites3/playerNorth.png")));
      this.black = ImageIO.read(Objects.requireNonNull(getClass().getResource(
          "res/sprites3/black.png")));
      this.sign = ImageIO.read(Objects.requireNonNull(getClass().getResource(
          "res/sprites3/sign.png")));
      this.robber = ImageIO.read(Objects.requireNonNull(getClass().getResource(
          "res/sprites3/robber.png")));
      this.previousDirection = Direction.south;
      this.playerSouth = this.resizeImage(this.playerSouth, 45, 45);
      this.playerNorth = this.resizeImage(this.playerNorth, 45, 45);
      this.playerWest = this.resizeImage(this.playerWest, 45, 45);
      this.playerEast = this.resizeImage(this.playerEast, 45, 45);
      this.pit = ImageIO.read(Objects.requireNonNull(getClass().getResource(
          "res/sprites3/hole.png")));
      imageHashMap.put(Set.of(Direction.north), ImageIO.read(Objects.requireNonNull(
          getClass().getResource("res/sprites3/N.png"))));
      imageHashMap.put(Set.of(Direction.east), ImageIO.read(Objects.requireNonNull(
          getClass().getResource("res/sprites3/E.png"))));
      imageHashMap.put(Set.of(Direction.west), ImageIO.read(Objects.requireNonNull(
          getClass().getResource("res/sprites3/W.png"))));
      imageHashMap.put(Set.of(Direction.south), ImageIO.read(Objects.requireNonNull(
          getClass().getResource("res/sprites3/S.png"))));
      imageHashMap.put(Set.of(Direction.north, Direction.south),
          ImageIO.read(Objects.requireNonNull(getClass().getResource("res/sprites3/NS.png"))));
      imageHashMap.put(Set.of(Direction.north, Direction.east),
          ImageIO.read(Objects.requireNonNull(getClass().getResource("res/sprites3/NE.png"))));
      imageHashMap.put(Set.of(Direction.north, Direction.west),
          ImageIO.read(Objects.requireNonNull(getClass().getResource("res/sprites3/NW.png"))));
      imageHashMap.put(Set.of(Direction.south, Direction.west),
          ImageIO.read(Objects.requireNonNull(getClass().getResource("res/sprites3/SW.png"))));
      imageHashMap.put(Set.of(Direction.south, Direction.east),
          ImageIO.read(Objects.requireNonNull(getClass().getResource("res/sprites3/SE.png"))));
      imageHashMap.put(Set.of(Direction.east, Direction.west),
          ImageIO.read(Objects.requireNonNull(getClass().getResource("res/sprites3/EW.png"))));
      imageHashMap.put(Set.of(Direction.east, Direction.west, Direction.north),
          ImageIO.read(Objects.requireNonNull(getClass().getResource("res/sprites3/NEW.png"))));
      imageHashMap.put(Set.of(Direction.east, Direction.west, Direction.south),
          ImageIO.read(Objects.requireNonNull(getClass().getResource("res/sprites3/SEW.png"))));
      imageHashMap.put(Set.of(Direction.east, Direction.north, Direction.south),
          ImageIO.read(Objects.requireNonNull(getClass().getResource("res/sprites3/NSE.png"))));
      imageHashMap.put(Set.of(Direction.north, Direction.south, Direction.west),
          ImageIO.read(Objects.requireNonNull(getClass().getResource("res/sprites3/NSW.png"))));
      imageHashMap.put(Set.of(Direction.north, Direction.south, Direction.west, Direction.east),
          ImageIO.read(Objects.requireNonNull(getClass().getResource("res/sprites3/NSEW.png"))));
    } catch (IOException ioe) {
      throw new IllegalStateException("No image " + ioe);
    }
    for (int i = 0; i < cells.length; i++) {
      for (int j = 0; j < cells[0].length; j++) {
        this.cells[i][j] = new JLabel();
      }
    }
  }

  /**
   * A method to refresh the view.
   *
   * @param m : ReadOnlyDungeonModel
   */
  public void refresh(ReadOnlyDungeonModel m) {
    this.model = m;
    this.cells = new JLabel[m.getNumRows()][m.getNumCols()];
    for (int i = 0; i < m.getNumRows(); i++) {
      for (int j = 0; j < m.getNumCols(); j++) {
        this.cells[i][j] = new JLabel();
      }
    }
    this.removeAll();
    this.setLayout(new GridLayout(m.getNumRows(), m.getNumCols()));
    for (List<Location> r : m.getGrid()) {
      for (Location c : r) {
        cells[c.getCoOrdinates().xcoordinate][c.getCoOrdinates().ycoordinate]
            .setIcon(new ImageIcon(this.getLocationImage(c)));
        this.add(cells[c.getCoOrdinates().xcoordinate][c.getCoOrdinates().ycoordinate]);
        Location playerLocation = new Location(c.getCoOrdinates());
        this.scrollRectToVisible(
            cells[playerLocation.getCoOrdinates().xcoordinate]
                [playerLocation.getCoOrdinates().ycoordinate]
                .getBounds());
        cells[c.getCoOrdinates().xcoordinate][c.getCoOrdinates().ycoordinate]
            .addMouseListener(new MouseAdapter() {
              @Override
              public void mouseClicked(MouseEvent e) {
                e.setSource(c.getCoOrdinates());
                mouseListener.mouseClicked(e);
              }
            });
      }
    }
  }

  public void addMouseListener(MouseListener listener) {
    mouseListener = listener;
  }

  private BufferedImage getLocationImage(Location l) {
    BufferedImage toRet = imageHashMap.get(l.getNeighboursMap().keySet());
    toRet = this.resizeImage(toRet, 128, 128);
    //adding arrows
    if (l.getArrows() > 0) {
      arrow = this.resizeImage(arrow, toRet.getWidth() / 4, toRet.getHeight() / 4);
      toRet = this.addToImage(toRet, arrow, toRet.getWidth() - 40, 10);
    }
    //adding smell
    if (l.getSmell() > 1) {
      toRet = this.addToImage(toRet, stench2, 0, 0);
    }
    if (l.getSmell() == 1) {
      toRet = this.addToImage(toRet, stench1, 0, 0);
    }
    //adding treasures
    if (!l.getTreasureList().isEmpty()) {
      int latest = 10;
      for (Treasure t : l.getTreasureList()) {
        if (t == Treasure.DIAMOND) {
          this.diamond = this.resizeImage(this.diamond, toRet.getWidth() / 6,
              toRet.getHeight() / 6);
          toRet = this.addToImage(toRet, this.diamond, latest, 10);
          latest += 5;
        } else if (t == Treasure.RUBY) {
          this.ruby = this.resizeImage(this.ruby, toRet.getWidth() / 6,
              toRet.getHeight() / 6);
          toRet = this.addToImage(toRet, this.ruby, latest, 10);
          latest += 5;
        } else if (t == Treasure.SAPPHIRE) {
          this.emerald = this.resizeImage(this.emerald, toRet.getWidth() / 6,
              toRet.getHeight() / 6);
          toRet = this.addToImage(toRet, this.emerald, latest, 10);
          latest += 5;
        }
      }
    }
    //adding player
    if (model.getPlayer().getLocation().equals(l)) {
      switch (this.previousDirection) {
        case north:
          toRet = this.addToImage(toRet, this.playerNorth, 40, 40);
          break;
        case south:
          toRet = this.addToImage(toRet, this.playerSouth, 40, 40);
          break;
        case east:
          toRet = this.addToImage(toRet, this.playerEast, 40, 40);
          break;
        case west:
          toRet = this.addToImage(toRet, this.playerWest, 40, 40);
          break;
        default:
          toRet = this.addToImage(toRet, this.playerWest, 40, 40);
          break;
      }
      this.visitedLocations.add(l);
    }
    //adding otyugh
    if (l.hasMonster()) {
      this.otyugh = this.resizeImage(this.otyugh, toRet.getWidth() / 2, toRet.getHeight() / 2);
      toRet = this.addToImage(toRet, this.otyugh, 20, 20);
    }
    //adding pit images
    if (l.hasPit()) {
      this.pit = this.resizeImage(this.pit, toRet.getWidth() / 2, toRet.getHeight() / 2);
      toRet = this.addToImage(toRet, this.pit, toRet.getWidth() / 4, toRet.getHeight() / 4);
    }
    //adding sign images
    if (l.hasSign()) {
      toRet = this.addToImage(toRet, this.sign, 20, 80);
    }
    //adding robber image
    if (l.hasRobber()) {
      toRet = this.addToImage(toRet, this.robber, 20, 20);
    }
    //resizing image
    toRet = this.resizeImage(toRet, 128, 128);
    if (!visitedLocations.contains(l)) {
      return this.resizeImage(black, 128, 128);
    }
    return toRet;
  }

  // https://coderedirect.com/questions/389014/overlay-images-in-java
  private BufferedImage addToImage(BufferedImage image, BufferedImage overlay, int posX, int posY) {
    int w = Math.max(image.getWidth(), overlay.getWidth());
    int h = Math.max(image.getHeight(), overlay.getHeight());
    BufferedImage combined = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

    // paint both images, preserving the alpha channels
    Graphics g = combined.getGraphics();
    g.drawImage(image, 0, 0, null);
    g.drawImage(overlay, posX, posY, null);
    // Save as new image
    return combined;
  }

  //https://riptutorial.com/java/example/28299/how-to-scale-a-bufferedimage
  private BufferedImage resizeImage(BufferedImage image, int width, int height) {
    BufferedImage resizedImage = new BufferedImage(width, height,
        BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D g2 = resizedImage.createGraphics();
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(image, 0, 0, width, height, null);
    g2.dispose();
    return resizedImage;
  }

  public void setpreviousDirection(Direction direction) {
    this.previousDirection = direction;
  }
}
