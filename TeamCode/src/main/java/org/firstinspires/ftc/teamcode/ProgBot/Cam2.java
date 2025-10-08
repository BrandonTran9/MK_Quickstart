package org.firstinspires.ftc.teamcode.ProgBot;

import android.util.Size; // For setCameraResolution
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry; // Re-added for minimal feedback
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagGameDatabase;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.ArrayList;
import java.util.List;

public class Cam2 {

    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    private Telemetry telemetry; // Store Telemetry to use it in updateDetections

    private List<AprilTagDetection> currentDetections = new ArrayList<>();

    public Cam2(HardwareMap hardwareMap, Telemetry telemetry) { // Added Telemetry back as a parameter
        this.telemetry = telemetry; // Store it

        aprilTag = new AprilTagProcessor.Builder()
                .setTagLibrary(AprilTagGameDatabase.getDecodeTagLibrary()) // For official FTC tags

                .setDrawAxes(true)
                .setDrawCubeProjection(true)
                .setDrawTagOutline(true)
                .setDrawTagID(true)
                .setOutputUnits(DistanceUnit.INCH, AngleUnit.DEGREES)
                // ------------------------------------------
                // Optional: Lens intrinsics for accurate 3D pose (VERY IMPORTANT for ftcPose accuracy)
                // .setLensIntrinsics(fx, fy, cx, cy)
                // Optional: Output units for ftcPose (Pose of the tag relative to the camera)
                .build();

        VisionPortal.Builder builder = new VisionPortal.Builder();
        builder.setCamera(hardwareMap.get(WebcamName.class, "Webcam 1")); // Use your webcam's configured name
        builder.setCameraResolution(new Size(640, 480)); // Common resolution, adjust if needed
        builder.enableLiveView(true); // Enable camera preview on RC phone or Control Hub
        builder.setStreamFormat(VisionPortal.StreamFormat.MJPEG); // MJPEG often uses less bandwidth
        builder.setAutoStopLiveView(false); // Keep LiveView running even if no processors are active (good for setup)
        builder.addProcessor(aprilTag);

        visionPortal = builder.build();
    }

    public void updateDetections() {
        if (aprilTag != null) {
            currentDetections = aprilTag.getDetections();

            // Minimal telemetry for confirmation
            if (telemetry != null) {
                telemetry.addData("[Cam2] # AprilTags Detected", currentDetections.size());
                for (AprilTagDetection detection : currentDetections) {
                    if (detection.metadata != null) {
                        telemetry.addLine(String.format("[Cam2] ID %d (%s)", detection.id, detection.metadata.name));
                    } else {
                        telemetry.addLine(String.format("[Cam2] ID %d (Unknown)", detection.id));
                    }
                    // You could add ftcPose.x, y, z here if you want that telemetry too
                }
            }
        }
    }

    public List<AprilTagDetection> getLatestDetections() {
        return currentDetections;
    }

    public AprilTagDetection getDetectionById(int targetId) {
        for (AprilTagDetection detection : currentDetections) {
            if (detection.id == targetId) {
                return detection;
            }
        }
        return null;
    }

    public void close() {
        if (visionPortal != null) {
            visionPortal.close();
            visionPortal = null; // Help with garbage collection
        }
        aprilTag = null; // Help with garbage collection
    }

    public void setProcessorEnabled(boolean enabled) {
        if (visionPortal != null && aprilTag != null) {
            visionPortal.setProcessorEnabled(aprilTag, enabled);
        }
    }
}
