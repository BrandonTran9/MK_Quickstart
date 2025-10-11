package org.firstinspires.ftc.teamcode.CompV1;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.powerable.SetPower;

public class RampB implements Subsystem {
    public static final RampB INSTANCE = new RampB();
    private RampB() { }
    private CRServoEx servo = new CRServoEx("rampB");
    public Command Go = new SetPower(servo, 1).requires(this);
    public Command Stop = new SetPower(servo, 1).requires(this);



}
