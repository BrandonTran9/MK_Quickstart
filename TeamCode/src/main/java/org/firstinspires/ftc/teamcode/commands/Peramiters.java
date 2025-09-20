package org.firstinspires.ftc.teamcode.commands;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

public class Peramiters {
    public com.qualcomm.robotcore.hardware.Servo servo0 = null;



    public void sPeramiter () {
        servo0 = hardwareMap.get(com.qualcomm.robotcore.hardware.Servo.class, "servo0");

    }

    public void init () {
        servo0.setPosition(0);
    }

    public void GPP () {
        servo0.setPosition(.25);
    }

    public void PGP () {
        servo0.setPosition(.5);
    }

    public void PPG () {
        servo0.setPosition(1);
    }
}
