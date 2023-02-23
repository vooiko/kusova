package main.commands.assign.wagon;

import main.app.TrainBuilder;
import main.commands.Command;
import main.extensions.Extensions;

import java.util.UUID;

public class AssignWagonCommand extends Command {
    private final UUID trainId;
    private final UUID wagonId;
    private final int number;

    public AssignWagonCommand(TrainBuilder trainBuilder, String trainId, String wagonId, String number) {
        super(trainBuilder);
        this.trainId = Extensions.parseId(trainId);
        this.wagonId = Extensions.parseId(wagonId);
        this.number = Extensions.parseInt(number);
    }

    public AssignWagonCommand(TrainBuilder trainBuilder, UUID trainId, UUID wagonId, String number) {
        super(trainBuilder);
        this.trainId = trainId;
        this.wagonId = wagonId;
        this.number = Extensions.parseInt(number);
    }

    @Override
    public void execute() {
        if (trainId != null && wagonId != null) {
            trainBuilder.addWagonToTheTrain(trainId, wagonId, number);
        } else {
            System.out.println("Incorrect entered data!");
        }
    }
}
