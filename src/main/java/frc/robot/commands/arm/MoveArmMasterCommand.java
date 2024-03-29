package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
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

    ArmPosition goal;

    public MoveArmMasterCommand(Hinge hinge, Telescope telescope, Wrist wrist, Grabber grabber, ArmPosition goal) {
        this.hinge = hinge;
        this.telescope = telescope;
        this.wrist = wrist;
        this.grabber = grabber;
        this.goal = goal;

        addCommands(new InstantCommand(() -> {
            ArmPositionHelper.hingeAdjustment = 0;
            ArmLogicAssistant.updatePositions(goal);
            ArmPositionHelper.currentPosition = goal;
        }));

        /*addCommands(new ConditionalCommand(
            new ParallelCommandGroup(
                new SequentialCommandGroup(
                    new MoveHingeCommand(hinge, goal),
                    new HoldHingeCommand(hinge, true)
                ),
                new MoveTelescopeCommand(telescope, goal)
            ),
            new SequentialCommandGroup(
                new ParallelDeadlineGroup(
                    new RetractTelescopeCommand(telescope),
                    new HoldHingeCommand(hinge, false)
                ),
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                        new MoveHingeCommand(hinge, goal),
                        new HoldHingeCommand(hinge, true)
                    ),
                    new MoveTelescopeCommand(telescope, goal)
                )
            ),
            this::startIsEnd)
        );*/
        addCommands(new SequentialCommandGroup(
            new ParallelDeadlineGroup(
                new RetractTelescopeCommand(telescope),
                new HoldHingeCommand(hinge, false)
            ),
            new SequentialCommandGroup(
                new MoveHingeCommand(hinge, goal),
                new ParallelCommandGroup(
                    new MoveTelescopeCommand(telescope, goal),
                    new HoldHingeCommand(hinge, true)
                )
            )
        ));

        //wrist commands - TODO: add at wrist position
        addCommands(new ConditionalCommand(
            new SequentialCommandGroup(
                new WaitCommand(2),
                new MoveWristCommand(wrist, goal),
                new HoldWristCommand(wrist)
            ), 
            new SequentialCommandGroup(
                new MoveWristCommand(wrist, goal),
                new HoldWristCommand(wrist)
            ),
            this::dangerousMove)
        );

        //grabber commands
        addCommands(new HoldInCommand(grabber));

        //information commands
        addCommands(new RunCommand(() -> Crashboard.toDashboard("At Hinge Position", ArmLogicAssistant.atHingePosition, Constants.ARM_TAB)));
        addCommands(new RunCommand(() -> Crashboard.toDashboard("At Telescope Position", ArmLogicAssistant.atTelescopePosition, Constants.ARM_TAB)));
        addCommands(new RunCommand(() -> Crashboard.toDashboard("At Wrist Position", ArmLogicAssistant.atTelescopePosition, Constants.ARM_TAB)));
        //addCommands(new RunCommand(() -> System.out.println("Start: " + ArmLogicAssistant.startPosition)));
        //addCommands(new RunCommand(() -> System.out.println("End: " + ArmLogicAssistant.endPosition)));
    }

    public boolean startIsEnd() {
        return ArmLogicAssistant.startPosition == ArmLogicAssistant.endPosition;
    }

    public boolean dangerousMove() {
        /*if(ArmLogicAssistant.startPosition == ArmPosition.START || ArmLogicAssistant.startPosition == ArmPosition.PICKUP_LOW) {
            if(ArmLogicAssistant.endPosition == ArmPosition.START || ArmLogicAssistant.endPosition == ArmPosition.PICKUP_LOW) {
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }*/

        return false;
    }
}