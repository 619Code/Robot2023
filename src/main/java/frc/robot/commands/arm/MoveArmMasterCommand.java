package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants;
import frc.robot.commands.arm.hinge.HoldHingeCommand;
import frc.robot.commands.arm.hinge.MoveHingeCommand;
import frc.robot.commands.arm.telescope.MoveTelescopeCommand;
import frc.robot.commands.arm.telescope.RetractTelescopeCommand;
import frc.robot.commands.arm.wrist.HoldWristCommand;
import frc.robot.commands.arm.wrist.MoveWristCommand;
import frc.robot.commands.grabber.HoldInCommand;
import frc.robot.helpers.ArmLogicAssistant;
import frc.robot.helpers.ArmPositionHelper;
import frc.robot.helpers.Crashboard;
import frc.robot.helpers.enums.ArmPosition;
import frc.robot.subsystems.Grabber;
import frc.robot.subsystems.arm.Hinge;
import frc.robot.subsystems.arm.Telescope;
import frc.robot.subsystems.arm.Wrist;

public class MoveArmMasterCommand extends ParallelCommandGroup {
    Hinge hinge;
    Telescope telescope;
    Wrist wrist;
    Grabber grabber;

    public MoveArmMasterCommand(Hinge hinge, Telescope telescope, Wrist wrist, Grabber grabber, ArmPosition goal) {
        this.hinge = hinge;
        this.telescope = telescope;
        this.wrist = wrist;
        this.grabber = grabber;

        addCommands(new RunCommand(() -> Crashboard.toDashboard("At Hinge Position", ArmLogicAssistant.atHingePosition, Constants.ARM_TAB)));

        //hinge commands
        addCommands(new SequentialCommandGroup(
            new InstantCommand(() -> {
                ArmPositionHelper.hingeAdjustment = 0;
                ArmLogicAssistant.updatePositions(goal);
                ArmPositionHelper.currentPosition = goal;
            }),
            new MoveHingeCommand(hinge, goal),
            new HoldHingeCommand(hinge)
        ));

        //telescope commands
        /*if(ArmLogicAssistant.movingToBack) { //if moving front to back, retract while moving
            addCommands(new SequentialCommandGroup(
                new RetractTelescopeCommand(telescope).until(ArmLogicAssistant::atHingePosition),
                new MoveTelescopeCommand(telescope, goal)
            ));
        } else {
            addCommands(new MoveTelescopeCommand(telescope, goal));
        }*/

        //wrist commands
        /*if(ArmLogicAssistant.startPosition == ArmPosition.START) { //if the wrist doesn't have space to move, wait
            addCommands(new SequentialCommandGroup(
                new WaitCommand(2),
                new MoveWristCommand(wrist, goal),
                new HoldWristCommand(wrist)
            ));
        } else {
            addCommands(new SequentialCommandGroup(
                new MoveWristCommand(wrist, goal),
                new HoldWristCommand(wrist)
            ));
        }*/

        //grabber commands
        //addCommands(new HoldInCommand(grabber));
    }
}