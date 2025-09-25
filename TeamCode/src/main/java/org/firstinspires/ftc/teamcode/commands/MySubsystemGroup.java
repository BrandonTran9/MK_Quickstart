package org.firstinspires.ftc.teamcode.commands;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.SubsystemGroup;

public class MySubsystemGroup extends SubsystemGroup {
    public static final MySubsystemGroup INSTANCE = new MySubsystemGroup();

    private MySubsystemGroup () {
        super(
                UslelessServo.INSTANCE,
                UselessMotor.INSTANCE
        );
    }
    public final Command action = new SequentialGroup(
            UslelessServo.INSTANCE.half,
            UselessMotor.INSTANCE.spinLeft
    ).named("action");

    public final Command action1 = new ParallelGroup(

            UslelessServo.INSTANCE.full,
            UselessMotor.INSTANCE.spinRight
    );
}

