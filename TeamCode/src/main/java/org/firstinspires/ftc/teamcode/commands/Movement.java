package org.firstinspires.ftc.teamcode.commands;
import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;

public class Movement {
    public final Pose startPose = new Pose(97, 8, Math.toRadians(90)); // Robot start pose
    private final Pose GPPM = new Pose(121, 35, Math.toRadians(90)); // GPPM Obelisk pose/test


    public void GPPM() {
        final Pose GPPM = new Pose(121, 35, Math.toRadians(90)); // GPPM Obelisk pose/test
    }
    public void GPPG() {
        final Pose PGPM = new Pose(121, 59, Math.toRadians(90)); // PGPM Obelisk pose/test
    }
    public void PPGM() {
        final Pose PPGM = new Pose(121, 83, Math.toRadians(90)); // PPGM Obelisk pose/test
    }
    public void startPose() {
        final Pose startPose = new Pose(97, 8, Math.toRadians(90)); // Robot start pose
    }

    public void buildPaths() {
        Path travelGPP = new Path(new BezierLine(startPose, GPPM));
        travelGPP.setLinearHeadingInterpolation(startPose.getHeading(), GPPM.getHeading());

    }


    public void travelGPP() {
        Path travelGPP = new Path(new BezierLine(startPose, GPPM));
        travelGPP.setLinearHeadingInterpolation(startPose.getHeading(), GPPM.getHeading());

    }
}


