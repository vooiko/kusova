package main.commands.wagons;

import main.app.TrainBuilder;
import main.commands.Command;
import main.extensions.Extensions;
import main.model.ComfortTypes;

public class AddWagonCommand extends Command {
    private final ComfortTypes type;
    private final int seatsNumber;
    private final float thingsWeight;

    public AddWagonCommand(TrainBuilder trainBuilder, String type, String seatsNumber, String thingsWeight) {
        super(trainBuilder);
        this.type = ComfortTypes.valueOf(type);
        this.seatsNumber = Extensions.parseInt(seatsNumber);
        this.thingsWeight = Extensions.parseFloat(thingsWeight);
    }

    public AddWagonCommand(TrainBuilder trainBuilder, ComfortTypes type, String seatsNumber, String thingsWeight) {
        super(trainBuilder);
        this.type = type;
        this.seatsNumber = Extensions.parseInt(seatsNumber);
        this.thingsWeight = Extensions.parseFloat(thingsWeight);
    }

    @Override
    public void execute() {
        trainBuilder.addWagon(type, seatsNumber, thingsWeight);
    }
}
