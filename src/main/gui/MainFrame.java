package main.gui;

import main.app.TrainBuilder;
import main.app.TransportApp;
import main.commands.Command;
import main.commands.SaveCommand;
import main.commands.assign.wagon.AssignWagonCommand;
import main.commands.assign.wagon.RemoveAssignWagons;
import main.commands.assign.wagon.SortWagonsCommand;
import main.commands.trains.DeleteTrainCommand;
import main.commands.wagons.DeleteWagonCommand;
import main.gui.add.AddNewWagon;
import main.gui.add.CreateNewTrainFrame;
import main.gui.data.window.DataFrame;
import main.gui.searcher.FindWagonsFrame;
import main.model.AssignedWagon;
import main.model.Train;
import main.model.Wagon;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class MainFrame extends JFrame {
    // LINK TO THE TRAIN BUILDER
    private final TrainBuilder trainBuilder;

    // ui elements
    private JPanel mainPanel;
    private JLabel appNameLabel;
    private JButton closeButton;
    private JButton createTrainButton;
    private JButton deleteTrainButton;
    private JButton assignWagonButton;
    private JButton removeWagonButton;
    private JButton sortWagonsButton;
    private JButton addWagonButton;
    private JButton deleteWagonButton;
    private JButton findWagonBySeatsNumberButton;
    private JButton saveButton;
    private JButton refreshButton;

    // JLists
    private JList<Wagon> wagonsList;
    private JList<Train> trainsList;
    private JList<AssignedWagon> assignedWagonsList;

    // selected items
    private UUID selectedWagon;
    private UUID selectedTrain;
    private UUID selectedAssignedWagons;

    //flags
    private boolean isWagonsListInitialized;
    private boolean isTrainListInitialized;
    private boolean isAssignedWagonsInitialized;

    // icons
    private ImageIcon trainImage;

    public MainFrame(TrainBuilder trainBuilder) {
        this.trainBuilder = trainBuilder;
        setContentPane(mainPanel);
        getRootPane().setDefaultButton(closeButton);

        // flags
        isTrainListInitialized = false;
        isWagonsListInitialized = false;
        isAssignedWagonsInitialized = false;

        // icons
        downloadIcons();
        appNameLabel.setIcon(trainImage);

        /// init buttons
        // wagons
        addWagonButton.addActionListener(l -> onAddWagonAction());
        deleteWagonButton.addActionListener(l -> onDeleteWagonAction());
        findWagonBySeatsNumberButton.addActionListener(l -> onFindWagonsAction());
        // trains
        createTrainButton.addActionListener(l -> onCreateTrainAction());
        deleteTrainButton.addActionListener(l -> onDeleteTrainAction());
        // assigned wagons
        assignWagonButton.addActionListener(l -> onAssignWagonAction());
        removeWagonButton.addActionListener(l -> onRemoveWagonAction());
        sortWagonsButton.addActionListener(l -> onSortWagonsAction());
        // other
        refreshButton.addActionListener(l -> onRefreshAction());
        closeButton.addActionListener(l -> onCloseAction());

        /// init lists
        setWagonsListInitialized();
        setTrainListInitialized();

        /// init escape
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // call onCloseAction() on ESCAPE
        mainPanel.registerKeyboardAction(e -> onCloseAction(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onAddWagonAction() {
        AddNewWagon dialog = new AddNewWagon(trainBuilder);
        dialog.setLocationRelativeTo(null);
        dialog.pack();
        dialog.setVisible(true);
        setWagonsListInitialized();
    }

    private void onDeleteWagonAction() {
        executeCommand(new DeleteWagonCommand(trainBuilder, selectedWagon));
        setWagonsListInitialized();
    }

    private void onFindWagonsAction() {
        FindWagonsFrame dialog = new FindWagonsFrame(trainBuilder);
        dialog.setLocationRelativeTo(null);
        dialog.pack();
        dialog.setVisible(true);
    }

    private void onCreateTrainAction() {
        CreateNewTrainFrame dialog = new CreateNewTrainFrame(trainBuilder);
        dialog.setLocationRelativeTo(null);
        dialog.pack();
        dialog.setVisible(true);
        setTrainListInitialized();
    }

    private void onDeleteTrainAction() {
        executeCommand(new DeleteTrainCommand(trainBuilder, selectedTrain));
        setTrainListInitialized();
    }

    private void onAssignWagonAction() {
        var getDataFrame = new JFrame("Number input");
        String number = JOptionPane.showInputDialog(getDataFrame, "Enter number of wagons: ");
        executeCommand(new AssignWagonCommand(trainBuilder, selectedTrain, selectedWagon, number));
        setAssignedWagonsInitialized(null);
    }

    private void onRemoveWagonAction() {
        executeCommand(new RemoveAssignWagons(trainBuilder, selectedTrain, selectedAssignedWagons));
        setAssignedWagonsInitialized(selectedTrain);
    }

    private void onSortWagonsAction() {
        executeCommand(new SortWagonsCommand(trainBuilder, selectedTrain));
        setAssignedWagonsInitialized(selectedTrain);
    }

    private void onCloseAction() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private void onRefreshAction() {
        setTrainListInitialized();
        setWagonsListInitialized();
        setAssignedWagonsInitialized(null);
    }

    private void downloadIcons() {
        try {
            trainImage = new ImageIcon(ImageIO.read(new File("lib/drawable/train.png")));
        } catch (IOException e) {
            TransportApp.logger.severe("Cannot read images!");
            TransportApp.logger.severe(e.getMessage());
        }
    }

    private void setWagonsListInitialized() {
        // init default model
        DefaultListModel<Wagon> model = new DefaultListModel<>();
        // fill by data
        ArrayList<Wagon> wagons = trainBuilder.getWagons();
        model.addAll(wagons);
        wagonsList.setModel(model);

        // if listener isn't added
        if (!isWagonsListInitialized) {
            wagonsList.addListSelectionListener(l -> {
                if (wagonsList.getSelectedValue() != null) {
                    selectedWagon = wagonsList.getSelectedValue().getWagonId();
                }
            });

            wagonsList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                        var wagons = trainBuilder.getWagons();
                        for (var wagon : wagons) {
                            if (wagon.getWagonId().compareTo(selectedWagon) == 0) {
                                DataFrame dialog = new DataFrame(wagon);
                                dialog.setLocationRelativeTo(null);
                                dialog.pack();
                                dialog.setVisible(true);
                                break;
                            }
                        }
                    }
                    super.mouseClicked(e);
                }
            });
            isWagonsListInitialized = true;
        }
    }

    private void setTrainListInitialized() {
        // init default model
        DefaultListModel<Train> model = new DefaultListModel<>();
        // fill by data
        var trains = trainBuilder.getTrains();
        model.addAll(trains);
        trainsList.setModel(model);

        if (!isTrainListInitialized) {
            trainsList.addListSelectionListener(l -> {
                if (trainsList.getSelectedValue() != null) {
                    selectedTrain = trainsList.getSelectedValue().getId();
                    setAssignedWagonsInitialized(selectedTrain);
                }
            });

            trainsList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                        var trains = trainBuilder.getTrains();
                        for (var train : trains) {
                            if (train.getId().compareTo(selectedTrain) == 0) {
                                DataFrame dialog = new DataFrame(train);
                                dialog.setLocationRelativeTo(null);
                                dialog.pack();
                                dialog.setVisible(true);
                                break;
                            }
                        }
                    }
                    super.mouseClicked(e);
                }
            });
            isTrainListInitialized = true;
        }
    }

    private void setAssignedWagonsInitialized(UUID trainId) {
        // init default model
        DefaultListModel<AssignedWagon> model = new DefaultListModel<>();
        /// fill by data
        // get trains
        ArrayList<Train> trains = trainBuilder.getTrains();
        ArrayList<AssignedWagon> assignedWagons;
        if (trainId != null) {
            for (Train train : trains) {
                // find train that we need
                if (trainId.compareTo(train.getId()) == 0) {
                    // fill data
                    assignedWagons = train.getWagons();
                    model.addAll(assignedWagons);
                    break;
                }
            }
        }
        assignedWagonsList.setModel(model);

        if (!isAssignedWagonsInitialized) {
            assignedWagonsList.addListSelectionListener(l -> {
                if (assignedWagonsList.getSelectedValue() != null) {
                    selectedAssignedWagons = assignedWagonsList.getSelectedValue().getAssignedId();
                }
            });

            // Listener isn't setting action for all items
            /*assignedWagonsList.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                        for (Train train : trainBuilder.getTrains()) {
                            if (trainId != null && train.getId().compareTo(trainId) == 0) {
                                for (var wagon : train.getWagons()) {
                                    if (wagon.getAssignedId().compareTo(selectedAssignedWagons) == 0) {
                                        DataFrame dialog = new DataFrame(wagon);
                                        dialog.setLocationRelativeTo(null);
                                        dialog.pack();
                                        dialog.setVisible(true);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    super.mouseClicked(e);
                }
            });*/
            isAssignedWagonsInitialized = true;
        }
    }

    private void executeCommand(Command command) {
        command.execute();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
