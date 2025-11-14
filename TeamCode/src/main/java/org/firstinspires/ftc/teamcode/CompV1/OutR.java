package org.firstinspires.ftc.teamcode.CompV1;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class OutR implements Subsystem {
    public static final OutR INSTANCE = new OutR();
    public OutR() {

    }

    private MotorEx motor = new MotorEx("outR");


    private ControlSystem controlSystem = ControlSystem.builder()
            .velPid(.000125, .000001, 100)
            .build();

    public Command Out (){
        return new RunToVelocity(controlSystem, 1400, 1500).requires(this);
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

