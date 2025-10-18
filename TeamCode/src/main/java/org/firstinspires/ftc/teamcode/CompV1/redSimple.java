package org.firstinspires.ftc.teamcode.CompV1;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;

import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

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

@Autonomous(name = "redSimple")
public class redSimple extends NextFTCOpMode {
    public redSimple() {
        addComponents(
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE,
                new SubsystemComponent(Intake.INSTANCE,OutL.INSTANCE,OutR.INSTANCE,
                        RampS.INSTANCE, RampW1.INSTANCE, RampW2.INSTANCE, rampAdj.INSTANCE)
        );
    }
    Pose startPose =  new Pose(57, 9, Math.toRadians(180)).mirror();//look at the pedro path generator for a visual rep
    Pose shootPose = new Pose(60, 17, Math.toRadians(225));
    Pose PPGpose = new Pose(40, 35, Math.toRadians(180));
    Pose PPGposeC = new Pose(60, 35);
    Pose PPGf = new Pose(20, 35, Math.toRadians(180));
    Pose PGPpose = new Pose(40, 60, Math.toRadians(180));
    Pose PGPposeC= new Pose(57, 52);
    Pose PGPf = new Pose(20, 60, Math.toRadians(180));
    Pose strafeRight = new Pose(57,40, Math.toRadians(180)).mirror();

    public static Pose autoEndPose = new Pose();
    PathChain StartToShoot;
    PathChain ShootToGPP;
    PathChain PickupGPP;
    PathChain GPPToShoot;
    PathChain ShootToPGP;
    PathChain PickupPGP;
    PathChain PGPToShoot;
    PathChain StrafeL;


    public void buildPaths(){

        StartToShoot = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();

        ShootToGPP = PedroComponent.follower().pathBuilder()
                .addPath(new BezierCurve(shootPose, PPGpose, PPGposeC))
                .setLinearHeadingInterpolation(shootPose.getHeading(), PPGpose.getHeading())
                .build();

        PickupGPP = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(PPGposeC, PPGf))
                .setLinearHeadingInterpolation(PPGposeC.getHeading(), PPGf.getHeading())
                .build();

        GPPToShoot = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(PPGf, shootPose))
                .setLinearHeadingInterpolation(PPGf.getHeading(), shootPose.getHeading())
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

        StrafeL = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(startPose, strafeRight))
                .setLinearHeadingInterpolation(startPose.getHeading(), strafeRight.getHeading())
                .build();


    }
    public Command run() {
        return new SequentialGroup(
                new FollowPath(StrafeL)





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

    public void onStop() {
        Intake.INSTANCE.Stop().schedule();
        OutL.INSTANCE.Stop().schedule();
        OutR.INSTANCE.Stop().schedule();
        redSimple.autoEndPose = follower().getPose();

    }


}
