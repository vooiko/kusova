package main.commands;

import main.app.TrainBuilder;

public abstract class Command {
    protected TrainBuilder trainBuilder;

    public Command(TrainBuilder trainBuilder) {
        this.trainBuilder = trainBuilder;
    }

    public abstract void execute();
}
