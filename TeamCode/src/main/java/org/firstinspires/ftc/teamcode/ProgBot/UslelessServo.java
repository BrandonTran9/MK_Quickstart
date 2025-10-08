package org.firstinspires.ftc.teamcode.ProgBot;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

public class UslelessServo implements Subsystem {
    public static final UslelessServo INSTANCE = new UslelessServo();

    private UslelessServo() { }
    private ServoEx servo = new ServoEx("uselessServo");
    public Command half = new SetPosition(servo,0.5).requires(this);
    public Command full = new SetPosition(servo, 1).requires(this);


}