package org.firstinspires.ftc.teamcode.CompV1;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.extensions.pedro.FollowPath;

@Autonomous(name = "bluePedroPathingV2")
public class bluePedroPathingV2 extends OpMode {
    public Follower follower;

//    public static PedroComponent createFollower(HardwareMap hardwareMap) {
//        return null;
//    }

    public Timer pathTimer, opmodeTimer;

    private int pathState;

    Pose startPose =  new Pose(57, 9, Math.toRadians(180)).mirror();//look at the pedro path generator for a visual rep
    Pose shootPose = new Pose(60, 17, Math.toRadians(225));
    Pose PPGpose = new Pose(40, 35, Math.toRadians(180));
    Pose PPGposeC = new Pose(60, 35);
    Pose PPGf = new Pose(20, 35, Math.toRadians(180));
    Pose PGPpose = new Pose(40, 60, Math.toRadians(180));
    Pose PGPposeC= new Pose(57, 52);
    Pose PGPf = new Pose(20, 60, Math.toRadians(180));
    Pose strafeRight = new Pose(57,40, Math.toRadians(180)).mirror();

    public static Pose autoEndPose = new Pose();

    PathChain StartToShoot, ShootToGPP, PickupGPP, GPPToShoot, ShootToPGP, PickupPGP, PGPToShoot, StrafeL;


    public void buildPaths(){

        StartToShoot = follower().pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();

        ShootToGPP = follower().pathBuilder()
                .addPath(new BezierCurve(shootPose, PPGpose, PPGposeC))
                .setLinearHeadingInterpolation(shootPose.getHeading(), PPGpose.getHeading())
                .build();

        PickupGPP = follower().pathBuilder()
                .addPath(new BezierLine(PPGposeC, PPGf))
                .setLinearHeadingInterpolation(PPGposeC.getHeading(), PPGf.getHeading())
                .build();

        GPPToShoot = follower().pathBuilder()
                .addPath(new BezierLine(PPGf, shootPose))
                .setLinearHeadingInterpolation(PPGf.getHeading(), shootPose.getHeading())
                .build();

        ShootToPGP = follower().pathBuilder()
                .addPath(new BezierCurve(shootPose, PGPpose, PGPposeC))
                .setLinearHeadingInterpolation(shootPose.getHeading(), PGPpose.getHeading())
                .build();

        PickupPGP = follower().pathBuilder()
                .addPath(new BezierLine(PGPposeC, PGPf))
                .setLinearHeadingInterpolation(PGPposeC.getHeading(), PGPf.getHeading())
                .build();

        PGPToShoot = follower().pathBuilder()
                .addPath(new BezierLine(PGPf, shootPose))
                .setLinearHeadingInterpolation(PGPf.getHeading(), shootPose.getHeading())
                .build();

        StrafeL = follower().pathBuilder()
                .addPath(new BezierLine(startPose, strafeRight))
                .setLinearHeadingInterpolation(startPose.getHeading(), strafeRight.getHeading())
                .build();


    }

    public Command run() {
        return new SequentialGroup(
                new FollowPath(StrafeL)
        );
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower().followPath(StartToShoot);
                setPathState(1);
                break;
            case 1:

            /* You could check for
            - Follower State: "if(!follower.isBusy()) {}"
            - Time: "if(pathTimer.getElapsedTimeSeconds() > 1) {}"
            - Robot Position: "if(follower.getPose().getX() > 36) {}"
            */

                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower().isBusy()) {
                    /* Score Preload */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower().followPath(ShootToGPP,true);
                    setPathState(2);
                }
                break;
            case 2:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup1Pose's position */
                if(!follower().isBusy()) {
                    /* Grab Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower().followPath(PickupGPP,true);
                    setPathState(3);
                }
                break;
            case 3:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower().isBusy()) {
                    /* Score Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower().followPath(GPPToShoot,true);
                    setPathState(4);
                }
                break;
            case 4:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup2Pose's position */
                if(!follower().isBusy()) {
                    /* Grab Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower().followPath(ShootToPGP,true);
                    setPathState(5);
                }
                break;
            case 5:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower().isBusy()) {
                    /* Score Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                    follower().followPath(PickupPGP,true);
                    setPathState(6);
                }
                break;
            case 6:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup3Pose's position */
                if(!follower().isBusy()) {
                    /* Grab Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower().followPath(PGPToShoot, true);
                    setPathState(7);
                }
                break;
            case 7:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the pickup3Pose's position */
                if(!follower().isBusy()) {
                    /* Grab Sample */

                    /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                    follower().followPath(StrafeL, true);
                    setPathState(8);
                }
                break;
            case 8:
                /* This case checks the robot's position and will wait until the robot position is close (1 inch away) from the scorePose's position */
                if(!follower().isBusy()) {
                    /* Set the state to a Case we won't use or define, so it just stops running an new paths */
                    setPathState(-1);
                }
                break;
        }
    }

    /** These change the states of the paths and actions. It will also reset the timers of the individual switches **/
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        follower = Constants.createFollower(hardwareMap);
        buildPaths();

        follower().setStartingPose(startPose);
        buildPaths();
        rampAdj.INSTANCE.half.schedule();
    }

    @Override
    public void loop() {
        // These loop the movements of the robot, these must be called continuously in order to work
        follower().update();
        autonomousPathUpdate();
        // Feedback to Driver Hub for debugging
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower().getPose().getX());
        telemetry.addData("y", follower().getPose().getY());
        telemetry.addData("heading", follower().getPose().getHeading());
        telemetry.update();
    }

    /** This method is called continuously after Init while waiting for "play". **/
    @Override
    public void init_loop() {

    }
    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    public void onStop() {
        Intake.INSTANCE.Stop().schedule();
        OutL.INSTANCE.Stop().schedule();
        OutR.INSTANCE.Stop().schedule();
        blueSimple.autoEndPose = follower().getPose();
    }
}