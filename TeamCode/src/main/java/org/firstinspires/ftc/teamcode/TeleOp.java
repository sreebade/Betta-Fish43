package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;

/*
 * Created by Tej Bade on 10/6/18.
 */

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {
    Robot r = new Robot();
    int direction = 1;
    double position = 1;

    public void runOpMode() throws InterruptedException {
        // Initialize the drive system variables.
        // The init() method of the hardware class does all the work here
        r.init(hardwareMap, telemetry);
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            // Joystick value test
            telemetry.addData("leftX", gamepad1.left_stick_x);
            telemetry.addData("leftY", gamepad1.left_stick_y);
            telemetry.addData("rightX", gamepad1.right_stick_x);
            telemetry.addData("rightY", gamepad1.right_stick_y);
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
            if (gamepad2.y) { //slide up
                r.extendingArm.setPower(1);
            } else if (gamepad2.a) { //slide down
                r.extendingArm.setPower(-1);
            } else {
                r.extendingArm.setPower(0);
            }

            if (gamepad2.b) { //rotate up
                r.extendingArm.setPower(1);
            } else if (gamepad2.x) { //rotate down
                r.extendingArm.setPower(-1);
            } else {
                r.extendingArm.setPower(0);
            }

            if (gamepad2.right_bumper) {
                r.intake.setPower(-1);
            }
            if(gamepad2.x)
            {
                // dump minerals, rotating arm up. reverse is up
                r.rotatingArm.setPower(-0.8);
            } else if(gamepad2.b)
            {
                // go down (to retrieve minerals). forward is down
                r.rotatingArm.setPower(0.6);
            } else
                r.rotatingArm.setPower(0);

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
