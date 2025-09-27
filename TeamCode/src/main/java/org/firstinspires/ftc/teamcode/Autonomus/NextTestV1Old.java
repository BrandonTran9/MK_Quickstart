package org.firstinspires.ftc.teamcode.Autonomus;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.UselessMotor;
import org.firstinspires.ftc.teamcode.commands.UslelessServo;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "NextFTCAuto")
public class NextTestV1Old extends NextFTCOpMode {
    public NextTestV1Old() {
        addComponents(
                new SubsystemComponent(UselessMotor.INSTANCE, UslelessServo.INSTANCE),
                BulkReadComponent.INSTANCE,
                new PedroComponent(Constants::createFollower)
        );
    }

    private final Pose startPose = new Pose(8, 8, Math.toRadians(90));
    private final Pose forward = new Pose(32, 8, Math.toRadians(90));

    private PathChain Forward;
    private Follower follower; // Pedro Pathing follower


    public void buildPaths () {
        Forward = follower.pathBuilder()
                .addPath(new BezierLine(startPose, forward))
                .setLinearHeadingInterpolation(startPose.getHeading(), forward.getHeading())
                .build();
    }

    public Command secondRoutine() {
        return new SequentialGroup(
                new ParallelGroup(
                        new FollowPath(Forward),
                        UselessMotor.INSTANCE.spinLeft
                ),
                new ParallelGroup(
                        UselessMotor.INSTANCE.spinRight,
                        UslelessServo.INSTANCE.full
                ),
                new Delay(1),
                UselessMotor.INSTANCE.spinLeft,
                UslelessServo.INSTANCE.half
        );
    }

    @Override
    public void onInit() {
    }

    @Override
    public void onStartButtonPressed() {
        secondRoutine().schedule();
    }

}
