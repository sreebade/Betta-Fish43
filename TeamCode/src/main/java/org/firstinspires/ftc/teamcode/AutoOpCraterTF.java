/* Copyright (c) 2018 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

/**
 * This 2018-2019 OpMode illustrates the basics of using the TensorFlow Object Detection API to
 * determine the position of the gold and silver minerals.
 * <p>
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 * <p>
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained below.
 */

@Autonomous(name = "Crater with TFLite")
public class AutoOpCraterTF extends AutoOpBase {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    /*l
     * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
     * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
     * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
     * web site at https://developer.vuforia.com/license-manager.
     *
     * Vuforia license keys are always 380 characters long, and look as if they contain mostly
     * random data. As an example, here is a example of a fragment of a valid key:
     *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
     * Once you've obtained a license key, copy the string from the Vuforia web site
     * and paste it in to your code on the next line, between the double quotes.
     */
    private static final String VUFORIA_KEY = "AfxXQqT/////AAABmeV5q1PmwEdgj5TLHPs/fLpiSGjLwPtQfI0I2e7Trulm22828KlsKt1Yd8h7HyvouNBn+ATRb8cYn84cJZZEkO8fOMNNP3fpxrM24Mws75J37WlwNYI4jPWLwGYl8R1URCO03RWUfI5DU+/RwL916RQGJZn+W6zjjzEAepEeMkTxXlxef3iaufyDtHzXalQMWkTURL8L+glxH0fzupa03nHyyZZYkz1ByycMR6dBnMo/VQOljRbdf+cU0NWnxUiR5L7Afnxrb3E+rcAgA7dy2WQA98Hx/0GGXeYQoF9Xtnve6CiqEoTpcxNs2HNvwpC658wd6p11yxYPZKj/tHLlIyQq6gYPA/1A1o1shPFQKt+e";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    private double maintain;

    @Override
    public void runOpMode() throws InterruptedException {
        initRobot();

        waitForStart();

        startRobot(); //drop down, sample and strafe out of hook

        telemetry.addData("Step 1: ", "Completed");
        telemetry.update();
        // The TFObjectDetector uses the camera frames from the VuforiaLocalizer, so we create that
        // first.
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        int K = 0;
        maintain = r.getCurrentAngle();
        // drive forward
        driveForwardDistance(maintain, 10, 0.7);
        // if location == 0 strafe left
        //SAMPLING CODE
        if(r.location == 0) { // left from robot's point of view
            mecanumStrafeLeftTime(0.5, 2000);
            driveForwardDistance(20, 0.5);
            driveBackwardDistance(10, 0.5);
            turnRightToAngle(r.getCurrentAngle() - 90);
            driveForwardDistance(50, 1);
        } else if (r.location == 2) { // right from robot's point of view
            mecanumStrafeLeftTime(0.5, 1000);
            driveForwardDistance(20, 0.5);
            sleep(100);
            driveBackwardDistance(10, 0.5);
            turnRightToAngle(r.getCurrentAngle() - 90);
            driveForwardDistance(25, 1);
        } else { // center from robot's point of view
            mecanumStrafeRightTime(0.5, 1000);
            driveForwardDistance(20, 0.5);
            sleep(100);
            driveBackwardDistance(10, 0.5);
            turnRightToAngle(r.getCurrentAngle() - 90);
            driveForwardDistance(30, 1);
        }

        double angle = r.getCurrentAngle()+1;

        turnLeftToAngle(angle); //turn to depot
        sleep(500);
        // if location == 2 strafe right

        if (tfod != null) {
            tfod.shutdown();
        }

        maintain = r.getCurrentAngle();
        driveForwardDistance(maintain, 36, 1);
        angle = r.getCurrentAngle() + 0.5;
        turnLeftToAngle(angle);
        maintain = r.getCurrentAngle();
        driveForwardDistance(maintain, 30, 0.8);
        angle = r.getCurrentAngle() + 2;
        turnRightToAngle(angle);
        maintain = r.getCurrentAngle();
        driveForwardDistance(maintain, 75, 1);
        r.stop();
    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

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
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }
}