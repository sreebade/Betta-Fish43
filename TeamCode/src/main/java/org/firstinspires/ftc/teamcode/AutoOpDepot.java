package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;

/*
 * Created by Tej Bade on 10/6/18.
 */

@Autonomous(name="Depot")
public class AutoOpDepot extends AutoOpBase {

    public void runOpMode() throws InterruptedException {
        initRobot();

        waitForStart();

        startRobot(); //drops down, views the minerals, strafes, and drives forward

        //SAMPLING CODE
        if(r.location == 0) { //left from robot's point of view
            mecanumStrafeLeftTime(0.5, 2000);
            driveForwardDistance(20, 0.5);
            driveBackwardDistance(10, 0.5);
            turnRightToAngle(r.getCurrentAngle() - 90);
            driveForwardDistance(50, 1);
        } else if (r.location == 2) { //right from robot's point of view
            mecanumStrafeLeftTime(0.5, 1000);
            driveForwardDistance(20, 0.5);
            sleep(100);
            driveBackwardDistance(10, 0.5);
            turnRightToAngle(r.getCurrentAngle() - 90);
            driveForwardDistance(25, 1);
        } else { //center from robot's point of view
            mecanumStrafeRightTime(0.5, 1000);
            driveForwardDistance(20, 0.5);
            sleep(100);
            driveBackwardDistance(10, 0.5);
            turnRightToAngle(r.getCurrentAngle() - 90);
            driveForwardDistance(30, 1);
        }

        turnRightToAngle(r.getCurrentAngle() - 7);
        sleep(200);

        mecanumStrafeLeftTime(0.5, 600);

        mecanumStrafeRightTime(0.5, 500);

        driveForwardDistance(r.getCurrentAngle(), 20, 0.7);

        dropMarker();

        driveBackwardDistance(r.getCurrentAngle(), 20, 0.7);

        turnRightToAngle(r.getCurrentAngle() - 179); //turn to crater
        sleep(100);

        driveForwardDistance(r.getCurrentAngle(), 30, 1); //drive to crater

        while (opModeIsActive()) {
            r.intakeArm.setPower(0.7);
            idle();
        }

        r.stop();
    }

}