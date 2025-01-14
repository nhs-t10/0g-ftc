package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.teleop.*;
import org.firstinspires.ftc.teamcode.data.*;
import org.firstinspires.ftc.teamcode.*;
import org.firstinspires.ftc.teamcode.auxillary.*;

import org.firstinspires.ftc.teamcode.ManipulationManager;

@TeleOp
public class Teleop extends OpMode {
    InputManager input;
    MovementManager driver;
    ColorSensor sensor;
    ManipulationManager hands;
//    Servo sev;
    ColorSensor sensorDown;
    boolean sideLift = false;
    boolean sideGrab = false;

    private static boolean toggleSpeed = false;

    public void init() {
        input = new InputManager(gamepad1);
        driver = new MovementManager(hardwareMap.get(DcMotor.class, "fl"),
                hardwareMap.get(DcMotor.class, "fr"),
                hardwareMap.get(DcMotor.class, "bl"),
                hardwareMap.get(DcMotor.class, "br"));
        sensor = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensor"));
        sensorDown = new ColorSensor(hardwareMap.get(NormalizedColorSensor.class, "sensorDown"));

//        sev =  hardwareMap.get(Servo.class, "sev");
        hands = new ManipulationManager(
                hardwareMap.get(Servo.class, "sev"),
                hardwareMap.get(DcMotor.class, "lift"),
                hardwareMap.get(Servo.class, "sideGrab"),
                hardwareMap.get(Servo.class, "sideLift"),
                hardwareMap.get(Servo.class, "foundationGrabber"),
                hardwareMap.get(Servo.class, "dropper")
        );
        hands.liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        driver.resetAllEncoders();
        driver.frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driver.frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driver.backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        driver.backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void loop() {
        driver.driveOmni(input.getMovementControls());
        //hands.setLiftState(input.getLiftControls());

        hands.setDropperPosition(input.getDropperPosition());
        sensor.runSample();

        
        if (input.getGamepad().right_trigger >= 0.01) {
            hands.grabServo.setServoPosition(0);
        } else {
            hands.grabServo.setServoPosition(1);
        }
        if (input.getGamepad().dpad_down) {
            hands.setFoundationGrabberPosition(0);
        }
        if (input.getGamepad().dpad_up) {
            hands.setFoundationGrabberPosition(1);
        }

        //this is the speed toggle code
        /*
        if (gamepad1.left_bumper) {
            if (driver.getSpeed() == 0.25f && !toggleSpeed) {
                driver.setSpeed(1.0f);
                toggleSpeed = true;
            }
            if (driver.getSpeed() == 1.0f && !toggleSpeed) {
                driver.setSpeed(0.25f);
                toggleSpeed = true;
            } else {
                toggleSpeed = false;
            }
        }
        */

        //this is the speed single pushbutton code
        if (gamepad1.left_bumper) {
            driver.setSpeed(0.25f);
        } else {
            driver.setSpeed(1.00f);
        }


        if (gamepad1.b) {
            hands.liftMotor.setPower(-1);
        } else if (gamepad1.a) {
            hands.liftMotor.setPower(1);
        }else {
            hands.liftMotor.setPower(0);
        }

        if (gamepad1.x) {
            hands.setDropperPosition(1);
        }
        if (gamepad1.y) {
            hands.setDropperPosition(0);
        }

//        if (gamepad1.left_trigger >= 0.01) {
//            if (!sideLift) {
//                hands.setSideLiftPosition(1);
//                sideLift = true;
//            } else if (sideLift) {
//                hands.setSideLiftPosition(0);
//                sideLift = false;
//            }
//
//        }
//        if (gamepad1.right_trigger >= 0.01) {
//            if (!sideGrab) {
//                hands.setSideGrabberPosition(1);
//                sideGrab = true;
//            } else if (sideGrab) {
//                hands.setSideGrabberPosition(0);
//                sideGrab = false;
//            }
//
//        }  
        telemetry.addData("FL Ticks:", driver.frontLeft.getCurrentPosition());
        telemetry.addData("FR Ticks:", driver.frontRight.getCurrentPosition());
        telemetry.addData("BL Ticks:", driver.backRight.getCurrentPosition());
        telemetry.addData("BR Ticks:", driver.backLeft.getCurrentPosition());
        telemetry.addData("Average Ticks:", (driver.frontLeft.getCurrentPosition()+
                                                            driver.frontRight.getCurrentPosition()+
                                                                driver.backLeft.getCurrentPosition()+
                                                                    driver.backRight.getCurrentPosition())/4);


        telemetry.addData("Input LX: ", input.getGamepad().left_stick_x);
        telemetry.addData("Input LY: ", input.getGamepad().left_stick_y);
        telemetry.addData("Input RX: ", input.getGamepad().right_stick_x);
        telemetry.addData("Skystone", sensor.isSkystone());
        telemetry.addData("Blue/Red", sensor.isBled());
        telemetry.addData("Color Code", sensor.getHexCode());

        telemetry.addData("FL Power: ", driver.frontLeft.getPower());
        telemetry.addData("FL Port: ", driver.frontLeft.getPortNumber());

        telemetry.addData("FR Power: ", driver.frontRight.getPower());
        telemetry.addData("FR Port: ", driver.frontRight.getPortNumber());

        telemetry.addData("BL Power: ", driver.backLeft.getPower());
        telemetry.addData("BL Port: ", driver.backLeft.getPortNumber());

        telemetry.addData("BR Power: ", driver.backRight.getPower());
        telemetry.addData("BR Port: ", driver.backRight.getPortNumber());
    }
}