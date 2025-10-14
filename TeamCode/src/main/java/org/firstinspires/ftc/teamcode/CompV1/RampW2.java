package org.firstinspires.ftc.teamcode.CompV1;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.powerable.SetPower;

public class RampW2 implements Subsystem {
    public static final RampW2 INSTANCE = new RampW2();
    private RampW2() { }
    private CRServoEx servo = new CRServoEx("rampW2");
    public Command Go = new SetPower(servo, 1).requires(this);
    public Command no = new SetPower(servo, 0).requires(this);



}
