#include <Adafruit_NeoPixel.h>

Adafruit_NeoPixel ledRing = Adafruit_NeoPixel(12, A0, NEO_GRB + NEO_KHZ800);

void setup() {
    ledRing.begin();
    ledRing.show();

    pinMode(2, INPUT_PULLUP);
    digitalWrite(2, HIGH);

    
    pinMode(3, INPUT_PULLUP);
    digitalWrite(3, HIGH);

}

void loop() {
    
    int brightness = 100;

    if(digitalRead(3) == LOW){
        brightness = 255;
    }
  
    for(int i = 0; i < 12; i++) {
        if(digitalRead(2) == LOW){
            ledRing.setPixelColor(i, ledRing.Color(0, brightness, 0));
        } else{ 
            ledRing.setPixelColor(i, ledRing.Color(brightness, brightness, brightness));
        }
    }

    ledRing.show();
}
