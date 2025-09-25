package org.firstinspires.ftc.teamcode.Autonomus; // make sure this aligns with class location

import static java.lang.Thread.sleep;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.Commands;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.commands.Movement;
import org.firstinspires.ftc.teamcode.commands.UselessMotor;
import org.firstinspires.ftc.teamcode.commands.UslelessServo;
import org.firstinspires.ftc.teamcode.pedroPathing.Cam2;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.ftc.NextFTCOpMode;


@Autonomous(name = "Vision Auto", group = "Robot")
public class VisionAuto extends NextFTCOpMode {

    // DECLARE servoController HERE
    private Commands servoController; // This tells Java that servoController will be an object of type Commands
    private Cam2 cameraSystem;
    private Movement movement;

    private ElapsedTime detectionTimer = new ElapsedTime();
    private static final double DETECTION_TIMEOUT_SECONDS = 1.0;

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;
    public Path scorePreload;
    public Path travelGPP;
    private PathChain grabPickup1;
    public final Pose scorePose = new Pose(60, 85, Math.toRadians(135)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public final Pose pickup1Pose = new Pose(37, 121, Math.toRadians(0)); // Highest (First Set) of Artifacts from the Spike Mark.
    public final Pose startPose = new Pose(97, 8, Math.toRadians(90)); // Robot start pose
    public final Pose GPPM = new Pose(121, 35, Math.toRadians(90)); // GPPM Obelisk pose/test


    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(scorePreload);
                setPathState(1);
                break;
            case 1:
                follower.followPath(travelGPP);


        }
    }
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }


    public void buildPaths() {
        scorePreload = new Path(new BezierLine(startPose, scorePose));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());

        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierLine(scorePose, pickup1Pose))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup1Pose.getHeading())
                .build();

        travelGPP = new Path(new BezierLine(startPose, GPPM));
        travelGPP.setLinearHeadingInterpolation(startPose.getHeading(), GPPM.getHeading());

    }



    public Command autonomousRoutineNoTag() {

        return new SequentialGroup(
                UselessMotor.INSTANCE.spinLeft,
                UselessMotor.INSTANCE.spinRight,

                new ParallelGroup(
                        UselessMotor.INSTANCE.spinLeft,
                        UslelessServo.INSTANCE.half
                ),
                new Delay(1),
                new ParallelGroup(
                        UselessMotor.INSTANCE.spinRight,
                        UslelessServo.INSTANCE.full
                )
        );
    }
    public Command autonomousRoutinePPG() {
        return new SequentialGroup(

        );
    }


    // Enum to represent which of our target AprilTags is currently detected
    enum TargetAprilTag {
        NONE, // No target AprilTag is currently visible
        DGPP, // Corresponds to Obelisk GPP ID 21
        DPGP, // Corresponds to Obelisk PGP ID 22
        DPPG, // Corresponds to Obelisk PPG ID 23



    }
    private TargetAprilTag currentLatchedTarget = TargetAprilTag.NONE;
    private boolean aprilTagDecisionLatched = false; // Flag to indicate if we've made our one-time decision



    // --- YOU MUST CHANGE THESE IDs TO MATCH THE REAL TAGS YOU'RE USING ---
    private static final int GPP = 21;
    private static final int PGP = 22;
    private static final int PPG = 23;



    @Override
    public void onInit() {
        telemetry.addData("Status", "Initializing...");
        telemetry.update();

        servoController = new Commands(hardwareMap); // Pass hardwareMap here
        Commands.init(hardwareMap, telemetry);
        servoController.sCommands();  // Initialize the servo

        servoController.Zero();

        Commands.init(hardwareMap, telemetry);
        cameraSystem = new Cam2(hardwareMap, telemetry);
        currentLatchedTarget = TargetAprilTag.NONE; // Ensure it's reset on init
        aprilTagDecisionLatched = false;

        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);


        // Initialization code (e.g., hardware mapping)
        telemetry.addData("Status", "Initialized");
        telemetry.update();


    }



    @Override
    public void onStartButtonPressed() {
        // This method is called once when the OpMode starts (after INIT, before first loop).
        detectionTimer.reset();          // Start the timeout timer for AprilTag detection
        aprilTagDecisionLatched = false; // Ensure detection logic runs by resetting the main latch
        currentLatchedTarget = TargetAprilTag.NONE; // Reset just in case
        opmodeTimer.resetTimer();
        setPathState(0);
    }






    @Override
    public void onUpdate() {

        // These loop the movements of the robot, these must be called continuously in order to work
        //follower.update();
        //autonomousPathUpdate();

        if (!aprilTagDecisionLatched) {
            if (cameraSystem != null) {
                cameraSystem.updateDetections();

                TargetAprilTag detectedThisPass = TargetAprilTag.NONE;
                AprilTagDetection foundTag;

                foundTag = cameraSystem.getDetectionById(GPP);
                if (foundTag != null) {
                    detectedThisPass = TargetAprilTag.DGPP;
                }
                else {
                    foundTag = cameraSystem.getDetectionById(PGP);
                    if (foundTag != null) {
                        detectedThisPass = TargetAprilTag.DPGP;
                    }
                    else {
                        foundTag = cameraSystem.getDetectionById(PPG);
                        if (foundTag != null) {
                            detectedThisPass = TargetAprilTag.DPPG;
                        }
                        // Add checks for ID_FOR_LOCATION_4 and ID_FOR_LOCATION_5 if you use them
                    }
                }

                // If we haven't already latched due to camera failure, proceed with tag/timeout check
                if (!aprilTagDecisionLatched) { // This check might be redundant if camera failure sets it
                    boolean decisionMadeThisPass = false; // Temporary flag for this loop iteration

                    if (detectedThisPass != TargetAprilTag.NONE) {
                        currentLatchedTarget = detectedThisPass;
                        decisionMadeThisPass = true;
                        telemetry.addData("Tag Detected", currentLatchedTarget.toString());
                    } else if (detectionTimer.seconds() >= DETECTION_TIMEOUT_SECONDS) {
                        currentLatchedTarget = TargetAprilTag.NONE; // Default to NONE on timeout
                        decisionMadeThisPass = true;
                        telemetry.addData("Detection Timeout", "Defaulting to NONE");
                    }






                    if (decisionMadeThisPass) {
                        aprilTagDecisionLatched = true; // <<<< THIS IS THE KEY ADDITION / CHANGE
                        // Now the outer 'if' condition will fail on next loop

                        // Command the servo ONCE based on the decision
                        if (currentLatchedTarget == TargetAprilTag.DGPP) {
                            servoController.GPPT();



                        } else if (currentLatchedTarget == TargetAprilTag.DPGP) {
                            servoController.PGPT();



                        } else if (currentLatchedTarget == TargetAprilTag.DPPG) {
                            servoController.PPGT();




                        } else if (currentLatchedTarget == TargetAprilTag.NONE) {
                            servoController.Zero();
                            follower.update();
                            autonomousPathUpdate();
                            autonomousRoutineNoTag().schedule();


                            }
                    }






                }



                    }
                }
            }
        }


/*
        // Feedback to Driver Hub for debugging
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
*/


