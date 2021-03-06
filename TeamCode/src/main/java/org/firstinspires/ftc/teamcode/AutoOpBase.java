package org.firstinspires.ftc.teamcode;

/*
 * Created by Tej Bade on 10/6/18.
 */

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public abstract class AutoOpBase extends LinearOpMode {

    Robot r = new Robot();
    final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    final String LABEL_GOLD_MINERAL = "Gold Mineral";
    final String LABEL_SILVER_MINERAL = "Silver Mineral";
    public String sampling = "Default";


    final String VUFORIA_KEY = "AfxXQqT/////AAABmeV5q1PmwEdgj5TLHPs/fLpiSGjLwPtQfI0I2e7Trulm22828KlsKt1Yd8h7HyvouNBn+ATRb8cYn84cJZZEkO8fOMNNP3fpxrM24Mws75J37WlwNYI4jPWLwGYl8R1URCO03RWUfI5DU+/RwL916RQGJZn+W6zjjzEAepEeMkTxXlxef3iaufyDtHzXalQMWkTURL8L+glxH0fzupa03nHyyZZYkz1ByycMR6dBnMo/VQOljRbdf+cU0NWnxUiR5L7Afnxrb3E+rcAgA7dy2WQA98Hx/0GGXeYQoF9Xtnve6CiqEoTpcxNs2HNvwpC658wd6p11yxYPZKj/tHLlIyQq6gYPA/1A1o1shPFQKt+e";

    VuforiaLocalizer vuforia;
    TFObjectDetector tfod;

    public void initRobot() throws InterruptedException {
        r.init(hardwareMap, telemetry);
        r.resetEncoders();
        r.setUseEncoderMode();
        telemetry.addData("Initialization", "Success");
        telemetry.update();
    }

    public void startRobot() {
        sampling(this);
        r.winch.setPower(-1);
        sleep(11000);
        r.winch.setPower(0);
        driveForwardDistance(5, 0.5);
    }

    public void driveForwardDistance(int forwardInches, double driveSpeed) {
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

            }

        }
        r.stopDriving();
        sleep(100);
        r.setUseEncoderMode();
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
                if (Math.abs(newAngle - newAngle) > 1) {
                    if (newAngle < maintainAngle) {
                        // bot veered right. give less power to the left motor.
                        r.driveForwardLean(driveSpeed, r.LEAN_LEFT);
                    } else {
                        // bot veered left. give less power to the right motor.
                        r.driveForwardLean(driveSpeed, r.LEAN_RIGHT);
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
        r.setUseEncoderMode();
    }

    public void driveBackwardDistance(int backwardInches, double driveSpeed) {
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

            }

            r.stopDriving();
            sleep(200);
            r.setUseEncoderMode();
        }
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

            while (opModeIsActive() && r.isBusy()) {
                float newAngle = r.getCurrentAngle();
                if (newAngle != maintainAngle && Math.abs(newAngle - maintainAngle) > 1) {
                    if (newAngle < maintainAngle) {
                        // bot veered right. give less power to the left motor.
                        r.driveBackwardLean(driveSpeed, r.LEAN_LEFT);
                    } else {
                        // bot veered left. give less power to the right motor.
                        r.driveBackwardLean(driveSpeed, r.LEAN_RIGHT);
                    }
                } else {
                    r.driveBackwardLean(driveSpeed, r.GO_STRAIGHT);
                }

                double endAngle = r.getCurrentAngle();
                telemetry.addData("Angle: ", "Start: " + maintainAngle + " End: " + endAngle);
                telemetry.update();

            }

            r.stopDriving();
            sleep(200);
            r.setUseEncoderMode();
        }
    }

    public void turnLeftToAngle(double targetAngle) {
        float currentAngle = r.getCurrentAngle();
        telemetry.addData("Angle", r.getCurrentAngle());
        telemetry.update();
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

        r.turnLeft(r.TURN_SPEED_NORMAL);

        while (opModeIsActive() && targetAngle > currentAngle) {
            // Keep turning.
            currentAngle = r.getCurrentAngle();
            telemetry.addData("Target Angle", targetAngle);
            telemetry.addData("Current Angle", currentAngle);
            telemetry.update();

        }

        r.stopDriving();
    }

    public void turnLeftTime(double power, int time) {
        r.turnLeft(power);
        sleep(time);
        r.stopDriving();
    }

    public void turnRightToAngle(double targetAngle) {
        float currentAngle = r.getCurrentAngle();
        if (targetAngle == currentAngle) {
            // Nothing to do.
            return;
        }

        // Check if the target angle is to the right of current angle
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

    public void turnRightTime(double power, int time) {
        r.turnRight(power);
        sleep(time);
        r.stopDriving();
    }

    public void mecanumStrafeLeftTime(double power, int time) {
        if (opModeIsActive()) {
            r.mecanumStrafeLeft(power);
            sleep(time);
            r.stopDriving();
        }
    }

    public void mecanumStrafeRightTime(double power, int time) {
        if (opModeIsActive()) {
            r.mecanumStrafeRight(power);
            sleep(time);
            r.stopDriving();
        }
    }

    public void initSampling() {
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        }

        //Activate Tensor Flow Object Detection.
        if (tfod != null) {
            tfod.activate();
        }

        telemetry.addData("Sampling Initialization", "Success");
        telemetry.update();
    }

    public void sampling(LinearOpMode om) {

        boolean mineralIsFound = false;
        double startTime = om.getRuntime();

        while (!mineralIsFound && om.opModeIsActive() && om.getRuntime() - startTime < 3) {
            if (tfod != null) {
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    telemetry.addData("# Objects Detected", updatedRecognitions.size());
                    if (updatedRecognitions.size() >= 1) {
                        for (Recognition recognition : updatedRecognitions) {
                            if (recognition.getLabel().equals(LABEL_GOLD_MINERAL) && recognition.getLeft() < 280) {
                                float goldMineralX = recognition.getTop();
                                float goldMineralY = recognition.getLeft();
                                telemetry.addData("Gold Mineral X", goldMineralX);
                                telemetry.addData("Gold Mineral Y", goldMineralY);
                                if (goldMineralX < 250) {
                                    telemetry.addData("Gold Mineral Position", "Left");
                                    sampling = "Left";
                                } else if (goldMineralX > 500) {
                                    telemetry.addData("Gold Mineral Position", "Right");
                                    sampling = "Right";
                                } else {
                                    telemetry.addData("Gold Mineral Position", "Center");
                                    sampling = "Center";
                                }
                                telemetry.update();
                                mineralIsFound = true;
                            }
                        }
                    }
                    telemetry.update();
                }
            }
        }
        if (tfod != null) {
            tfod.shutdown();
        }
    }

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        // set the minimumConfidence to a higher percentage to be more selective when identifying objects.
        tfodParameters.minimumConfidence = 0.6;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL);
    }


}