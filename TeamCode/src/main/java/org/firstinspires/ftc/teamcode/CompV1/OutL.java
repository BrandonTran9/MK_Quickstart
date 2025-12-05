package org.firstinspires.ftc.teamcode.CompV1;

import com.qualcomm.robotcore.hardware.DcMotor;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class OutL implements Subsystem {
    public static final OutL INSTANCE = new OutL();
    public OutL() {

    }

    private MotorEx motor = new MotorEx("outL");



    private ControlSystem controlSystem = ControlSystem.builder()
            .basicFF(.0004, 0, 0.0075)
            .build();

    public Command Out (){
        return new RunToVelocity(controlSystem, -1400, 150).requires(this);
    }


    public Command Stop (){
        return new RunToVelocity(controlSystem, 0).requires(this);
    }


    @Override
    public void periodic() {
        motor.setPower(controlSystem.calculate(motor.getState()));

    }
    public static double getVelocity() {
        return INSTANCE.motor.getVelocity();
    }
}

