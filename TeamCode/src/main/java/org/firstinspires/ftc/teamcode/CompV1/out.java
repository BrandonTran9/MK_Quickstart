package org.firstinspires.ftc.teamcode.CompV1;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class out implements Subsystem {
    public static final out INSTANCE = new out();
    public out(){

    }


    MotorGroup motors = new MotorGroup(
            new MotorEx("outR"),
            new MotorEx("outL").reversed()
    );

    private ControlSystem controlSystem = ControlSystem.builder()
            .basicFF(11.1, 0 ,0)
            .build();

    public Command Out (){
        return new RunToVelocity(controlSystem, -1750, 1500).requires(this);
    }

    public Command Stop (){
        return new RunToVelocity(controlSystem, 0).requires(this);
    }

    @Override
    public void periodic() {
        motors.setPower(controlSystem.calculate(motors.getState()));

    }
    public static double getVelocity() {
        return INSTANCE.motors.getVelocity();
    }

}
