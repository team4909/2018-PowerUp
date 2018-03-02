#include <Adafruit_NeoPixel.h>
#include <Wire.h>

#define PIXEL_PIN    5
#define PIXEL_COUNT 64

Adafruit_NeoPixel strip = Adafruit_NeoPixel(PIXEL_COUNT, PIXEL_PIN, NEO_GRB + NEO_KHZ800);
//Adafruit_NeoPixel strip_b = Adafruit_NeoPixel(30, 5, NEO_GRB + NEO_KHZ800);

int deviceNumber = 4;
void setup() {
  // Start I2C as slave
  Wire.begin(deviceNumber);
  //Wire.onReceive(receiveEvent);
  Serial.begin(9600);
  // Initialize LED Strip
  strip.begin();
//    setColor(148, 0, 211);
  strip.show();
}

void receiveEvent() {
  // Read the last number sent (0-30)
  // int LED = Wire.read();
  int LED = 1;
}

int rainbow1(int wait){
  for(int i = 0; i <= 40; i++){
    int i1 = i-1;
    int i2 = i;
    int i3 = i+1; 
    strip.setPixelColor(i1, 0, 0, 170);
    strip.setPixelColor(i2, 0, 0, 255);
    strip.setPixelColor(i3, 0, 85, 170);
    strip.show();
    if(i>0){
      int x1 = i-4;
      int x2 = i-3;
      int x3 = i-2;
      int y1 = i-7;
      int y2 = i-6;
      int y3 = i-5;
      int z1 = i-10;
      int z2 = i-9;
      int z3 = i-8;
      int a1 = i-11;
      int a2 = i-12;
      int a3 = i-13;
      
      strip.setPixelColor(x1, 85, 170, 0);
      strip.setPixelColor(x2, 0, 255, 0);
      strip.setPixelColor(x3, 0, 170, 85);
      
      strip.setPixelColor(y1, 170, 42, 0);
      strip.setPixelColor(y2, 255, 0, 0);
      strip.setPixelColor(y3, 170, 85, 0);
      
      strip.setPixelColor(z1, 125, 125, 0);
      strip.setPixelColor(z2, 125, 125, 0);
      strip.setPixelColor(z3, 42, 42, 0);

      strip.setPixelColor(a1, 0, 0, 0);
      strip.setPixelColor(a2, 0, 0, 0);
      strip.setPixelColor(a3, 0, 0, 0);
      
      strip.show();
    }
    delay(wait);
  }
}

void rainbow2(uint8_t wait) {
  uint16_t i, j;

  for(j=0; j<256*5; j++) { // 5 cycles of all colors on wheel
    for(i=0; i< strip.numPixels(); i++) {
      strip.setPixelColor(i, Wheel(((i * 256 / strip.numPixels()) + j) & 255));
    }
    strip.show();
    delay(wait);
  }
}
uint32_t Wheel(byte WheelPos) {
  WheelPos = 255 - WheelPos;
  if(WheelPos < 85) {
    return strip.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  }
  if(WheelPos < 170) {
    WheelPos -= 85;
    return strip.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
  WheelPos -= 170;
  return strip.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
}

void loop() {
  int LED = 2;
  if (LED = 1){
    rainbow1(25); 
  }
  if (LED = 2){
    rainbow2(0.7);
  }
}
