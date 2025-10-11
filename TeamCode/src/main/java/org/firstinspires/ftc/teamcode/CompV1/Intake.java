package org.firstinspires.ftc.teamcode.CompV1;


import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class Intake implements Subsystem {
    public static final Intake INSTANCE = new Intake();
    private Intake() { }

    private MotorEx motor = new MotorEx("intake");


    private ControlSystem controlSystem = ControlSystem.builder()
            .velPid(.00025, 0, 0)
            .build();

    public Command In (){
        return new RunToVelocity(controlSystem, 100000).requires(this);
    }


    public Command Out (){
        return new RunToVelocity(controlSystem, -100000).requires(this);
    }


    public Command Stop (){
        return new RunToVelocity(controlSystem, 0).requires(this);
    }


    @Override
    public void periodic() {
        motor.setPower(controlSystem.calculate(motor.getState()));

    }
}

