package org.firstinspires.ftc.teamcode.scripting;

import org.firstinspires.ftc.teamcode.MovementOrder;
import org.firstinspires.ftc.teamcode.RobotState;

public class ScriptStatement {
    public ScriptConstraint[] constraints;

    public final static String TOKEN = "";

    public ScriptStatement(ScriptConstraint[] _constraints) {
        this.constraints = _constraints;
    }
    public boolean finished(RobotState rs) {
        for(ScriptConstraint constr : this.constraints) {
            if(!constr.test(rs.get(constr.operator))) return false;
        }
        return true;
    }
    public ScriptStatement() {}

    public void setConstraints(ScriptConstraint[] _constraints) {
        constraints = _constraints;
    }
}