package main.commands.assign.wagon;

import main.app.TrainBuilder;
import main.commands.Command;
import main.extensions.Extensions;

import java.util.UUID;

public class RemoveAssignWagons extends Command {
    private final UUID trainId;
    private final UUID assignedId;

    public RemoveAssignWagons(TrainBuilder trainBuilder, String trainId, String assignedId) {
        super(trainBuilder);
        this.trainId = Extensions.parseId(trainId);
        this.assignedId = Extensions.parseId(assignedId);
    }

    public RemoveAssignWagons(TrainBuilder trainBuilder, UUID trainId, UUID assignedId) {
        super(trainBuilder);
        this.trainId = trainId;
        this.assignedId = assignedId;
    }

    @Override
    public void execute() {
        if (trainId != null && assignedId != null) {
            trainBuilder.removeWagonFromTheTrain(trainId, assignedId);
        } else {
            System.out.println("Incorrect entered data!");
        }
    }
}
