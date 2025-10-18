package org.firstinspires.ftc.teamcode.CompV1;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "RedClose")
public class redCloseAuto extends NextFTCOpMode {
    public redCloseAuto() {
        addComponents(
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new SubsystemComponent(Intake.INSTANCE,OutL.INSTANCE,OutR.INSTANCE,
                        RampS.INSTANCE, RampW1.INSTANCE, RampW2.INSTANCE, rampAdj.INSTANCE)
        );
    }
    Pose startPose =  new Pose(56.5, 135, Math.toRadians(0));//look at the pedro path generator for a visual rep
    Pose shootPose = new Pose(60, 93, Math.toRadians(215));
    Pose GPPpose = new Pose(40, 84, Math.toRadians(180));
    Pose GPPposeC = new Pose(56, 83.5);
    Pose GPPf = new Pose(20, 84, Math.toRadians(180));
    Pose PGPpose = new Pose(40, 60, Math.toRadians(180));
    Pose PGPposeC= new Pose(61.75, 62);
    Pose PGPf = new Pose(20, 60, Math.toRadians(180));

    public static Pose autoEndPose = new Pose();
    PathChain StartToShoot;
    PathChain ShootToGPP;
    PathChain PickupGPP;
    PathChain GPPToShoot;
    PathChain ShootToPGP;
    PathChain PickupPGP;
    PathChain PGPToShoot;


    public void buildPaths(){

        StartToShoot = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();

        ShootToGPP = PedroComponent.follower().pathBuilder()
                .addPath(new BezierCurve(shootPose, GPPpose, GPPposeC))
                .setLinearHeadingInterpolation(shootPose.getHeading(), GPPpose.getHeading())
                .build();

        PickupGPP = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(GPPposeC, GPPf))
                .setLinearHeadingInterpolation(GPPposeC.getHeading(), GPPf.getHeading())
                .build();

        GPPToShoot = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(GPPf, shootPose))
                .setLinearHeadingInterpolation(GPPf.getHeading(), shootPose.getHeading())
                .build();

        ShootToPGP = PedroComponent.follower().pathBuilder()
                .addPath(new BezierCurve(shootPose, PGPpose, PGPposeC))
                .setLinearHeadingInterpolation(shootPose.getHeading(), PGPpose.getHeading())
                .build();

        PickupPGP = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(PGPposeC, PGPf))
                .setLinearHeadingInterpolation(PGPposeC.getHeading(), PGPf.getHeading())
                .build();

        PGPToShoot = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(PGPf, shootPose))
                .setLinearHeadingInterpolation(PGPf.getHeading(), shootPose.getHeading())
                .build();


    }
    public Command run() {
         return new SequentialGroup(

                new ParallelGroup(
                new FollowPath(StartToShoot),
                OutR.INSTANCE.Out(),
                OutL.INSTANCE.Out()
                ),
                new SequentialGroup(
                Intake.INSTANCE.In(),
                RampW1.INSTANCE.Go,
                RampW2.INSTANCE.Go,
                RampS.INSTANCE.Go,
                new Delay(5),
                RampW2.INSTANCE.no,
                RampS.INSTANCE.no,
                new FollowPath(ShootToGPP),
                new FollowPath(PickupGPP, true, 0.5),
                new Delay(.25),
                new FollowPath(GPPToShoot)
                ),

                new SequentialGroup(
                RampW2.INSTANCE.Go,
                RampS.INSTANCE.Go,
                new Delay(3),
                RampW2.INSTANCE.no,
                RampS.INSTANCE.no,
                new FollowPath(ShootToPGP),
                new FollowPath(PickupPGP, true, 0.5),
                new Delay(.25),
                new FollowPath(PGPToShoot)
                ),
                new SequentialGroup(
                RampW2.INSTANCE.Go,
                RampS.INSTANCE.Go,
                new Delay(3)
                )
        );
    }
    @Override
    public void onInit(){
        follower().setStartingPose(startPose);
        buildPaths();
        rampAdj.INSTANCE.half.schedule();
    }

    @Override
    public void onWaitForStart(){
    }

    @Override
    public void onStartButtonPressed(){

        run().schedule();
    }

    public void onUpdate(){
        CommandManager.INSTANCE.snapshot();

        telemetry.addData("Commands", CommandManager.INSTANCE.snapshot());
        telemetry.update();

    }

    public void onStop() {
        Intake.INSTANCE.Stop().schedule();
        OutL.INSTANCE.Stop().schedule();
        OutR.INSTANCE.Stop().schedule();
        redCloseAuto.autoEndPose = follower().getPose();

    }


}
