package org.firstinspires.ftc.teamcode.CompV1;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.CRServoEx;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.powerable.SetPower;

public class rampAdj implements Subsystem {
    public static final rampAdj INSTANCE = new rampAdj();

    private rampAdj() {
    }

    private ServoEx servo = new ServoEx("rampAdj");

    public Command up = new SetPosition(servo, 1).requires(this);
    public Command flat = new SetPosition(servo, 0).requires(this);
    public Command half = new SetPosition(servo, 0.5).requires(this);
}
