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
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;

@Autonomous(name = "NextPedro")
public class NextTestV2 extends NextFTCOpMode {

    public NextTestV2() {
        addComponents(
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new SubsystemComponent(UselessMotor.INSTANCE),
                new SubsystemComponent(UslelessServo.INSTANCE)


        );
    }
    Pose startPose =  new Pose(8, 8, Math.toRadians(90));
    Pose shootPose = new Pose(8, 31, Math.toRadians(90));
    public static Pose autoEndPose = new Pose();
    PathChain forward;
    private Follower follower;

    public void buildPaths(){
        forward = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();
    }
    public Command run() {
        return new SequentialGroup(
                new FollowPath(forward),
                new Delay(.2),
                UselessMotor.INSTANCE.spinRight
        );
    }

    @Override
    public void onInit(){
        follower().setStartingPose(startPose);
        UselessMotor.INSTANCE.spinLeft.schedule(); // I would prob put this in onStartbuttonPressed because you dont want it running in Init
        buildPaths();
    }

    @Override
    public void onWaitForStart(){
        //scan obelisk if necessary
    }

    @Override
    public void onStartButtonPressed(){
        run().schedule();
    }

    public void onStop() {
        follower.update();
        NextTestV2.autoEndPose = follower.getPose();
    }
}

