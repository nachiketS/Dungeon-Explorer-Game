package view;

import dungeonmodel.Direction;
import dungeonmodel.ReadOnlyDungeonModel;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

/**
 * An interface for the Dungeon view.
 */
public interface DungeonView {
  /**
   * Refresh method to refresh the view after any changes to the model.
   *
   * @param status : The change that takes place.
   */
  void refresh(String status);

  /**
   * The method to add the clicklistener to the view.
   *
   * @param listener : A MouseListener
   */
  void addClickListener(MouseListener listener);

  /**
   * The method to add the Action Listener to the view.
   *
   * @param listener : An ActionListener
   */
  void addActionListener(ActionListener listener);

  /**
   * The method to add the key listener to the view.
   *
   * @param listener : KeyListener
   */
  void addKeyListener(KeyListener listener);

  /**
   * The method to update the model.
   *
   * @param m : ReadOnlyDungeonModel.
   */
  void updateModel(ReadOnlyDungeonModel m);

  /**
   * A method which will initialize the shoot method.
   *
   * @return : String as status of the shoot.
   */
  String shootArrow();

  /**
   * A method to create and set a new model to the view.
   *
   * @param model : ReadOnlyDungeonModel
   */
  void newModel(ReadOnlyDungeonModel model);

  /**
   * A method to set the previous direction from which the player came.
   * This method is used for loading proper player images.
   *
   * @param direction : Direction
   */
  void setPreviousDirection(Direction direction);
}
