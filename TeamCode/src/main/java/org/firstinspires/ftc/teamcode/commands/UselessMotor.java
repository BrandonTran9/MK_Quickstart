package org.firstinspires.ftc.teamcode.commands;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class UselessMotor implements Subsystem {
    public static final UselessMotor INSTANCE = new UselessMotor();

    private UselessMotor() { }

    private MotorEx motor = new MotorEx("exp0");


    private ControlSystem controlSystem = ControlSystem.builder()
            .velPid(.00025, 0, 0)
            .build();

    public Command spinRight(){
        return new RunToVelocity(controlSystem, 100000).requires(this);
    }


    public Command spinLeft(){
        return new RunToVelocity(controlSystem, -100000).requires(this);
    }


    public Command Stop(){
        return new RunToVelocity(controlSystem, 0).requires(this);
    }


    @Override
    public void periodic() {
        motor.setPower(controlSystem.calculate(motor.getState()));


    }
}
