package main.commands.prints;

import main.app.TrainBuilder;
import main.commands.Command;

public class PrintTrainsCommand extends Command {
    public PrintTrainsCommand(TrainBuilder trainBuilder) {
        super(trainBuilder);
    }

    @Override
    public void execute() {
        trainBuilder.printTrains();
    }
}
