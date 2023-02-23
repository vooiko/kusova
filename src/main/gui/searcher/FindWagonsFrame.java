package main.gui.searcher;

import main.app.TrainBuilder;
import main.commands.Command;
import main.commands.wagons.FindWagonsSeatsNumberCommand;
import main.model.Wagon;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FindWagonsFrame extends JDialog {
    private TrainBuilder trainBuilder;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JList<Wagon> wagonsList;

    public FindWagonsFrame(TrainBuilder trainBuilder) {
        this.trainBuilder = trainBuilder;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        executeCommand(new FindWagonsSeatsNumberCommand(trainBuilder, textField1.getText(), textField2.getText()));
        ArrayList<Wagon> data = trainBuilder.getFoundedWagons();
        DefaultListModel<Wagon> model = new DefaultListModel<>();
        model.addAll(data);
        wagonsList.setModel(model);
    }

    private void onCancel() {
        dispose();
    }

    private void executeCommand(Command command) {
        command.execute();
    }
}
