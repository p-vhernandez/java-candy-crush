package Model;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class BoardGridModel {

    private ArrayList<ActionListener> actionListeners = new ArrayList<>();
    private ArrayList<ChangeListener> changeListeners = new ArrayList<>();
    private boolean isEnabled = true;
    private boolean isPressed;

    public boolean isEnabled() {
        return isEnabled;
    }
    public boolean isPressed() {
        return isPressed;
    }
    public void setEnabled(boolean b) {
        this.isEnabled = b;
    }
    public void setPressed(boolean b) {
        this.isPressed = b;
    }


    public void addActionListener(ActionListener l) {
        actionListeners.add(l);
    }
    public void removeActionListener(ActionListener l) {
        actionListeners.remove(l);
    }

    public void addChangeListener(ChangeListener l) {
        changeListeners.add(l);
    }
    public void removeChangeListener(ChangeListener l) {
        changeListeners.remove(l);
    }
}
