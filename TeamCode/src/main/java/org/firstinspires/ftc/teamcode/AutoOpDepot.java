package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;

/*
 * Created by Tej Bade on 10/6/18.
 */

@Autonomous(name = "Depot")
public class AutoOpDepot extends AutoOpBase {

    boolean finished = false;

    public void runOpMode() throws InterruptedException {
        initRobot();

        initSampling();

        while(opModeIsActive() && !finished) {
            waitForStart();

            startRobot(); //views the minerals, drops down, and drives forward

            //SAMPLING CODE
            switch (sampling) {
                case "Left": //gold mineral is left from robot's point of view
                    turnLeftTime(0.8, 250);
                    driveForwardDistance(40, 0.7);
                    turnRightTime(0.3, 950);
                    mecanumStrafeLeftTime(0.3, 1200);
                    mecanumStrafeRightTime(0.3, 400);
                    driveForwardDistance(20, 0.3);
                    r.intake.setPower(-0.4);
                    sleep(900);
                    r.intake.setPower(0);
                    driveBackwardDistance(70, 0.7); //drive to crater
                    break;
                case "Center": //gold mineral is center from robot's point of view
                    driveForwardDistance(37, 0.7);
                    r.intake.setPower(-0.4);
                    sleep(900);
                    r.intake.setPower(0);
                    driveBackwardDistance(24, 0.7);
                    turnLeftTime(0.3, 1300);
                    driveForwardDistance(40, 0.7);
                    turnLeftTime(0.3, 400);
                    driveForwardDistance(10, 1);
                    break;
                case "Right": //gold mineral is right from robot's point of view
                    turnRightTime(0.7, 200);
                    driveForwardDistance(30, 0.7);
                    driveBackwardDistance(27, 0.7);
                    turnLeftTime(0.7, 625);
                    driveForwardDistance(47, 0.7);
                    turnRightTime(0.7, 650);
                    mecanumStrafeLeftTime(1, 500);
                    mecanumStrafeRightTime(1, 300);
                    driveForwardDistance(25, 0.7);
                    r.intake.setPower(-0.4);
                    sleep(900);
                    r.intake.setPower(0);
                    driveBackwardDistance(70, 1);
                    break;
                default:
                    driveForwardDistance(37, 0.7);
                    r.intake.setPower(-0.4);
                    sleep(900);
                    r.intake.setPower(0);
                    driveBackwardDistance(24, 0.7);
                    turnLeftTime(0.3, 1300);
                    driveForwardDistance(45, 0.7);
                    turnLeftTime(0.3, 1000);
                    driveForwardDistance(20, 1);
            }

            finished = true;

        }

        if(tfod != null) {
            tfod.shutdown();
        }

        r.stop();

    }

}