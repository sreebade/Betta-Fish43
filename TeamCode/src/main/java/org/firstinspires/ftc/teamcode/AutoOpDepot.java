package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;

/*
 * Created by Tej Bade on 10/6/18.
 */

@Autonomous(name = "Depot")
public class AutoOpDepot extends AutoOpBase {

    public void runOpMode() throws InterruptedException {
        initRobot();

        waitForStart();

        startRobot(); //views the minerals, drops down, and drives forward

        //SAMPLING CODE
        switch (sampling) {
            case 0: //gold mineral is left from robot's point of view
                turnLeftTime(0.8, 250);
                driveForwardDistance(r.getCurrentAngle(), 40, 0.7);
                turnRightTime(0.3, 950);
                mecanumStrafeLeftTime(1, 1200);
                mecanumStrafeRightTime(1, 400);
                driveForwardDistance(r.getCurrentAngle(), 20, 0.3);
                dropMarker();
                driveBackwardDistance(r.getCurrentAngle(), 70, 0.7); //drive to crater
                break;
            case 1: //gold mineral is center from robot's point of view
                driveForwardDistance(r.getCurrentAngle(),37, 0.7);
                dropMarker();
                driveBackwardDistance(r.getCurrentAngle(),24, 0.7);
                turnLeftTime(0.3, 1300);
                driveForwardDistance(45, 0.7);
                turnLeftTime(0.3, 1000);
                driveForwardDistance(20, 1);
                break;
            case 2: //gold mineral is right from robot's point of view
                turnRightTime(0.7, 200);
                driveForwardDistance( 30, 0.7);
                driveBackwardDistance(27, 0.7);
                turnLeftTime(0.7, 625);
                driveForwardDistance(47, 0.7);
                turnRightTime(0.7,650);
                mecanumStrafeLeftTime(1, 500);
                mecanumStrafeRightTime(1, 300);
                driveForwardDistance(25, 0.7);
                dropMarker();
                driveBackwardDistance(70, 1);
                break;
            default:
                driveForwardDistance(r.getCurrentAngle(),37, 0.7);
                dropMarker();
                driveBackwardDistance(r.getCurrentAngle(),24, 0.7);
                turnLeftTime(0.3, 1300);
                driveForwardDistance(45, 0.7);
                turnLeftTime(0.3, 1000);
                driveForwardDistance(20, 1);
                break;
        }


        while (opModeIsActive()) {
            idle();
        }

        r.stop();

    }

}