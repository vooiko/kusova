package main.commands.trains;

import main.app.TrainBuilder;
import main.app.TransportApp;
import main.commands.Command;
import main.extensions.Extensions;
import main.model.Train;

public class CreateTrainCommand extends Command {
    private final String name;
    private final String code;
    private final int expectedSitsNumber;
    private float[] coefficients;

    public CreateTrainCommand(TrainBuilder trainBuilder, String name, String code, String expectedSitsNumber, String ordinaryCoeff, String businessCoeff, String vipCoeff) {
        super(trainBuilder);
        this.name = name;
        this.code = code;
        this.expectedSitsNumber = Extensions.parseInt(expectedSitsNumber);

        this.coefficients = new float[3];
        this.coefficients[0] = Extensions.parseFloat(ordinaryCoeff);
        this.coefficients[1] = Extensions.parseFloat(businessCoeff);
        this.coefficients[2] = Extensions.parseFloat(vipCoeff);

        if (this.coefficients[0] + this.coefficients[1] + this.coefficients[2] != 1.0F){
            this.coefficients = null;
        }
    }

    @Override
    public void execute() {
        if (coefficients != null) {
            trainBuilder.createTrain(new Train(name, code), expectedSitsNumber, coefficients);
        } else {
            TransportApp.logger.info("Error while 'CreateTrain'! Entered coefficients was bigger or smaller than 1.0");
        }
    }
}
