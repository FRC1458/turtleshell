import processing.serial.*;
import java.net.*;
import java.io.*;
import java.util.*;

Serial port;

void setup() 
{
  size(200, 200);
  background(255);
  String portName = Serial.list()[1];

  System.out.println(Arrays.toString(Serial.list()));

  port = new Serial(this, portName, 9600);


  float kP = 4;
  float kD = 0;
  float lastValue = 0;
  while (true) {
    float value = -1;
    try {
      value = getXvalue();
    } 
    catch(Exception e) {
    }
    
    
    //if(1==1) continue;

    if (value == -1) {
      port.write("0");
      port.write('\n');
      continue;
    }

    value = (value-640f)/640f;

    value = (value * kP) - (kD * lastValue);

    value = Math.round(value);

    lastValue = value;

    int v = -1*(int)Math.round(value);
    
    
    System.out.println(value+" v="+v);

    //System.out.println(v);
    port.write(v+"");
    port.write('\n');
    try {
      Thread.sleep(50);
    } 
    catch(Exception e) {
    }
  }
}

void draw() {
  background(255);
}

static float getXvalue() throws Exception {
  StringBuilder data = new StringBuilder();

  URL url = new URL("http://localhost:2084/GRIP/data?contours");
  HttpURLConnection connection = (HttpURLConnection) url.openConnection();
  connection.setRequestMethod("GET");


  BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
  String line;

  float largestArea = -100;
  int largestAreaIndex = -1;




  while (! (line = reader.readLine ()).contains("\"area\": ["));

  int i = 0;
  while (! (line = reader.readLine ()).contains("]")) {
    line = line.replace(",", "");
    if (Float.parseFloat(line) > largestArea) {
      largestAreaIndex = i;
      largestArea = Float.parseFloat(line);
    }
    i++;
  }

  if (largestArea < 10000) return -1;


  while (! (line = reader.readLine ()).contains("\"centerX\": ["));

  for (int x = 0; x < largestAreaIndex; x++) {
    reader.readLine();
  }

  return Float.parseFloat(reader.readLine().replace(",", ""));
}