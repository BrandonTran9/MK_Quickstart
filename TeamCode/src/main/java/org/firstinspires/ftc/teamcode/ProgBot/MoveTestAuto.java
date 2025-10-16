package org.firstinspires.ftc.teamcode.ProgBot;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Disabled
@Autonomous(name = "MoveAuto", group = "Robot")
public class MoveTestAuto extends OpMode {

private Follower follower;
private  Timer pathTimer, actionTimer, opmodeTimer;
private int pathState;


   // private final Pose startPose = new Pose(28.5, 128, Math.toRadians(180)); // Start Pose of our robot.
    public final Pose scorePose = new Pose(60, 85, Math.toRadians(135)); // Scoring Pose of our robot. It is facing the goal at a 135 degree angle.
    public final Pose pickup1Pose = new Pose(37, 121, Math.toRadians(0)); // Highest (First Set) of Artifacts from the Spike Mark.
    public final Pose startPose = new Pose(8, 8, Math.toRadians(90)); // Robot start pose
    public final Pose GPPM = new Pose(121, 35, Math.toRadians(90)); // GPPM Obelisk pose/test
    public final Pose Curve = new Pose(31, 31, Math.toRadians(90));
    public final Pose CurveP = new Pose(40,5, Math.toRadians(90));





    public Path scorePreload;
    public Path travelGPP;
    public Path CurveT;


private PathChain grabPickup1, scorePickup1, grabPickup2, scorePickup2, grabPickup3, scorePickup3;

    public void buildPaths() {
    scorePreload = new Path(new BezierLine(startPose, scorePose));
    scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());

    grabPickup1 = follower.pathBuilder()
            .addPath(new BezierLine(scorePose, pickup1Pose))
            .setLinearHeadingInterpolation(scorePose.getHeading(), pickup1Pose.getHeading())
            .build();

    travelGPP = new Path(new BezierLine(startPose, GPPM));
    travelGPP.setLinearHeadingInterpolation(startPose.getHeading(), GPPM.getHeading());

    CurveT = new Path(new BezierCurve(startPose, CurveP, Curve));
    CurveT.setLinearHeadingInterpolation(startPose.getHeading(),Curve.getHeading());


    }
    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(CurveT);
                setPathState(1);
                break;
            case 1:




        }
    }

    /** These change the states of the paths and actions. It will also reset the timers of the individual switches **/
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void loop() {
        // These loop the movements of the robot, these must be called continuously in order to work
        follower.update();
        autonomousPathUpdate();
        // Feedback to Driver Hub for debugging
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }
    /** This method is called once at the init of the OpMode. **/
    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);
    }
    /** This method is called continuously after Init while waiting for "play". **/
    @Override
    public void init_loop() {}
    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }
    /** We do not use this because everything should automatically disable **/
    @Override
    public void stop() {}



}
