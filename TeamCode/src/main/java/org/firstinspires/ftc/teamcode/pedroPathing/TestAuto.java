package org.firstinspires.ftc.teamcode.pedroPathing; // make sure this aligns with class location

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.commands.Peramiters;

@Autonomous(name = "Example Auto", group = "Examples")
public class TestAuto extends OpMode {

    // DECLARE servoController HERE
    private Peramiters servoController; // This tells Java that servoController will be an object of type Peramiters
    private Cam2 cameraSystem;

    @Override
    public void init() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        servoController = new Peramiters(hardwareMap); // Pass hardwareMap here
        Peramiters.init(hardwareMap, telemetry);
        servoController.sPeramiter();  // Initialize the servo

        servoController.Zero();

        // Initialization code (e.g., hardware mapping)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        Peramiters.init(hardwareMap, telemetry);
        Cam2.init(hardwareMap, telemetry);
    }

    @Override
    public void loop() {

    }


    @Override
    public void stop() {
        // Optional: Cleanup code
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }
}