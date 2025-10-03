package org.firstinspires.ftc.teamcode.TeleOp;
import static org.firstinspires.ftc.teamcode.Autonomus.NextTestV2.autoEndPose;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Autonomus.NextTestV2;
import org.firstinspires.ftc.teamcode.Autonomus.simpleAuto;
import org.firstinspires.ftc.teamcode.commands.UselessMotor;
import org.firstinspires.ftc.teamcode.commands.UslelessServo;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.function.Supplier;

import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;


@Configurable
@TeleOp
public class Example_TeleOp extends NextFTCOpMode {


    public Example_TeleOp() {
        addComponents(
                new SubsystemComponent(UselessMotor.INSTANCE, UslelessServo.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE

        );
    }
    private Follower follower;
    public static Pose startingPose; //See MoveTestAuto to understand how to use this
    private boolean automatedDrive;
    private Supplier<PathChain> uhh;
    private Supplier<PathChain> uhh2;
    private TelemetryManager telemetryM;





    @Override
    public void onInit() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(NextTestV2.autoEndPose);
        follower.update();
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();

        uhh = () -> follower.pathBuilder() //Lazy Curve Generation
                .addPath(new Path(new BezierLine(follower::getPose, new Pose(0, 0))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, Math.toRadians(0), 0.8))
                .build();
        uhh2 = () -> follower.pathBuilder() //Lazy Curve Generation
                .addPath(new Path(new BezierLine(follower::getPose, new Pose(10, 10))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, Math.toRadians(90), 0.8))
                .build();
    }



    @Override
    public void onStartButtonPressed() {
        //The parameter controls whether the Follower should use break mode on the motors (using it is recommended).
        //In order to use float mode, add .useBrakeModeInTeleOp(true); to your Drivetrain Constants in Constant.java (for Mecanum)
        //If you don't pass anything in, it uses the default (false)
        follower.startTeleopDrive();
    }


    @Override
    public void onUpdate() {
        //Call this once per loop
        follower.update();
        telemetryM.update();

        if (!automatedDrive) {
            //Make the last parameter false for field-centric
            //In case the drivers want to use a "slowMode" you can scale the vectors

            //This is the normal version to use in the TeleOp
            follower.setTeleOpDrive(
                    -gamepad1.left_stick_y,
                    -gamepad1.left_stick_x,
                    -gamepad1.right_stick_x,
                    false // F= Feild Centric   T= Robot Centric

            );
        }

        //Automated PathFollowing
        if (gamepad1.touchpadWasPressed()) {
            follower.followPath(uhh.get());

            automatedDrive = true;
        }

        if (gamepad1.leftBumperWasPressed()) {
            follower.followPath(uhh2.get());
            automatedDrive = true;
        }

        if (gamepad1.touchpadWasReleased()) {
            follower.startTeleopDrive();
            automatedDrive = false;
        }

        if (gamepad1.leftBumperWasReleased()) {
            follower.startTeleopDrive();
            automatedDrive = false;
        }









        telemetryM.debug("position", follower.getPose());
        telemetryM.debug("velocity", follower.getVelocity());
        telemetryM.debug("automatedDrive", automatedDrive);



    }

    public void onStop() {

    }
}