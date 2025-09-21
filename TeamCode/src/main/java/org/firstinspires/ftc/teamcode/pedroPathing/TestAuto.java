package org.firstinspires.ftc.teamcode.pedroPathing; // make sure this aligns with class location

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.commands.Peramiters;
import org.firstinspires.ftc.teamcode.commands.Movement;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;

@Autonomous(name = "Example Auto", group = "Examples")
public class TestAuto extends OpMode {

    // DECLARE servoController HERE
    private Peramiters servoController; // This tells Java that servoController will be an object of type Peramiters
    private Cam2 cameraSystem;

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;




    @Override
    public void loop() {

        // These loop the movements of the robot, these must be called continuously in order to work
        follower.update();
        //autonomousPathUpdate();
        // Feedback to Driver Hub for debugging
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }



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
    public void stop() {
        // Optional: Cleanup code
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }
}