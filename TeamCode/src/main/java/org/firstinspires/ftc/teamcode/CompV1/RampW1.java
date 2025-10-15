package org.firstinspires.ftc.teamcode.CompV1;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.powerable.SetPower;

public class RampW1 implements Subsystem {
    public static final RampW1 INSTANCE = new RampW1();
    private RampW1() { }
    private CRServoEx servo = new CRServoEx("rampW1");
    public Command Go = new SetPower(servo, -1).requires(this);
    public Command no = new SetPower(servo, 0).requires(this);



}
