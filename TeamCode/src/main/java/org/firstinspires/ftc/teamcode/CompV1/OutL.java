package org.firstinspires.ftc.teamcode.CompV1;

import com.qualcomm.robotcore.hardware.DcMotor;

import dev.nextftc.control.ControlSystem;
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
            .velPid(.00215, .0000, 0.01)
            .build();

    public Command Out (){
        return new RunToVelocity(controlSystem, -1750, 1500).requires(this);
    }


    public Command Stop (){
        return new RunToVelocity(controlSystem, 0).requires(this);
    }


    @Override
    public void periodic() {
        motor.setPower(controlSystem.calculate(motor.getState()));

    }
}

