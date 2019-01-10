package org.firstinspires.ftc.teamcode;

/*
 * Created by Tej Bade on 10/6/18.
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

import java.util.List;

public abstract class AutoOpBase extends LinearOpMode {

    Robot r = new Robot();

    public void initRobot() throws InterruptedException {
        r.init(hardwareMap, telemetry);
        r.resetEncoders();
        r.setUseEncoderMode();
    }

    public void startRobot() throws InterruptedException {
        sampling();
        //r.winch.setPower(1);
        //sleep(10000);
        //r.winch.setPower(0);
        driveForwardDistance(r.getCurrentAngle(),4, 0.2);
        //r.winch.setPower(-1);
        //sleep(4200);
        //r.winch.setPower(0);
    }

    public void driveForwardDistance(double maintainAngle, int forwardInches, double driveSpeed) {
        if (opModeIsActive()) {
            int newRightBackTarget = r.rightBack.getCurrentPosition() + (int) (forwardInches * r.COUNTS_PER_INCH);
            int newLeftBackTarget = r.leftBack.getCurrentPosition() + (int) (forwardInches * r.COUNTS_PER_INCH);
            int newRightFrontTarget = r.rightFront.getCurrentPosition() + (int) (forwardInches * r.COUNTS_PER_INCH);
            int newLeftFrontTarget = r.leftFront.getCurrentPosition() + (int) (forwardInches * r.COUNTS_PER_INCH);

            r.rightBack.setTargetPosition(newRightBackTarget);
            r.rightFront.setTargetPosition(newRightFrontTarget);
            r.leftBack.setTargetPosition(newLeftBackTarget);
            r.leftFront.setTargetPosition(newLeftFrontTarget);

            r.setRunToPositionMode();

            r.driveForward(driveSpeed);

            while (opModeIsActive() && r.isBusy()) {
                float newAngle = r.getCurrentAngle();
                if (newAngle != maintainAngle && Math.abs(newAngle - maintainAngle) > 1) {
                    if (newAngle < maintainAngle) {
                        // bot veered right. give less power to the left motor.
                        r.driveForward(driveSpeed, r.LEAN_LEFT);
                    } else {
                        // bot veered left. give less power to the right motor.
                        r.driveForward(driveSpeed, r.LEAN_RIGHT);
                    }
                } else {
                    r.driveForward(driveSpeed, r.GO_STRAIGHT);
                }

                double endAngle = r.getCurrentAngle();

                telemetry.addData("Angle: ", "Start: " + maintainAngle + " End: " + endAngle);
                telemetry.update();
            }

        }
        r.stopDriving();
        sleep(100);
        r.setUseEncoderMode();
    }

    public void driveBackwardDistance(double maintainAngle, int backwardInches, double driveSpeed) {
        backwardInches = -1 * backwardInches;

        if (opModeIsActive()) {
            int newRightBackTarget = r.rightBack.getCurrentPosition() + (int) (backwardInches * r.COUNTS_PER_INCH);
            int newLeftBackTarget = r.leftBack.getCurrentPosition() + (int) (backwardInches * r.COUNTS_PER_INCH);
            int newRightFrontTarget = r.rightFront.getCurrentPosition() + (int) (backwardInches * r.COUNTS_PER_INCH);
            int newLeftFrontTarget = r.leftFront.getCurrentPosition() + (int) (backwardInches * r.COUNTS_PER_INCH);

            r.rightBack.setTargetPosition(newRightBackTarget);
            r.rightFront.setTargetPosition(newRightFrontTarget);
            r.leftBack.setTargetPosition(newLeftBackTarget);
            r.leftFront.setTargetPosition(newLeftFrontTarget);

            r.setRunToPositionMode();

            r.driveBackward(driveSpeed);

            while (opModeIsActive() && r.isBusy()) {
                float newAngle = r.getCurrentAngle();
                if (newAngle != maintainAngle && Math.abs(newAngle - maintainAngle) > 1) {
                    if (newAngle < maintainAngle) {
                        // bot veered right. give less power to the left motor.
                        r.driveBackward(driveSpeed, r.LEAN_LEFT);
                    } else {
                        // bot veered left. give less power to the right motor.
                        r.driveBackward(driveSpeed, r.LEAN_RIGHT);
                    }
                } else {
                    r.driveBackward(driveSpeed, r.GO_STRAIGHT);
                }

                double endAngle = r.getCurrentAngle();

                telemetry.addData("Angle: ", "Start: " + maintainAngle + " End: " + endAngle);
                telemetry.update();
            }

            r.stopDriving();
            sleep(100);
            r.setUseEncoderMode();
        }
    }

    public void turnLeftToAngle(double targetAngle) {
        float currentAngle = r.getCurrentAngle();
        if (targetAngle == currentAngle) {
            // Nothing to do.
            return;
        }

        // Check if the target angle is to the left of current angle
        if (targetAngle > currentAngle) {
            turnLeftToAngleLocal(targetAngle);
        } else {
            // We will be crossing over the 180 mark.

            // Let's first go to 180.
            turnLeftToAngleLocal(180);

            // Now let's go to the actual target angle.
            turnLeftToAngleLocal(targetAngle);
        }

        r.stopDriving();
        sleep(100);
        r.setUseEncoderMode();
    }

    private void turnLeftToAngleLocal(double targetAngle) {
        float currentAngle = r.getCurrentAngle();
        if (opModeIsActive() && currentAngle < targetAngle) {
            r.turnLeft(r.TURN_SPEED_NORMAL);
        }

        while (opModeIsActive() && targetAngle > currentAngle) {
            // Keep turning.
            currentAngle = r.getCurrentAngle();
            telemetry.addData("Angle", currentAngle);
            telemetry.update();
        }

        r.stopDriving();
    }

    public void turnRightToAngle(double targetAngle) {
        float currentAngle = r.getCurrentAngle();
        if (targetAngle == currentAngle) {
            // Nothing to do.
            return;
        }

        // Check if the target angle is to the left of current angle
        if (targetAngle < currentAngle) {
            turnRightToAngleLocal(targetAngle);
        } else {
            // We will be crossing over the 180 mark.

            // Let's first go to -179.99.
            turnRightToAngleLocal(-179.99);

            // Now let's go to the actual target angle.
            turnRightToAngleLocal(targetAngle);
        }

        r.stopDriving();
        sleep(100);
        r.setUseEncoderMode();
    }

    private void turnRightToAngleLocal(double targetAngle) {
        float currentAngle = r.getCurrentAngle();
        if (opModeIsActive() && targetAngle < currentAngle) {
            r.turnRight(r.TURN_SPEED_NORMAL);
        }

        while (opModeIsActive() && targetAngle < currentAngle) {
            // Keep turning.
            currentAngle = r.getCurrentAngle();
            telemetry.addData("Target Angle", targetAngle);
            telemetry.addData("Current Angle", currentAngle);
            telemetry.update();
        }

        r.stopDriving();
    }

    public void mecanumStrafeLeftTime(double power, int time) {
        if (opModeIsActive()) {
            r.mecanumStrafeLeft(power);
            sleep(time);
            r.stopDriving();
            sleep(100);
        }
    }

    public void mecanumStrafeRightTime(double power, int time) {
        if (opModeIsActive()) {
            r.mecanumStrafeRight(power);
            sleep(time);
            r.stopDriving();
            sleep(100);
        }
    }

    private void sampling() {

        r.initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            r.initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        /** Activate Tensor Flow Object Detection. */
        if (r.tfod != null) {
            r.tfod.activate();
        }

        while (opModeIsActive()) {
            if (r.tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since the last time that call was made.
                List<Recognition> updatedRecognitions = r.tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null && updatedRecognitions.size() == 3) {
                        int goldMineralX = -1;
                        int silverMineral1X = -1;
                        int silverMineral2X = -1;
                        for (Recognition recognition : updatedRecognitions) {
                            if (recognition.getLabel().equals(r.LABEL_GOLD_MINERAL)) {
                                goldMineralX = (int) recognition.getLeft();
                            } else if (silverMineral1X == -1) {
                                silverMineral1X = (int) recognition.getLeft();
                            } else {
                                silverMineral2X = (int) recognition.getLeft();
                            }
                        }
                        if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                            if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                telemetry.addData("Gold Mineral Position", "Left");
                                r.sampling = 0;
                            } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                telemetry.addData("Gold Mineral Position", "Right");
                                r.sampling = 2;
                            } else {
                                telemetry.addData("Gold Mineral Position", "Center");
                                r.sampling = 1;
                            }
                        }
                        telemetry.update();

                        if (r.tfod != null) {
                            r.tfod.shutdown();
                        }

                        return;
                }

            }
        }
    }

    public void dropMarker() {
        if(opModeIsActive()) {
            sleep(200);
            //r.intake.setPower(-0.7);
            sleep(3000); //spin outward for 3 secs.
            //r.intake.setPower(0);
        }
    }

}
