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
    double speed = 1;

    public void runOpMode() throws InterruptedException {
        // Initialize the drive system variables.
        // The init() method of the hardware class does all the work here
        r.init(hardwareMap, telemetry);
        r.resetEncoders();
        r.setUseEncoderMode();
        telemetry.addData("Initialization", "Success");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Display Modes
            if (direction == 1)
                telemetry.addData("Direction", "Forward");
            else if (direction == -1)
                telemetry.addData("Direction", "Reverse");

            telemetry.addData("Speed", speed);

            telemetry.addData("Rotation", r.rotatingArm.getCurrentPosition());
            telemetry.addData("Extension", -r.extendingArm.getCurrentPosition());
            telemetry.update();

            //Gamepad 1

            if (gamepad1.left_stick_button) {
                direction = -direction;
                while (gamepad1.left_stick_button) {
                    //DO NOTHING UNTIL RELEASED
                }
            }

            if (gamepad1.right_stick_button) {
                if (speed == 1)
                    speed = 0.3;
                else if (speed == 0.3)
                    speed = 1;
                while (gamepad1.right_stick_button) {
                    //DO NOTHING UNTIL RELEASED
                }
            }

            if (Math.abs(gamepad1.left_stick_y) > Math.abs(gamepad1.left_stick_x) && gamepad1.left_stick_y < -0.2)
                r.driveForward(Math.abs(gamepad1.left_stick_y) * direction * speed);
            else if (Math.abs(gamepad1.left_stick_y) > Math.abs(gamepad1.left_stick_x) && gamepad1.left_stick_y > 0.2)
                r.driveBackward(Math.abs(gamepad1.left_stick_y) * direction * speed);
            else if (gamepad1.right_stick_x > 0.2)
                r.turnRight(Math.abs(gamepad1.right_stick_x) * speed);
            else if (gamepad1.right_stick_x < -0.2)
                r.turnLeft(Math.abs(gamepad1.right_stick_x) * speed);
            else if (Math.abs(gamepad1.left_stick_x) > Math.abs(gamepad1.left_stick_y) && gamepad1.left_stick_x > 0.2)
                r.mecanumStrafeRight(Math.abs(gamepad1.left_stick_x) * direction * speed);
            else if (Math.abs(gamepad1.left_stick_x) > Math.abs(gamepad1.left_stick_y) && gamepad1.left_stick_x < -0.2)
                r.mecanumStrafeLeft(Math.abs(gamepad1.left_stick_x) * direction * speed);
            else
                r.stopDriving();

            //Gamepad 2

            if (gamepad2.dpad_left) { //Reset Encoders
                r.rotatingArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                r.extendingArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                r.rotatingArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                r.extendingArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

            if (gamepad2.y) //Rotation
                r.rotatingArm.setPower(1);
            else if (gamepad2.dpad_right) {
                if (r.rotatingArm.getCurrentPosition() < 3600)
                    r.rotatingArm.setPower(0.5);
                else
                    r.rotatingArm.setPower(0);
            } else if (gamepad2.left_stick_button) {
                if (r.rotatingArm.getCurrentPosition() < 2100)
                    r.rotatingArm.setPower(1);
                else
                    r.rotatingArm.setPower(0);
            } else if (gamepad2.a)
                r.rotatingArm.setPower(-1);
            else
                r.rotatingArm.setPower(0);

            if (gamepad2.b) //Extension
                r.extendingArm.setPower(-0.7);
            else if (gamepad2.right_stick_button) {
                if (-r.extendingArm.getCurrentPosition() > 0)
                    r.extendingArm.setPower(-0.7);
                else
                    r.extendingArm.setPower(0);
            } else if (gamepad2.x && -r.extendingArm.getCurrentPosition() < 2300)
                r.extendingArm.setPower(0.7);
            else
                r.extendingArm.setPower(0);

            if (gamepad2.left_bumper)
                r.intake.setPower(1); //intake
            else if (gamepad2.right_bumper)
                r.intake.setPower(-1); //outtake
            else
                r.intake.setPower(0);


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

}
