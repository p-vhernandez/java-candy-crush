package Components;

import utils.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoadingDialog extends JDialog {

    private final JPanel dialogPanel;
    private final JPanel animationPanel;
    private final JLabel loadingLabel;

    private final Timer timer;
    private int xVelocity, yVelocity;
    private int xPosition, yPosition;

    private final Image loadingImage;

    private static final int DIALOG_WIDTH = 200;
    private static final int DIALOG_HEIGHT = 110;
    private static final int ANIMATION_WIDTH = 100;
    private static final int ANIMATION_HEIGHT = 100;

    public LoadingDialog() {
        this.dialogPanel = new JPanel();
        this.animationPanel = new JPanel();
        this.loadingLabel = new JLabel("Loading");
        this.loadingImage = Utils.generateImage(this, "../resources/img/eye-ball.png");

        xVelocity = 10;
        yVelocity = 20;
        xPosition = 5;
        yPosition = 5;

        this.timer = new Timer(500, e -> animateLoading());

        initialize();
    }

    private void initialize() {
        setResizable(false);
        setModal(false);
        setUndecorated(true);
        setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        animationPanel.setSize(ANIMATION_WIDTH, ANIMATION_HEIGHT);
        animationPanel.setPreferredSize(animationPanel.getSize());
        animationPanel.setBackground(new Color(44, 54, 47));

        loadingLabel.setForeground(Color.white);
        setCustomFont(loadingLabel, "../resources/font/creepster-rg.ttf", 22f, Font.PLAIN);

        dialogPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        dialogPanel.setBackground(new Color(44, 54, 47));
        dialogPanel.add(animationPanel);
        dialogPanel.add(loadingLabel);

        add(dialogPanel);
        timer.start();
    }

    public void openLoading() {
        setVisible(true);
    }

    public void closeLoading() {
        setVisible(false);
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.drawImage(loadingImage, xPosition, yPosition, null);
    }

    private void animateLoading() {
        int loadingImageWidth = loadingImage.getWidth(null);
        int loadingImageHeight = loadingImage.getHeight(null);

        if (xPosition >= ANIMATION_WIDTH - (loadingImageWidth + xVelocity) + 5
                || (xPosition + xVelocity) < 0) {
            xVelocity = -xVelocity;
        }

        if (yPosition >= ANIMATION_HEIGHT - (loadingImageHeight + yVelocity) + 5
                || (yPosition + yVelocity) < 0) {
            yVelocity = -yVelocity;
        }

        xPosition = xPosition + xVelocity;
        yPosition = yPosition + yVelocity;

        if (loadingLabel.getText().endsWith("...")) {
            loadingLabel.setText("Loading");
        } else {
            loadingLabel.setText(loadingLabel.getText() + ".");
        }

        repaint();
    }

    private void setCustomFont(JLabel label, String path,
                               float fontSize, int fontStyle) {
        Font customFom = Utils.generateFont(this, path);
        if (customFom != null) {
            label.setFont(customFom.deriveFont(fontStyle, fontSize));
        }
    }

}
