package org.firstinspires.ftc.teamcode.CompV1;

import static dev.nextftc.bindings.Bindings.button;

import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.ProgBot.NextTestV2;
import org.firstinspires.ftc.teamcode.ProgBot.UselessMotor;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.function.Supplier;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp(name="redTele", group = "Robot")
public class redTele extends NextFTCOpMode {

    public redTele() {
        addComponents(
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE

        );
    }

    public static final Pose PoseA = new Pose(0, 0, Math.toRadians(0)); //put your desired position and heading here

    private Follower follower;
    // public static Pose startingPose; //See MoveTestAuto to understand how to use this
    private boolean automatedDrive;
    private Supplier<PathChain> Pose1;

    private TelemetryManager telemetryM;



    public void onInit() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(NextTestV2.autoEndPose);
        follower.update();
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();

        Pose1 = () -> follower.pathBuilder() //Lazy Curve Generation
                .addPath(new Path(new BezierLine(follower::getPose, new Pose(0, 0))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, Math.toRadians(90), 0.8))
                .build();


    }

    @Override
    public void onStartButtonPressed() {

        follower.startTeleopDrive();


        button(() -> gamepad1.triangle)
                .whenBecomesFalse(UselessMotor.INSTANCE.spinLeft())
                .whenBecomesTrue(UselessMotor.INSTANCE.Stop());

    }

    public void onUpdate() {
        //Call this once per loop
        follower.update();
        telemetryM.update();
        BindingManager.update();

        if (!automatedDrive) {
            //Make the last parameter false for field-centric

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
            follower.followPath(Pose1.get());
            automatedDrive = true;
        }
        if (gamepad1.touchpadWasReleased()) {
            follower.startTeleopDrive();
            automatedDrive = false;
        }

    }

    public void onStop() {
        BindingManager.reset();
        UselessMotor.INSTANCE.Stop().schedule();
    }
}
