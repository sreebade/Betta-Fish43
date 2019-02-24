package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;

/*
 * Created by Tej Bade on 10/6/18.
 */

@Autonomous(name="TFOD")
public class TFOD extends AutoOpBase {

    public void runOpMode() throws InterruptedException {
        initRobot();

        waitForStart();

        sampling2(this);

        while (opModeIsActive()) {
            idle();
        }

        r.stop();

    }

}