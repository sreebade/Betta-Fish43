package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;

/*
 * Created by Tej Bade on 10/6/18.
 */

@Autonomous(name="Crater Basic")
public class AutoOpCraterBasic extends AutoOpBase {

    public void runOpMode() throws InterruptedException {
        initRobot();

        waitForStart();


        while (opModeIsActive()) {
            idle();
        }

    }

}