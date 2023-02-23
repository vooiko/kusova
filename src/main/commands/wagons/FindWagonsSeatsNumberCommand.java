package main.commands.wagons;

import main.app.TrainBuilder;
import main.commands.Command;
import main.extensions.Extensions;

public class FindWagonsSeatsNumberCommand extends Command {
    private final int minValue, maxValue;

    public FindWagonsSeatsNumberCommand(TrainBuilder trainBuilder, String minValue, String maxValue) {
        super(trainBuilder);
        this.minValue = Extensions.parseInt(minValue);
        this.maxValue = Extensions.parseInt(maxValue);
    }

    @Override
    public void execute() {
        trainBuilder.findWagonsBySeatsNumber(minValue, maxValue);
    }
}
