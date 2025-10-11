package org.firstinspires.ftc.teamcode.CompV1;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.powerable.SetPower;

public class RampS implements Subsystem {
    public static final RampS INSTANCE = new RampS();

    private RampS(){ }

    private CRServoEx servo = new CRServoEx("RampS");

    public Command Go = new SetPower(servo, 1).requires(this);
    public Command Stop = new SetPower(servo, 0).requires(this);

}
