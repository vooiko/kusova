package main.commands.trains;

import main.app.TrainBuilder;
import main.commands.Command;
import main.extensions.Extensions;

import java.util.UUID;

public class DeleteTrainCommand extends Command {
    private final UUID id;

    public DeleteTrainCommand(TrainBuilder trainBuilder, String id) {
        super(trainBuilder);
        this.id = Extensions.parseId(id);
    }

    public DeleteTrainCommand(TrainBuilder trainBuilder, UUID id) {
        super(trainBuilder);
        this.id = id;
    }

    @Override
    public void execute() {
        if (id != null) {
            trainBuilder.deleteTrain(id);
        } else System.out.println("Incorrect entered data!");
    }
}
