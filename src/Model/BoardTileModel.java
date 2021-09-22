package Model;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class BoardTileModel implements ButtonModel {

    private ArrayList<ActionListener> actionListeners = new ArrayList<>();
    private ArrayList<ChangeListener> changeListeners = new ArrayList<>();
    private boolean isArmed;
    private boolean isEnabled = true;
    private boolean isPressed;
    private boolean isSelected;
    private boolean isRollover;
    /**
     * Indicates partial commitment towards triggering the
     * button.
     *
     * @return <code>true</code> if the button is armed,
     * and ready to be triggered
     * @see #setArmed
     */
    @Override
    public boolean isArmed() {
        return isArmed;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public boolean isPressed() {
        return isPressed;
    }

    @Override
    public boolean isRollover() {
        return isRollover;
    }

    @Override
    public void setArmed(boolean b) {
        this.isArmed = b;
    }

    @Override
    public void setSelected(boolean b) {
        this.isSelected = b;
    }

    @Override
    public void setEnabled(boolean b) {
        this.isEnabled = b;
    }

    @Override
    public void setPressed(boolean b) {
        this.isPressed = b;
    }

    @Override
    public void setRollover(boolean b) {
        this.isRollover = b;
    }

    /**
     * Sets the keyboard mnemonic (shortcut key or
     * accelerator key) for the button.
     *
     * @param key an int specifying the accelerator key
     */
    @Override
    public void setMnemonic(int key) {}

    /**
     * Gets the keyboard mnemonic for the button.
     *
     * @return an int specifying the accelerator key
     * @see #setMnemonic
     */
    @Override
    public int getMnemonic() { return 0; }

    /**
     * Sets the action command string that gets sent as part of the
     * <code>ActionEvent</code> when the button is triggered.
     *
     * @param s the <code>String</code> that identifies the generated event
     * @see #getActionCommand
     * @see ActionEvent#getActionCommand
     */
    @Override
    public void setActionCommand(String s) {

    }

    /**
     * Returns the action command string for the button.
     *
     * @return the <code>String</code> that identifies the generated event
     * @see #setActionCommand
     */
    @Override
    public String getActionCommand() {
        return null;
    }

    /**
     * Identifies the group the button belongs to --
     * needed for radio buttons, which are mutually
     * exclusive within their group.
     *
     * @param group the <code>ButtonGroup</code> the button belongs to
     */
    @Override
    public void setGroup(ButtonGroup group) {

    }

    /**
     * Adds an <code>ActionListener</code> to the model.
     *
     * @param l the listener to add
     */
    @Override
    public void addActionListener(ActionListener l) {
        actionListeners.add(l);
    }

    /**
     * Removes an <code>ActionListener</code> from the model.
     *
     * @param l the listener to remove
     */
    @Override
    public void removeActionListener(ActionListener l) {
        actionListeners.remove(l);
    }

    /**
     * Returns the selected items or {@code null} if no
     * items are selected.
     *
     * @return the list of selected objects, or {@code null}
     */
    @Override
    public Object[] getSelectedObjects() {
        return new Object[0];
    }

    /**
     * Adds an <code>ItemListener</code> to the model.
     *
     * @param l the listener to add
     */
    @Override
    public void addItemListener(ItemListener l) {

    }

    /**
     * Removes an <code>ItemListener</code> from the model.
     *
     * @param l the listener to remove
     */
    @Override
    public void removeItemListener(ItemListener l) {

    }

    /**
     * Adds a <code>ChangeListener</code> to the model.
     *
     * @param l the listener to add
     */
    @Override
    public void addChangeListener(ChangeListener l) {
        changeListeners.add(l);
    }

    /**
     * Removes a <code>ChangeListener</code> from the model.
     *
     * @param l the listener to remove
     */
    @Override
    public void removeChangeListener(ChangeListener l) {
        changeListeners.remove(l);
    }
}
