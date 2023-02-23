package main.app;

import main.Settings;
import main.commands.Command;
import main.commands.SaveCommand;
import main.commands.assign.wagon.AssignWagonCommand;
import main.commands.assign.wagon.RemoveAssignWagons;
import main.commands.assign.wagon.SortWagonsCommand;
import main.commands.prints.PrintTrainCommand;
import main.commands.prints.PrintTrainsCommand;
import main.commands.prints.PrintWagonsCommand;
import main.commands.trains.CreateTrainCommand;
import main.commands.trains.DeleteTrainCommand;
import main.commands.wagons.AddWagonCommand;
import main.commands.wagons.DeleteWagonCommand;
import main.commands.wagons.FindWagonsSeatsNumberCommand;
import main.gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TransportApp {
    public static Logger logger;
    private TrainBuilder trainBuilder;

    public TransportApp() {
        initLogger();
        trainBuilder = new TrainBuilder();
    }

    public void initGUI() {
        JFrame frame = new JFrame("Train builder");
        frame.setContentPane(new MainFrame(trainBuilder).getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    public void initUI() {
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;

        System.out.println("Choose command, then print it number. Like this: '10'");
        printInstructions();
        while (flag) {
            System.out.print("Command: ");
            String inputCommand = scanner.next();
            switch (inputCommand) {
                // Save data
                case ("save") -> {
                    executeCommand(new SaveCommand(trainBuilder));
                    System.out.println();
                }

                // Create train
                case ("1") -> {
                    System.out.print("Enter train's name: ");
                    String name = scanner.next();
                    System.out.print("Enter train's code: ");
                    String code = scanner.next();
                    System.out.print("Enter train's expected sits: ");
                    String expectedSits = scanner.next();
                    System.out.println("Enter coefficients. Like this '0.5'. " +
                            "That's mean that 50% of passenger seats will be in the ordinary wagons, for example." +
                            "Note! Summary coefficients must be 1.0");
                    System.out.print("Enter coefficient ordinary comfort type:");
                    String coeff1 = scanner.next();
                    System.out.print("Enter coefficient business comfort type:");
                    String coeff2 = scanner.next();
                    System.out.print("Enter coefficient vip comfort type:");
                    String coeff3 = scanner.next();
                    executeCommand(new CreateTrainCommand(trainBuilder, name, code, expectedSits, coeff1, coeff2, coeff3));
                    System.out.println();
                }

                // Delete train
                case ("2") -> {
                    System.out.print("Enter train id: ");
                    String id = scanner.next();
                    executeCommand(new DeleteTrainCommand(trainBuilder, id));
                    System.out.println();
                }


                // Add wagon
                case ("3") -> {
                    System.out.println("""
                    Enter wagon type:
                    In can be:
                    \tordinary
                    \tbusiness
                    \tvip""");
                    String wagonType = scanner.next();
                    System.out.print("Enter seats number: ");
                    String sitsNumber = scanner.next();
                    System.out.print("Enter things weight per person: ");
                    String thingsWeight = scanner.next();
                    executeCommand(new AddWagonCommand(trainBuilder, wagonType, sitsNumber, thingsWeight));
                    System.out.println();
                }


                // Delete wagon
                case ("4") -> {
                    System.out.print("Enter wagon's id: ");
                    String id = scanner.next();
                    executeCommand(new DeleteWagonCommand(trainBuilder, id));
                    System.out.println();
                }


                // Find wagons by seats number
                case ("5") -> {
                    System.out.print("Enter min seats number: ");
                    String minValue = scanner.next();
                    System.out.print("Enter max seats number:");
                    String maxValue = scanner.next();
                    executeCommand(new FindWagonsSeatsNumberCommand(trainBuilder, minValue, maxValue));
                    System.out.println();
                }


                // Assign wagons to the train
                case ("6") -> {
                    System.out.print("Enter train id: ");
                    String trainId = scanner.next();
                    System.out.print("Enter wagon id: ");
                    String wagonId = scanner.next();
                    System.out.print("Enter number of wagons: ");
                    String number = scanner.next();

                    executeCommand(new AssignWagonCommand(trainBuilder, trainId, wagonId, number));
                    System.out.println();
                }


                // Remove wagons from the train
                case ("7") -> {
                    System.out.print("Enter train's id: ");
                    String trainId = scanner.next();
                    System.out.print("Enter wagon's id: ");
                    String wagonId = scanner.next();

                    executeCommand(new RemoveAssignWagons(trainBuilder, trainId, wagonId));
                    System.out.println();
                }


                // Sort wagons
                case ("8") -> {
                    System.out.print("Enter train's id:");
                    String id = scanner.next();

                    executeCommand(new SortWagonsCommand(trainBuilder, id));
                }


                // Print trains;
                case ("9") -> executeCommand(new PrintTrainsCommand(trainBuilder));

                // Print train;
                case ("10") -> {
                    System.out.print("Enter train's id: ");
                    String id = scanner.next();
                    executeCommand(new PrintTrainCommand(trainBuilder, id));
                }

                // Print wagons;
                case ("11") -> executeCommand(new PrintWagonsCommand(trainBuilder));

                // HELP
                case ("help") -> {
                    printInstructions();
                }


                // EXIT
                case ("exit") -> {
                    flag = false;
                    executeCommand(new SaveCommand(trainBuilder));
                }
                default -> System.out.println("Incorrect data!");
            }
        }
    }

    public static void initLogger() {
        logger = Logger.getLogger("TransportAppLogger");
        try {
            FileHandler fileHandler = new FileHandler(Settings.logsFilePath);
            logger.addHandler(fileHandler);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    private void printInstructions() {
        System.out.println(
                """
                        'save'. Save data;
                        1. Create train;
                        2. Delete train;
                        3. Add wagon;
                        4. Delete wagon;
                        5. Find wagon;
                        6. Assign wagons to the train;
                        7. Remove wagons from the train;
                        8. Sort wagons;
                        9. Print trains;
                        10. Print train;
                        11. Print wagons;
                        'help'. Show instructions
                        'exit'. Save data and stop program"""
        );
    }

    private void executeCommand(Command command) {
        command.execute();
    }
}
