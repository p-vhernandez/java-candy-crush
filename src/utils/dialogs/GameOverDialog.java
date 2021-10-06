package utils.dialogs;

import utils.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Objects;

public class GameOverDialog extends JDialog {

    private final JPanel dialogPanel;

    private final JLabel lblGameOver;
    private final JButton btnDismiss;

    private static final int DIALOG_WIDTH = 300;
    private static final int DIALOG_HEIGHT = 150;

    public GameOverDialog() {
        this.dialogPanel = new JPanel();

        lblGameOver = new JLabel();
        btnDismiss = new JButton();

        initialize();
    }

    private void initialize() {
        setResizable(false);
        setModal(false);
        setUndecorated(true);
        setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBorder(new LineBorder(Utils.halloweenOrange, 5));
        dialogPanel.setBackground(Utils.darkBackground);

        setUpInfo();
        setUpButton();

        add(dialogPanel);
    }

    private void setUpInfo() {
        lblGameOver.setText("Game over.");
        lblGameOver.setForeground(Color.white);
        Utils.setCustomFont(this, lblGameOver, "../../resources/font/deanna.ttf", 32f, Font.PLAIN);
        lblGameOver.setIcon(new ImageIcon(Utils.generateImage(this, "../../resources/img/eye-ball.png")
                .getScaledInstance(45, 45, Image.SCALE_SMOOTH)));

        lblGameOver.setBorder(new EmptyBorder(20, 0, 20, 0));
        lblGameOver.setAlignmentX(Component.CENTER_ALIGNMENT);
        dialogPanel.add(lblGameOver);
    }

    private void setUpButton() {
        btnDismiss.setText("Accept defeat");
        btnDismiss.setBackground(Utils.halloweenOrange);
        btnDismiss.setForeground(Utils.darkBackground);
        btnDismiss.addActionListener(listener -> dispose());
        btnDismiss.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnDismiss.setFont(Objects.requireNonNull(
                Utils.generateFont(this, "../../resources/font/caramel-rg.ttf"))
                .deriveFont(Font.BOLD, 28f)
        );

        dialogPanel.add(btnDismiss);
    }

    public void paint(Graphics graphics) {
        super.paint(graphics);
    }

}
