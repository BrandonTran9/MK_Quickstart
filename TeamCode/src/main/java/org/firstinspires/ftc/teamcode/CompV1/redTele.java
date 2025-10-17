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
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.function.Supplier;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@TeleOp(name="redTele", group = "Robot")
public class redTele extends NextFTCOpMode {

    public redTele() {
        addComponents(
                new SubsystemComponent(Intake.INSTANCE,OutL.INSTANCE,OutR.INSTANCE,
                        RampS.INSTANCE, RampW1.INSTANCE, rampAdj.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE

        );
    }

    //public static final Pose ShootP = new Pose(85, 95, Math.toRadians(50)); //put your desired position and heading here

    private Follower follower;
    // public static Pose startingPose; //See MoveTestAuto to understand how to use this
    private boolean automatedDrive;
    private Supplier<PathChain> Shoot;

    private TelemetryManager telemetryM;



    public void onInit() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(redFarAuto.autoEndPose);
        follower.update();
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();

        Shoot = () -> follower.pathBuilder() //Lazy Curve Generation
                .addPath(new Path(new BezierLine(follower::getPose, new Pose(85, 95))))
                .setHeadingInterpolation(HeadingInterpolator.linearFromPoint(follower::getHeading, Math.toRadians(50), 0.8))
                .build();


    }

    @Override
    public void onStartButtonPressed() {

        follower.startTeleopDrive();

        Intake.INSTANCE.In().schedule();
        OutL.INSTANCE.Stop().schedule();
        OutR.INSTANCE.Stop().schedule();
        RampW1.INSTANCE.no.schedule();
        RampW2.INSTANCE.no.schedule();



        button(() -> gamepad2.y)
                .whenBecomesTrue(Intake.INSTANCE.Out())
                        .whenBecomesFalse(Intake.INSTANCE.In());

        button(() -> gamepad2.b)
                .whenBecomesTrue(RampS.INSTANCE.Go)
                .whenBecomesTrue(RampW1.INSTANCE.Go)
                .whenBecomesTrue(RampW2.INSTANCE.Go)
                .whenBecomesFalse(RampS.INSTANCE.no)
                .whenBecomesFalse(RampW1.INSTANCE.no)
                .whenBecomesFalse(RampW2.INSTANCE.no);

        button(() -> gamepad2.a)
                .whenBecomesTrue(OutL.INSTANCE.Out())
                        .whenBecomesTrue(OutR.INSTANCE.Out())
                                .whenBecomesFalse(OutL.INSTANCE.Stop())
                                        .whenBecomesFalse(OutR.INSTANCE.Stop());

        button(() -> gamepad2.dpad_up)
                .whenBecomesTrue(rampAdj.INSTANCE.up);
        button(() -> gamepad2.dpad_left)
                .whenBecomesTrue(rampAdj.INSTANCE.half);
        button(() -> gamepad2.dpad_down)
                .whenBecomesTrue(rampAdj.INSTANCE.flat);

        button(() -> gamepad2.x)
                .whenBecomesTrue(Intake.INSTANCE.Stop())
                .whenBecomesFalse(Intake.INSTANCE.In());



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
            follower.followPath(Shoot.get());
            automatedDrive = true;
        }
        if (gamepad1.touchpadWasReleased()) {
            follower.startTeleopDrive();
            automatedDrive = false;
        }

    }

    public void onStop() {
        BindingManager.reset();
        Intake.INSTANCE.Stop().schedule();
        OutL.INSTANCE.Stop().schedule();
        OutR.INSTANCE.Stop().schedule();
    }
}
