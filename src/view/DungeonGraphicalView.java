package view;

import dungeonmodel.Direction;
import dungeonmodel.Location;
import dungeonmodel.ReadOnlyDungeonModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * A Graphical view  or the GUI for the DUngeon explorer game. This uses java swing for creating
 * the Graphical User Interface.
 */
public class DungeonGraphicalView extends JFrame implements DungeonView {
  private ReadOnlyDungeonModel model;
  private MyMenuBar menuBar;
  private JPanel gridPanel;
  private PlayerDescPanel playerDesc1;
  private JLabel statusLabel;
  private JTextField shootingDist;
  private GamePanel gamePanel1;
  private JScrollPane scrollPane;

  /**
   * A constructor for the GUI view. This will initialize all the attributes of the
   * GUI view.
   *
   * @param model : ReadOnlyDungeonModel
   */
  public DungeonGraphicalView(ReadOnlyDungeonModel model) {
    super("Dungeon");
    setSize(600, 600);
    setLocationRelativeTo(null);
    this.setLayout(new BorderLayout());
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.model = model;
    this.gamePanel1 = new GamePanel(model);
    this.menuBar = new MyMenuBar(model);
    this.setJMenuBar(menuBar);
    this.gridPanel = new JPanel(new FlowLayout());
    this.gridPanel.setSize(800, 800);
    this.playerDesc1 = new PlayerDescPanel(model);
    this.playerDesc1.setPreferredSize(new Dimension(150, 400));
    this.playerDesc1.setLayout(new BoxLayout(playerDesc1, BoxLayout.PAGE_AXIS));
    this.statusLabel = new JLabel("New Game");
    this.shootingDist = new JTextField("1");
    this.gridPanel.setBackground(Color.darkGray);
    this.scrollPane = new JScrollPane(this.gridPanel);
    this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    this.getContentPane().add(this.playerDesc1, BorderLayout.EAST);
    this.getContentPane().add(this.statusLabel, BorderLayout.SOUTH);
  }

  @Override
  public void refresh(String statusText) {
    updateModel(this.model);
    updateStatus(statusText);
  }

  private void updateStatus(String statusText) {
    this.playerDesc1.updatePlayerDesc(this.model, statusText);
    this.statusLabel.setText(statusText);
  }

  @Override
  public void addClickListener(MouseListener listener) {
    this.gamePanel1.addMouseListener(listener);
  }

  @Override
  public void addActionListener(ActionListener listener) {
    this.menuBar.addActionListener(listener);
  }

  /**
   * A method which gets called when the model of the view is updated.
   *
   * @param m : ReadOnlyDungeonModel
   */
  public void newModel(ReadOnlyDungeonModel m) {
    this.gridPanel.remove(gamePanel1);
    this.gamePanel1 = new GamePanel(m);
    this.updateModel(m);
    this.revalidate();
    this.repaint();
    this.setVisible(true);
  }

  /**
   * A method which is called to update the model of the view.
   *
   * @param m : The new ReadOnlyDungeonModel.
   */
  @Override
  public void updateModel(ReadOnlyDungeonModel m) {
    this.model = m;
    this.getContentPane().removeAll();
    this.statusLabel.setText("New Game");
    this.gridPanel.remove(gamePanel1);
    this.gamePanel1.refresh(model);
    this.gridPanel.add(gamePanel1);
    this.playerDesc1.updatePlayerDesc(this.model, "New Game");
    this.scrollPane = new JScrollPane(this.gridPanel);
    this.getContentPane().add(scrollPane, BorderLayout.CENTER);
    this.getContentPane().add(this.playerDesc1, BorderLayout.EAST);
    this.getContentPane().add(this.statusLabel, BorderLayout.SOUTH);
    Location playerLocation = (Location) m.getPlayer().getLocation();
    this.gamePanel1.scrollRectToVisible(
        new Rectangle(playerLocation.getCoOrdinates().ycoordinate * 128,
            playerLocation.getCoOrdinates().xcoordinate * 128,
            1, 1));
    this.revalidate();
    this.repaint();
    this.setVisible(true);
    if (model.getPlayer().getLocation().hasMonster()) {
      JOptionPane.showMessageDialog(this, "You Died !!!");
      System.exit(0);
    }
    if (model.getStartEnd().get(1).equals(playerLocation)) {
      JOptionPane.showMessageDialog(this, "You Won !!!");
      System.exit(0);
    }
  }

  @Override
  public String shootArrow() {
    int result = new JOptionPane().showOptionDialog(this,
        new Object[]{
            new JLabel("enter distance to shoot"),
            this.shootingDist
        },
        "Enter Distance",
        JOptionPane.OK_CANCEL_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        null,
        null,
        1
    );
    if (result == JOptionPane.OK_OPTION) {
      return this.shootingDist.getText();
    }
    return null;
  }

  @Override
  public void setPreviousDirection(Direction direction) {
    this.gamePanel1.setpreviousDirection(direction);
  }
}

