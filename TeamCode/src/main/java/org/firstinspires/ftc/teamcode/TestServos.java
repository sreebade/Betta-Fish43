package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/*
 * Created by Tej Bade on 11/14/18.
 */

@TeleOp(name="Test Servos")
public class TestServos extends AutoOpBase {

    public void runOpMode() throws InterruptedException {
        initRobot();

        waitForStart();

        if(gamepad1.left_bumper) {
            r.leftBox.setPosition(0.5);
            r.rightBox.setPosition(0.5);
        } else if (gamepad1.right_bumper) {
            r.leftBox.setPosition(0);
            r.rightBox.setPosition(0);
        }

        while (opModeIsActive()) {
            idle();
        }

        r.stop();

    }

}