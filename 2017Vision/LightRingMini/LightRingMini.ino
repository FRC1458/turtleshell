#include <Adafruit_NeoPixel.h>

Adafruit_NeoPixel ledRing = Adafruit_NeoPixel(12, 0, NEO_GRB + NEO_KHZ800);

void setup() {
    ledRing.begin();
    ledRing.show();

}

void loop() {
    for(int i = 0; i < 12; i++) {
        ledRing.setPixelColor(i, ledRing.Color(0, 120, 0));
    }

    ledRing.show();
}
