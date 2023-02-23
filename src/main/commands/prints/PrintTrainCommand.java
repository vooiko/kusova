package main.commands.prints;

import main.app.TrainBuilder;
import main.commands.Command;
import main.extensions.Extensions;

import java.util.UUID;

public class PrintTrainCommand extends Command {
    private final UUID id;
    public PrintTrainCommand(TrainBuilder trainBuilder, String id) {
        super(trainBuilder);
        this.id = Extensions.parseId(id);
    }

    @Override
    public void execute() {
        if (id != null) {
            trainBuilder.printTrain(id);
        } else {
            System.out.println("Incorrect entered data!");
        }
    }
}
