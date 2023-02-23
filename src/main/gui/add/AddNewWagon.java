package main.gui.add;

import main.app.TrainBuilder;
import main.commands.Command;
import main.commands.wagons.AddWagonCommand;
import main.model.ComfortTypes;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;

public class AddNewWagon extends JDialog {
    private TrainBuilder trainBuilder;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JComboBox<ComfortTypes> comboBox1;

    public AddNewWagon(@NotNull TrainBuilder trainBuilder) {
        this.trainBuilder = trainBuilder;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        initComboBox();
    }

    private void onOK() {
        if (!Objects.equals(textField1.getText(), "") && !Objects.equals(textField2.getText(), "")) {
            executeCommand(new AddWagonCommand(trainBuilder, (ComfortTypes) comboBox1.getSelectedItem(), textField1.getText(), textField2.getText()));
            dispose();
        }
    }

    private void onCancel() {
        dispose();
    }

    private void initComboBox() {
        for (var types: ComfortTypes.values()) {
            comboBox1.addItem(types);
        }
    }

    private void executeCommand(@NotNull Command command) {
        command.execute();
    }
}
