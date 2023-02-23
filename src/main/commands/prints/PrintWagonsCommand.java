package main.commands.prints;

import main.app.TrainBuilder;
import main.commands.Command;

public class PrintWagonsCommand extends Command {
    public PrintWagonsCommand(TrainBuilder trainBuilder) {
        super(trainBuilder);
    }

    @Override
    public void execute() {
        trainBuilder.printWagons();
    }
}
