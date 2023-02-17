import dungeonmodel.Direction;
import dungeonmodel.ReadOnlyDungeonModel;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.IOException;
import view.DungeonView;

/**
 * A class for mocking the view.
 */
public class MockView implements DungeonView {
  private Appendable log;

  public MockView(Appendable log) {
    this.log = log;
  }

  @Override
  public void refresh(String status) {
    try {
      log.append("refreshed, status = " + status + "\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void addClickListener(MouseListener listener) {
    try {
      log.append("click listener controller added\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void addActionListener(ActionListener listener) {
    try {
      log.append("action listener controller added\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void addKeyListener(KeyListener listener) {
    try {
      log.append("key listener controller added\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void updateModel(ReadOnlyDungeonModel m) {
    try {
      log.append("update model called\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public String shootArrow() {
    try {
      log.append("Shooting initiated\n");
    } catch (IOException e) {
      // do nothing
    }
    return null;
  }

  @Override
  public void newModel(ReadOnlyDungeonModel model) {
    try {
      log.append("new model called\n");
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void setPreviousDirection(Direction south) {
    try {
      log.append("set Previous Direction called\n");
    } catch (IOException e) {
      // do nothing
    }
  }
}
