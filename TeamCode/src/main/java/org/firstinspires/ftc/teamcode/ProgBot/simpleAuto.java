package org.firstinspires.ftc.teamcode.ProgBot;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

@Autonomous(name = "simpleAuto")
public class simpleAuto extends OpMode {

    private Follower follower;
    private int pathState;

    Pose startPose = new Pose(8, 8, Math.toRadians(90));
    Pose forwardPose = new Pose(10, 10, Math.toRadians(90));
    public static Pose autoEndPose = new Pose();

    PathChain Forward;

    public void buildPaths() {
        Forward = follower.pathBuilder()
                .addPath(new BezierLine(startPose, forwardPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), forwardPose.getHeading())
                .build();
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(Forward);
                setPathState(1);
                break;
            case 1:

        }

    }
    public void setPathState(int pState) {
        pathState = pState;

    }


    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();

        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        buildPaths();
        follower.setStartingPose(startPose);
    }

    @Override
    public void start() {
        setPathState(0);
    }

    @Override
    public void stop() {
        simpleAuto.autoEndPose = follower.getPose();
    }

}