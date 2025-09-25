package org.firstinspires.ftc.teamcode.Autonomus;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commands.UselessMotor;
import org.firstinspires.ftc.teamcode.commands.UslelessServo;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "NextFTCAuto")
public class NextFTCAuto extends NextFTCOpMode {
    public NextFTCAuto() {
        addComponents(
                new SubsystemComponent(UselessMotor.INSTANCE, UslelessServo.INSTANCE),
                BulkReadComponent.INSTANCE,
                new PedroComponent(Constants::createFollower)
        );
    }

    public Command autonomousRoutine() {

        return new SequentialGroup(
                UselessMotor.INSTANCE.spinLeft,
                UselessMotor.INSTANCE.spinRight,

                new ParallelGroup(
                        UselessMotor.INSTANCE.spinLeft,
                        UslelessServo.INSTANCE.half
                ),
                new Delay(1),
                new ParallelGroup(
                        UselessMotor.INSTANCE.spinRight,
                        UslelessServo.INSTANCE.full
                )
        );
    }

    @Override
    public void onStartButtonPressed() {
        autonomousRoutine().schedule();
    }

}
