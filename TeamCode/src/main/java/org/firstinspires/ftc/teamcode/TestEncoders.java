package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;

/*
 * Created by Tej Bade on 10/6/18.
 */

@Autonomous(name="Test Encoders")
public class TestEncoders extends AutoOpBase {

    public void runOpMode() throws InterruptedException {
        initRobot();

        waitForStart();

        while (opModeIsActive()) {
            idle();
        }

        r.stop();

    }

}