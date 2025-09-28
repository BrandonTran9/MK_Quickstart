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
            .velPid(.0009)
            .build();

    public Command spinLeft = new RunToVelocity(controlSystem, -100000).requires(this);
    public Command spinRight = new RunToVelocity(controlSystem, 100000).requires(this);
    public Command Stop(){
        return new RunToVelocity(controlSystem, 0);
    }
    @Override
    public void periodic() {
        motor.setPower(controlSystem.calculate(motor.getState()));


    }
}
