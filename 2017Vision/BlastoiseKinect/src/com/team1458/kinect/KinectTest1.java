package com.team1458.kinect;

import org.openkinect.freenect.*;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class KinectTest1 {

    public static void main(String[] args) throws Exception {
        Context context = Freenect.createContext();

        Device camera = getCamera(context);

        // Tilt up and down
        /*camera.setTiltAngle(20);
        Thread.sleep(4000);
        camera.setTiltAngle(-20);
        Thread.sleep(4000);
        camera.setTiltAngle(0);*/

        // Accelerometer test
        /*while(System.in.available() == 0){
            camera.refreshTiltState();
            System.out.println(Arrays.toString(camera.getAccel()));
            Thread.sleep(250);
        }*/

        camera.setDepthFormat(DepthFormat.D10BIT, Resolution.LOW);



        camera.startDepth((FrameMode frameMode, ByteBuffer byteBuffer, int i) -> {
            System.out.println(frameMode+" "+i);
            //System.out.println(byteBuffer.toString());
        });

        Thread.sleep(50000);

        camera.close();

        context.shutdown(); // Clean up
    }

    static Device getCamera(Context context){
        Device camera = null;

        if (context.numDevices() > 0) {
            camera = context.openDevice(0);
        } else {
            System.err.println("No kinect detected.  Exiting.");
            System.exit(0);
        }

        return camera;
    }
}
