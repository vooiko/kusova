package main.gui.data.window;

import main.app.TransportApp;
import main.model.AssignedWagon;
import main.model.Train;
import main.model.Wagon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class DataFrame extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel idHolder;
    private JLabel dataHolder1;
    private JLabel dataHolder2;
    private JLabel dataHolder3;
    private JLabel dataHolder4;
    private JLabel mainLabelHolder;
    private JLabel textPlaceholder;
    private JLabel textPlaceholder1;
    private JLabel textPlaceholder2;
    private JLabel textPlaceholder3;
    private JLabel textPlaceholder4;


    public DataFrame(Wagon wagon) {
        initDefaultOptions();
        /// init gui
        mainLabelHolder.setText("Wagon {" + wagon.getWagonId() + "}");
        initIcon("lib/drawable/wagon.png");
        // data
        textPlaceholder.setText("Id: ");
        idHolder.setText(wagon.getWagonId().toString());
        textPlaceholder1.setText("Comfort type: ");
        dataHolder1.setText(wagon.getWagonTypeName());
        textPlaceholder2.setText("Seats numbers: ");
        dataHolder2.setText(String.valueOf(wagon.getSeatsNumber()));
        textPlaceholder3.setText("Max things weight per person: ");
        dataHolder3.setText(String.valueOf(wagon.getWeightPerPerson()));
        textPlaceholder4.setVisible(false);
        dataHolder4.setVisible(false);
    }

    public DataFrame(Train train) {
        initDefaultOptions();
        /// init gui
        initIcon("lib/drawable/train.png");
        mainLabelHolder.setText("Train {" + train.getId() + "}");
        // data
        textPlaceholder.setText("Id: ");
        idHolder.setText(train.getId().toString());
        textPlaceholder1.setText("Name: ");
        dataHolder1.setText(train.getName());
        textPlaceholder2.setText("Code: ");
        dataHolder2.setText(train.getCode());
        textPlaceholder3.setVisible(false);
        dataHolder3.setVisible(false);
        textPlaceholder4.setVisible(false);
        dataHolder4.setVisible(false);
    }

    public DataFrame(AssignedWagon assignedWagon) {
        initDefaultOptions();
        ///init gui
        initIcon("lib/drawable/wagon.png");
        mainLabelHolder.setText("Assigned wagon {" + assignedWagon.getAssignedId() + "}");
        // data
        textPlaceholder.setText("Id: ");
        idHolder.setText(assignedWagon.getWagonId().toString());
        textPlaceholder1.setText("Comfort type: ");
        dataHolder1.setText(assignedWagon.getWagonTypeName());
        textPlaceholder2.setText("Seats numbers: ");
        dataHolder2.setText(String.valueOf(assignedWagon.getSeatsNumber()));
        textPlaceholder3.setText("Max things weight per person: ");
        dataHolder3.setText(String.valueOf(assignedWagon.getWeightPerPerson()));
        textPlaceholder4.setText("Number: ");
        dataHolder4.setText(String.valueOf(assignedWagon.getNumber()));
    }

    private void initDefaultOptions() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());
        // call onOK() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        // call onOK() on ESCAPE
        contentPane.registerKeyboardAction(e -> onOK(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void initIcon(String pathname) {
        try {
            mainLabelHolder.setIcon(new ImageIcon(ImageIO.read(new File(pathname))));
        } catch (IOException e) {
            TransportApp.logger.severe("Cannot read image icon! " + e.getMessage());
        }
    }

    private void onOK() {
        dispose();
    }
}
