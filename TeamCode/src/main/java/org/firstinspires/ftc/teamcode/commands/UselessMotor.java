package org.firstinspires.ftc.teamcode.commands;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.MotorEx;





    public class UselessMotor implements Subsystem {
        public static final UselessMotor INSTANCE = new UselessMotor();

        private UselessMotor() { }

        private MotorEx motor = new MotorEx("exp0");

        private ControlSystem controlSystem = ControlSystem.builder()
                .posPid(0.005, 0, 0)
                .elevatorFF(0)
                .build();

        public Command spinLeft = new RunToPosition(controlSystem, 1000).requires(this);
        public Command spinRight = new RunToPosition(controlSystem, -1000).requires(this);

        @Override
        public void periodic() {
            motor.setPower(controlSystem.calculate(motor.getState()));
        }
    }








