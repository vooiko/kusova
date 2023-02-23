package main.gui.add;

import main.app.TrainBuilder;
import main.commands.Command;
import main.commands.trains.CreateTrainCommand;

import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;

public class CreateNewTrainFrame extends JDialog {
    private final TrainBuilder trainBuilder;

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameTextField;
    private JTextField codeTextField;
    private JTextField seatsNumberTextField;
    private JTextField ordinaryCoefficientField;
    private JTextField higherCoefficientField;
    private JTextField vipCoefficientField;

    public CreateNewTrainFrame(TrainBuilder trainBuilder) {
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
    }

    private void onOK() {
        if (!Objects.equals(nameTextField.getText(), "") && !Objects.equals(codeTextField.getText(), "") && !Objects.equals(seatsNumberTextField.getText(), "")) {
            if (Float.parseFloat(ordinaryCoefficientField.getText()) +
                    Float.parseFloat(higherCoefficientField.getText()) +
                    Float.parseFloat(vipCoefficientField.getText()) == 1) {
                executeCommand(new CreateTrainCommand(trainBuilder, nameTextField.getText(), codeTextField.getText(), seatsNumberTextField.getText(),
                        ordinaryCoefficientField.getText(),
                        higherCoefficientField.getText(),
                        vipCoefficientField.getText()));
                dispose();
            }
        }
    }

    private void onCancel() {
        dispose();
    }

    private void executeCommand(Command command) {
        command.execute();
    }
}
