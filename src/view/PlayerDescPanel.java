package view;

import dungeonmodel.ReadOnlyDungeonModel;
import dungeonmodel.Treasure;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

class PlayerDescPanel extends JPanel {
  private ReadOnlyDungeonModel model;
  private JLabel rubyDesc;
  private JLabel sapphireDesc;
  private JLabel diamondDesc;
  private JLabel locationDesc;
  private JLabel arrowDesc;
  private JLabel statusLabel;

  public PlayerDescPanel(ReadOnlyDungeonModel model) {
    this.setSize(200, 100);
    this.model = model;
    this.arrowDesc = new JLabel();
    this.arrowDesc.setForeground(Color.WHITE);
    this.diamondDesc = new JLabel();
    this.diamondDesc.setForeground(Color.WHITE);
    this.sapphireDesc = new JLabel();
    this.sapphireDesc.setForeground(Color.WHITE);
    this.rubyDesc = new JLabel();
    this.rubyDesc.setForeground(Color.WHITE);
    this.locationDesc = new JLabel();
    this.locationDesc.setForeground(Color.WHITE);
    this.statusLabel = new JLabel();
    this.statusLabel.setForeground(Color.WHITE);
    this.add(locationDesc);
    this.add(arrowDesc);
    this.add(diamondDesc);
    this.add(rubyDesc);
    this.add(sapphireDesc);
    this.add(statusLabel);
    this.setBackground(Color.BLACK);
    Font myFont = new Font("Monaco", Font.PLAIN, 15);
    this.locationDesc.setFont(myFont);
    this.sapphireDesc.setFont(myFont);
    this.diamondDesc.setFont(myFont);
    this.rubyDesc.setFont(myFont);
    this.arrowDesc.setFont(myFont);
    this.statusLabel.setFont(myFont);
  }

  void updatePlayerDesc(ReadOnlyDungeonModel model, String msg) {
    this.model = model;
    String locationText;
    if (this.model.getPlayer().getLocation().isCave()) {
      locationText = "Cave";
    } else {
      locationText = "Tunnel";
    }
    statusLabel.setText("<html><br><br><br><p>" + msg + "</p></html>");
    String arrowText;
    arrowText = String.format("Arrows x %d", this.model.getPlayer().getNumArrows());
    String diamondText;
    diamondText = String.format("Diamonds x %d", this.model.getPlayer().getTreasures()
        .get(Treasure.DIAMOND));
    String sapphireText;
    sapphireText = String.format("Sapphire x %d", this.model.getPlayer().getTreasures()
        .get(Treasure.SAPPHIRE));
    String rubyText;
    rubyText = String.format("Ruby x %d", this.model.getPlayer().getTreasures()
        .get(Treasure.RUBY));
    locationDesc.setText(locationText);
    arrowDesc.setText(arrowText);
    diamondDesc.setText(diamondText);
    sapphireDesc.setText(sapphireText);
    rubyDesc.setText(rubyText);
  }

}
