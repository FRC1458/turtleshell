package com.team1458.test;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

import java.net.InetAddress;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        NetworkTable.setClientMode();
        InetAddress address = InetAddress.getByName("roborio-1458-frc.local");
        NetworkTable.setIPAddress(address.getHostAddress());

        NetworkTable SmartDashboard = NetworkTable.getTable("SmartDashboard");

        Scanner s = new Scanner(System.in);

        //SmartDashboard.putNumber("TestValueJetson", 42);

        while(true) {
            if(s.hasNextInt()) {
                SmartDashboard.putNumber("TestValueJetson", s.nextInt());
            }
        }
    }


}
