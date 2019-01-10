package spartanlib.subsystem.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class DriveTrain extends Subsystem {

    private Command defaultCommand;

    public DriveTrain() {
    }

    public void newDefaultCommand(Command com) {
        defaultCommand = com;
        initDefaultCommand();
    }

    @Override
    public void initDefaultCommand() {
        if (defaultCommand != null) {
            System.out.println("Setting Default Command");
            setDefaultCommand(defaultCommand);
        }
    }

}