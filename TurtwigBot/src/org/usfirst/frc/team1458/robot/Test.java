package org.usfirst.frc.team1458.robot;


import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author asinghani
 */
public class Test {
	public static void main(String[] args) throws Exception {
		//BlastoiseDataLogger.Update update = new BlastoiseDataLogger.Update();
		//System.out.println(update.toJSON());
		Process p = Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", "df -h | awk '$NF==\"/\"{printf \"%d\", $3}'"});
		//Process p = Runtime.getRuntime().exec("df -h | awk '$NF==\"/\"{printf \"%d\", $3}'");
		System.out.println(p.waitFor());

		BufferedReader reader =
				new BufferedReader(new InputStreamReader(p.getInputStream()));

		System.out.println(reader.readLine());
	}
}
