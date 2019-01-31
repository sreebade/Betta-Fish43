package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;

/*
 * Created by Tej Bade on 10/6/18.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {
    Robot r = new Robot();
    int direction = 1;

    public void runOpMode() throws InterruptedException {
        // Initialize the drive system variables.
        // The init() method of the hardware class does all the work here
        r.init(hardwareMap, telemetry);
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            // Joystick value test
            telemetry.addData("lb", r.leftBack.getPower());
            telemetry.addData("lf", r.leftFront.getPower());
            telemetry.addData("rb", r.rightBack.getPower());
            telemetry.addData("rf", r.rightFront.getPower());
            telemetry.update();

            //Gamepad 1

            if (Math.abs(gamepad1.left_stick_y) > Math.abs(gamepad1.left_stick_x) && gamepad1.left_stick_y < -0.2)
                r.driveForward(Math.abs(gamepad1.left_stick_y) * direction);
            else if (Math.abs(gamepad1.left_stick_y) > Math.abs(gamepad1.left_stick_x) && gamepad1.left_stick_y > 0.2)
                r.driveBackward(Math.abs(gamepad1.left_stick_y) * direction);
            else if (gamepad1.right_stick_x > 0.2)
                r.turnRight(Math.abs(gamepad1.right_stick_x));
            else if (gamepad1.right_stick_x < -0.2)
                r.turnLeft(Math.abs(gamepad1.right_stick_x));
            else if (Math.abs(gamepad1.left_stick_x) > Math.abs(gamepad1.left_stick_y) && gamepad1.left_stick_x > 0.2)
                r.mecanumStrafeRight(Math.abs(gamepad1.left_stick_x) * direction);
            else if (Math.abs(gamepad1.left_stick_x) > Math.abs(gamepad1.left_stick_y) && gamepad1.left_stick_x < -0.2)
                r.mecanumStrafeLeft(Math.abs(gamepad1.left_stick_x) * direction);
            else
                r.stopDriving();


            if (gamepad1.left_stick_button) {
                direction = -direction;
                while (gamepad1.left_stick_button) {
                    //DO NOTHING UNTIL RELEASED
                }
            }


            //Gamepad 2

            if (gamepad2.y) { //rotate up
                r.rotatingArm.setPower(0.8);
            } else if (gamepad2.a) { //rotate down
                r.rotatingArm.setPower(-0.8);
            } else {
                r.rotatingArm.setPower(0);
            }

            if (gamepad2.b) { //extend out
                r.extendingArm.setPower(0.8);
            } else if (gamepad2.x) { //extend in
                r.extendingArm.setPower(-0.8);
            } else {
                r.extendingArm.setPower(0);
            }

            if (gamepad2.left_bumper) {
                r.intake.setPower(-1); //intake
            } else if (gamepad2.right_bumper) {
                r.intake.setPower(1); //outtake
            } else {
                r.intake.setPower(0);
            }

            if (gamepad2.dpad_up)
                r.winch.setPower(-1);
            else if (gamepad2.dpad_down)
                r.winch.setPower(1);
            else
                r.winch.setPower(0);

            idle();
        }

        r.stop();

    }

    /*private void mecanumStrafeLeftTurn(double driveSpeed) {
        double maintainAngle = r.getCurrentAngle();
        while (Math.abs(gamepad1.left_stick_x) > Math.abs(gamepad1.left_stick_y) && gamepad1.left_stick_x < -0.2) {
            float newAngle = r.getCurrentAngle();
            if (Math.abs(newAngle - maintainAngle) > 1.5) {
                r.turnLeft(0.1);
            } else {
                r.mecanumStrafeLeft(driveSpeed);
            }
        }

    }

    private void mecanumStrafeRightTurn(double driveSpeed) {
        double maintainAngle = r.getCurrentAngle();
        while (Math.abs(gamepad1.left_stick_x) > Math.abs(gamepad1.left_stick_y) && gamepad1.left_stick_x > 0.2) {
            float newAngle = r.getCurrentAngle();
            r.mecanumStrafeRight(driveSpeed);
            while (Math.abs(newAngle - maintainAngle) > 1) {
                r.turnRight(0.5);
            }
        }

    }
    */
}
