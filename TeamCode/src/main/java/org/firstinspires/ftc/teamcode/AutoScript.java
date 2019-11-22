package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.scripting.ScriptStatement;
import org.firstinspires.ftc.teamcode.scripting.ParsedScript;
import org.firstinspires.ftc.teamcode.scripting.MovementScriptStatement;

@Autonomous
public class AutoScript extends OpMode {
    ParsedScript script;
    FileSaver file;
    MovementManager driver;
    ElapsedTime timer;
    int currentLine = 0;

    public void init() {
        file = new FileSaver("ftcscript.txt");
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));
        timer = new ElapsedTime();
        script = new ParsedScript(file.readLines());
    }
    public void loop() {

        if(currentLine <= script.length) {
            ScriptStatement currentStatement = script.getStatement(currentLine);

            if (currentStatement instanceof MovementScriptStatement) {

                MovementOrder currentOrder = ((MovementScriptStatement) script.getStatement(currentLine)).order;
            }
            if (script.getStatement(currentLine).finished(RobotState.current)) currentLine++;
        }

        telemetry.addData("Instructions Completed", currentLine + "/" + script.length);
    }
}