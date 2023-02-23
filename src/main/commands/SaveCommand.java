package main.commands;

import main.app.TrainBuilder;

public class SaveCommand extends Command{
    public SaveCommand(TrainBuilder trainBuilder) {
        super(trainBuilder);
    }

    @Override
    public void execute() {
        trainBuilder.saveData();
    }
}
