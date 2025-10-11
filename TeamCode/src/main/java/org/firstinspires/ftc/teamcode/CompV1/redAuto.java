package org.firstinspires.ftc.teamcode.CompV1;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.ProgBot.NextTestV2;
import org.firstinspires.ftc.teamcode.ProgBot.UselessMotor;
import org.firstinspires.ftc.teamcode.ProgBot.UslelessServo;
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

@Autonomous(name = "RedAuto")
public class redAuto extends NextFTCOpMode {
    public redAuto() {
        addComponents(
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
                //new SubsystemComponent(UselessMotor.INSTANCE, UslelessServo.INSTANCE)
        );
    }
    Pose startPose =  new Pose(0, 0, Math.toRadians(0));//look at the pedro path generator for a visual rep
    Pose shootPose = new Pose(8, 9, Math.toRadians(90));
    public static Pose autoEndPose = new Pose();
    PathChain forward;

    public void buildPaths(){

        forward = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();
                //adujst this to your created paths
    }
    public Command run() {
        return new SequentialGroup(
                new FollowPath(forward),
                new Delay(.2),
                UselessMotor.INSTANCE.spinRight()
        );
    }
    @Override
    public void onInit(){
        follower().setStartingPose(startPose);
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
        UselessMotor.INSTANCE.Stop().schedule();
        redAuto.autoEndPose = follower().getPose();

    }


}
