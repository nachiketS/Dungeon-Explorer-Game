package view;

import dungeonmodel.ReadOnlyDungeonModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

class MyMenuBar extends JMenuBar {
  private final JMenuItem resetSameSeed;
  private final JMenuItem resetNewSeed;
  private final JMenuItem customGame;
  private JTextField rowsText;
  private JTextField colsText;
  private JTextField interconnectivityText;
  private JTextField treasuresText;
  private JComboBox wrappingOptions;
  private JComboBox difficultyOptions;
  private JTextField numberOfRobbersText;
  private JTextField numberOfPitsText;

  public MyMenuBar(ReadOnlyDungeonModel model) {
    JMenu menu;
    menu = new JMenu("File");
    customGame = new JMenuItem("New Custom Game");
    menu.add(customGame);
    JMenuItem restart = new JMenu("restart");
    this.resetSameSeed = new JMenuItem("Reset game with same Dungeon");
    this.resetNewSeed = new JMenuItem("Reset game with new dungeon");
    restart.add(resetSameSeed);
    restart.add(resetNewSeed);
    JMenuItem exit = new JMenuItem("exit");
    exit.addActionListener(e -> System.exit(0));
    menu.add(restart);
    this.add(menu);
    this.add(exit);
    this.rowsText = new JTextField();
    this.rowsText.setText(String.valueOf(model.getNumRows()));
    this.colsText = new JTextField();
    this.colsText.setText(String.valueOf(model.getNumCols()));
    this.rowsText.setSize(200, 12);
    this.colsText.setSize(200, 12);
    String[] trueFalse = {"true", "false"};
    this.wrappingOptions = new JComboBox(trueFalse);
    this.wrappingOptions.setSelectedItem(model.getInterconnectivity());
    String[] difficulties = {"1", "2", "3"};
    this.difficultyOptions = new JComboBox(difficulties);
    this.difficultyOptions.setSelectedItem(model.getDifficulty());
    this.interconnectivityText = new JTextField();
    this.interconnectivityText.setText(String.valueOf(model.getInterconnectivity()));
    this.treasuresText = new JTextField();
    this.treasuresText.setText(String.valueOf(model.getPercentageTreasures()));
    this.numberOfPitsText = new JTextField();
    this.numberOfPitsText.setText(String.valueOf(model.getNumberOfPits()));
    this.numberOfRobbersText = new JTextField();
    this.numberOfRobbersText.setText(String.valueOf(model.getNumberOfRobbers()));
  }

  public void addActionListener(ActionListener listener) {
    resetNewSeed.setActionCommand("resetNewSeed");
    resetNewSeed.addActionListener(listener);
    resetSameSeed.setActionCommand("resetSameSeed");
    resetSameSeed.addActionListener(listener);
    customGame.setActionCommand("custom");
    customGame.addActionListener(e -> {
      int result = JOptionPane.showOptionDialog(this,
          new Object[]{
              new JLabel("enter num rows"),
              this.rowsText,
              new JLabel("Enter num cols "),
              this.colsText,
              new JLabel("Enter interconnectivity"),
              this.interconnectivityText,
              new JLabel("Select Wrapping"),
              this.wrappingOptions,
              new JLabel("Enter percentage of treasures and arrows"),
              this.treasuresText,
              new JLabel("Enter Difficulty"),
              this.difficultyOptions,
              new JLabel("Enter number of Pits"),
              this.numberOfPitsText,
              new JLabel("Enter number of Robbers"),
              this.numberOfRobbersText
          },
          "Enter Parameters",
          JOptionPane.OK_CANCEL_OPTION,
          JOptionPane.QUESTION_MESSAGE,
          null,
          null,
          null);
      if (result == JOptionPane.OK_OPTION) {
        ActionEvent custom = new ActionEvent(
            new ArrayList<>(List.of(
                rowsText.getText(),
                colsText.getText(),
                interconnectivityText.getText(),
                wrappingOptions.getSelectedItem(),
                treasuresText.getText(),
                difficultyOptions.getSelectedItem(),
                numberOfPitsText.getText(),
                numberOfRobbersText.getText()
            )), 0, "custom");
        listener.actionPerformed(custom);
      }
    });
  }


}
