package org.firstinspires.ftc.teamcode.commands;

// Remove: import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Commands {
    public Servo servo0 = null; // Changed to Servo for clarity
    private HardwareMap localHardwareMap; // Store HardwareMap locally

    // Constructor to receive HardwareMap
    public Commands(HardwareMap hardwareMap) {
        this.localHardwareMap = hardwareMap;
        // Initialize servo here directly if it's always needed
        // or ensure sCommands is called after construction.
    }

    public static void init(HardwareMap hardwareMap, Telemetry telemetry) {

    }


    // Method to initialize the specific servo for this instance
    public void sCommands() {
        if (this.localHardwareMap != null) {
            servo0 = this.localHardwareMap.get(Servo.class, "uselessServo");
        } else {
            // Handle error: hardwareMap was not provided
            // You might throw an exception or log an error to telemetry
            // For now, let's assume telemetry is available via a static init or passed differently
            // if (Telemetry != null) Telemetry.addData("Error", "HardwareMap not initialized in Commands");
        }
    }

    public void Zero() {
        if (servo0 != null) {
            servo0.setPosition(0);
        }
    }

    public void GPPT() {
        if (servo0 != null) {
            servo0.setPosition(0.25);
        }
    }

    public void PGPT() {
        if (servo0 != null) {
            servo0.setPosition(0.5);
        }
    }

    public void PPGT() {
        if (servo0 != null) {
            servo0.setPosition(1);
        }
    }
}