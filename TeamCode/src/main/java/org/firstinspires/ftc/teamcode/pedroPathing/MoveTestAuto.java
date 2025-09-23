package org.firstinspires.ftc.teamcode.pedroPathing;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.commands.Movement;


@Autonomous(name = "MoveAuto", group = "Robot")
public class MoveTestAuto extends OpMode {

    private Movement movement;


    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();



        follower = Constants.createFollower(hardwareMap);
        createPoses();
        buildPaths();
        follower.setStartingPose(startPose);
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(travelGPP());
                setPathState(1);
                break;
            case 1:
                movement.travelGPP();
        }
    }
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }



    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();
    }
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private int pathState;







}
