package Components;

import utils.Utils;

import javax.swing.*;

public class LoadingDialog extends JDialog {

    private final JFrame parent;
    private final JLabel loadingLabel;

    public LoadingDialog(JFrame parent) {
        this.parent = parent;
        this.loadingLabel = Utils.generateLoadingJLabel(this);

        initialize();
    }

    private void initialize() {
        add(loadingLabel);
        setResizable(false);
        setModal(false);
        setUndecorated(true);
        setSize(400, 300);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public void openLoading() {
        setVisible(true);
    }

    public void closeLoading() {
        setVisible(false);
    }

}
