package org.firstinspires.ftc.teamcode.TeleOp;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Autonomus.simpleAuto;
import org.firstinspires.ftc.teamcode.commands.UselessMotor;
import org.firstinspires.ftc.teamcode.commands.UslelessServo;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import com.pedropathing.follower.Follower;

import java.util.function.Supplier;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.extensions.pedro.PedroDriverControlled;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.DriverControlledCommand;
import static org.firstinspires.ftc.teamcode.Autonomus.NextTestV2.autoEndPose;


@TeleOp(name="NextTeleOp", group = "Concept")
public class NextTeleOp extends NextFTCOpMode {

    public NextTeleOp() {
        addComponents(
                new SubsystemComponent(UselessMotor.INSTANCE, UslelessServo.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new PedroComponent(Constants::createFollower)

                );
    }



       public static final Pose shootPoseF = new Pose(8, 10, Math.toRadians(130));


    private Follower follower;
    private Supplier<PathChain> pathChain;
    PathChain ShootF;

    public void buildPaths() {
   pathChain = () -> follower.pathBuilder()
           .addPath(new Path(new BezierLine(follower :: getPose, shootPoseF)))
           .build();
    }




    public void onInit() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(simpleAuto.autoEndPose);
        follower.update();

    }

    public void onStartButtonPressed() {
        DriverControlledCommand driverControlled = new PedroDriverControlled(
                Gamepads.gamepad1().leftStickY(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX()
        );

       driverControlled.schedule();



        Gamepads.gamepad1().triangle()
                .whenBecomesTrue(UselessMotor.INSTANCE.spinLeft())
                        .whenBecomesFalse(UselessMotor.INSTANCE.Stop());




    }

    public void onUpdate() {

    }

    public void onStop() {
        UselessMotor.INSTANCE.Stop().schedule();
    }


}

