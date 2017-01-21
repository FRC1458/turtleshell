import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {


	// 50 - 120
    public static void main(String[] args) throws Exception {

	    float kP = 3;
	    float kD = 1;
	    float lastValue = 0;
		while(true){
			float value = getXvalue();
			if(value == -1) continue;

			value = (value-640f)/640f;

			value = (value * kP) - (kD * lastValue);

			value = Math.round(value);

			lastValue = value;

			System.out.println(value);
			Thread.sleep(50);
		}

    }

    static float getXvalue() throws Exception{
	    StringBuilder data = new StringBuilder();

	    URL url = new URL("http://localhost:2084/GRIP/data?contours");
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    connection.setRequestMethod("GET");


	    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	    String line;

	    float largestArea = -100;
	    int largestAreaIndex = -1;




	    while(!(line = reader.readLine()).contains("\"area\": ["));

	    int i = 0;
	    while (!(line = reader.readLine()).contains("]")){
		    line = line.replace(",", "");
		    if(Float.parseFloat(line) > largestArea){
			    largestAreaIndex = i;
			    largestArea = Float.parseFloat(line);
		    }
		    i++;
	    }

	    if(largestArea < 10000) return -1;


	    while(!(line = reader.readLine()).contains("\"centerX\": ["));

	    for(int x = 0; x < largestAreaIndex; x++){
		    reader.readLine();
	    }

	    return Float.parseFloat(reader.readLine().replace(",", ""));
    }
}
