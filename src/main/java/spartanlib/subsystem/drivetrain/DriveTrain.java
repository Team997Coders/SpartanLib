package spartanlib.subsystem.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class DriveTrain extends Subsystem {

    private Command defaultCommand;

    public DriveTrain(Command defaultCommand) {
        this.defaultCommand = defaultCommand;
    }

    @Override
    public void initDefaultCommand() {
        if (defaultCommand != null) {
            setDefaultCommand(defaultCommand);
        }
    }

}