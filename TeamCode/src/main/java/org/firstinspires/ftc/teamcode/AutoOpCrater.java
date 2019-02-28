package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

@Autonomous(name = "Crater")
public class AutoOpCrater extends AutoOpBase {

    public void runOpMode() throws InterruptedException {
        initRobot();

        initSampling();

        waitForStart();

        startRobot(); //drop down, sample and strafe out of hook

        //SAMPLING CODE
        switch (sampling) {
            case "Left": //gold mineral is left from robot's point of view
                turnLeftTime(0.7, 250);
                driveForwardDistance(22, 0.7);
                turnLeftTime(0.7, 800);
                driveForwardDistance(20, 0.5);
                turnLeftTime(0.7, 250);
                mecanumStrafeRightTime(0.3,1500);
                mecanumStrafeLeftTime(0.3,400);
                turnRightTime(0.3, 200);
                driveForwardDistance(50, 1);
                r.intake.setPower(-0.4);
                sleep(900);
                r.intake.setPower(0);
                driveBackwardDistance(r.getCurrentAngle(), 60, 1);
                break;
            case "Center": //gold mineral is center from robot's point of view
                driveForwardDistance(20, 0.7);
                driveBackwardDistance(13, 0.7);
                turnLeftTime(0.5, 575);
                driveForwardDistance(45, 0.7);
                turnLeftTime(0.5, 700);
                mecanumStrafeRightTime(0.3,1500);
                mecanumStrafeLeftTime(0.3,400);
                turnRightTime(0.3, 200);
                driveForwardDistance(45, 1);
                r.intake.setPower(-0.4);
                sleep(900);
                r.intake.setPower(0);
                driveBackwardDistance(65, 1);
                break;
            case "Right": //gold mineral is right from robot's point of view
                turnRightTime(0.7, 200);
                driveForwardDistance(26, 0.7);
                driveBackwardDistance(26, 0.7);
                turnLeftTime(0.7, 600);
                driveForwardDistance(47, 0.7);
                turnLeftTime(0.5, 700);
                mecanumStrafeRightTime(0.3,1500);
                mecanumStrafeLeftTime(0.3,400);
                turnRightTime(0.3, 200);
                driveForwardDistance(45, 1);
                r.intake.setPower(-0.4);
                sleep(900);
                r.intake.setPower(0);
                driveBackwardDistance(65, 1);
                break;
            default: //case 1
                driveForwardDistance(20, 0.7);
                driveBackwardDistance(16, 0.7);
                turnLeftTime(0.5, 650);
                driveForwardDistance(45, 0.7);
                turnLeftTime(0.5, 700);
                mecanumStrafeRightTime(0.3,1500);
                mecanumStrafeLeftTime(0.3,400);
                turnRightTime(0.3, 200);
                driveForwardDistance(45, 1);
                r.intake.setPower(-0.4);
                sleep(900);
                r.intake.setPower(0);
                driveBackwardDistance(65, 1);
        }

        while (opModeIsActive()) {
            idle();
        }

        r.stop();
    }
}