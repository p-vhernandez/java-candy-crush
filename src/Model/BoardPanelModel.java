package Model;

import javax.swing.event.ChangeListener;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BoardPanelModel {

    private ArrayList<ActionListener> actionListeners = new ArrayList<>(); //triggering/fire button
    private ArrayList<ChangeListener> changeListeners = new ArrayList<>(); //change listening

    public BoardPanelModel() {}
}
