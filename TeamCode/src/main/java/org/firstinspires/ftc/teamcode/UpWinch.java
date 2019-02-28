package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;

/*
 * Created by Tej Bade on 10/6/18.
 */

@Autonomous(name="Up Winch")
public class UpWinch extends AutoOpBase {

    public void runOpMode() throws InterruptedException {
        initRobot();

        waitForStart();

        r.winch.setPower(-1);

        while (opModeIsActive()) {
            idle();
        }

        r.stop();

    }

}