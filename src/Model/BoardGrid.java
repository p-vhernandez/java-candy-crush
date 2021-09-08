package Model;

import javax.swing.*;
import java.awt.*;

public class BoardGrid extends JPanel{


    public BoardGrid(int size) {
        GridLayout gridLayout = new GridLayout(size, size);
        setLayout(gridLayout);
        setBackground(Color.green);
    }
}
