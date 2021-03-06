package utils.dialogs;

import utils.Utils;

import javax.swing.*;
import javax.swing.border.LineBorder;
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
    private static final int ANIMATION_HEIGHT = 90;

    public LoadingDialog(JFrame parent) {
        super(parent, null, false);

        this.dialogPanel = new JPanel();
        this.animationPanel = new JPanel();
        this.loadingLabel = new JLabel("Loading");
        this.loadingImage = Utils.generateImage(LoadingDialog.class, "/resources/img/EYEBALL.png")
                .getScaledInstance(32, 32, Image.SCALE_SMOOTH);

        xVelocity = 10;
        yVelocity = 20;
        xPosition = 5;
        yPosition = 5;

        this.timer = new Timer(500, e -> animateLoading());

        initialize();
    }

    private void initialize() {
        setResizable(false);
        setUndecorated(true);
        setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);

        animationPanel.setSize(ANIMATION_WIDTH, ANIMATION_HEIGHT);
        animationPanel.setPreferredSize(animationPanel.getSize());
        animationPanel.setBackground(Utils.darkBackground);

        loadingLabel.setForeground(Color.white);
        Utils.setCustomFont(LoadingDialog.class, loadingLabel,
                "/resources/font/creepster-rg.ttf", 22f, Font.PLAIN);


        dialogPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        dialogPanel.setBorder(new LineBorder(Utils.halloweenOrange, 5));
        dialogPanel.setBackground(Utils.darkBackground);
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

}
