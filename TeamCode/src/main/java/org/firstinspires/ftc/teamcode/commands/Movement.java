package org.firstinspires.ftc.teamcode.commands;
import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;

public class Movement {
    private final Pose startPose = new Pose(97, 8, Math.toRadians(90)); // Robot start pose
    private final Pose GPPT = new Pose(121, 35, Math.toRadians(90)); // GPP Obelisk pose/test
    private final Pose PGPT = new Pose(121, 59, Math.toRadians(90)); // PGP Obelisk pose/test
    private final Pose PPGT = new Pose(121, 83, Math.toRadians(90)); // PPG Obelisk pose/test

    public void buildPaths() {
        /* This is our scorePreload path. We are using a BezierLine, which is a straight line. */
        scorePreload = new Path(new BezierLine(startPose, GPPT));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), GPPT.getHeading());

        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierLine(GPPT, PGPT))
                .setLinearHeadingInterpolation(GPPT.getHeading(), PGPT.getHeading())
                .build();
    }
}

