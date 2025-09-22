package org.firstinspires.ftc.teamcode.pedroPathing; // make sure this aligns with class location

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.commands.Peramiters;

import com.pedropathing.util.Timer;

import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous(name = "Example Auto", group = "Examples")
public class TestAuto extends OpMode {

    // DECLARE servoController HERE
    private Peramiters servoController; // This tells Java that servoController will be an object of type Peramiters
    private Cam2 cameraSystem;

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;

    // Enum to represent which of our target AprilTags is currently detected
    enum TargetAprilTag {
        NONE,           // No target AprilTag is currently visible
        DGPP, // e.g., Corresponds to Obelisk GPP ID 21
        DPGP, // e.g., Corresponds to Obelisk PGP ID 22
        DPPG, // e.g., Corresponds to Obelisk PPG ID 23
    }
    private TargetAprilTag currentLatchedTarget = TargetAprilTag.NONE;
    private boolean aprilTagDecisionLatched = false; // Flag to indicate if we've made our one-time decision


    // --- YOU MUST CHANGE THESE IDs TO MATCH THE REAL TAGS YOU'RE USING ---
    private static final int GPP = 21;// Example
    private static final int PGP = 22;  // Example
    private static final int PPG = 23;  // Example





    @Override
    public void loop() {

        // These loop the movements of the robot, these must be called continuously in order to work
        follower.update();
        //autonomousPathUpdate();

        if (!aprilTagDecisionLatched) {
            if (cameraSystem != null) {
                cameraSystem.updateDetections();
                TargetAprilTag detectedThisPass = TargetAprilTag.NONE;
                AprilTagDetection foundTag;

                foundTag = cameraSystem.getDetectionById(GPP);
                if (foundTag != null) {
                    detectedThisPass = TargetAprilTag.DGPP;
                } else {
                    foundTag = cameraSystem.getDetectionById(PGP);
                    if (foundTag != null) {
                        detectedThisPass = TargetAprilTag.DPGP;
                    } else {
                        foundTag = cameraSystem.getDetectionById(PPG);
                        if (foundTag != null) {
                            detectedThisPass = TargetAprilTag.DPPG;
                        }
                        // Add checks for ID_FOR_LOCATION_4 and ID_FOR_LOCATION_5 if you use them
                    }
                }
                if (detectedThisPass == TargetAprilTag.DGPP);{
                    servoController.GPPT();
                }
                if (detectedThisPass == TargetAprilTag.DPGP);{
                    servoController.PGPT();
                }
                if (detectedThisPass == TargetAprilTag.DPPG);{
                    servoController.PPGT();
                }

            }
        }


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

        Peramiters.init(hardwareMap, telemetry);
        cameraSystem = new Cam2(hardwareMap, telemetry);
        currentLatchedTarget = TargetAprilTag.NONE; // Ensure it's reset on init
        aprilTagDecisionLatched = false;

        // Initialization code (e.g., hardware mapping)
        telemetry.addData("Status", "Initialized");
        telemetry.update();






    }


    @Override
    public void stop() {
        // Optional: Cleanup code
        if (cameraSystem != null) {
            cameraSystem.close();
        }
        telemetry.addData("Status", "Stopped");
        telemetry.update();
    }
}