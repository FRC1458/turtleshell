#include <Adafruit_NeoPixel.h>
#ifdef __AVR__
  #include <avr/power.h>
#endif

#define PIN 2
#define NUMPIXELS 102
#define DELAYTIME 0.1

Adafruit_NeoPixel p = Adafruit_NeoPixel(NUMPIXELS,PIN,NEO_GRB+NEO_KHZ800);
int[][] colors = new int[NUMPIXELS][3];
int offset = 0;
int[] cache = {0,0,0};


void setup() {
	p.begin();
	//fill the array with the correct colours
	for(int i = 0; i<NUMPIXELS; i++) {
		if(i%3==0) {
			colors[i]={100,0,0};
		}
		if(i%3==1) {
			colors[i]={0,100,0};
		}
		if(i%3==2) {
			colors[i]={0,0,100};
		}
	}
}

void loop() {
	for(int i = 0; i<colors.size; i++) {
		cache=colors[(i+offset)%NUMPIXELS];
		p.setPixelColor(i, pixels.Color(cache[0],cache[1],cache[2]));
	}
	p.show();
	delay(DELAYTIME);
	offset++;
}
