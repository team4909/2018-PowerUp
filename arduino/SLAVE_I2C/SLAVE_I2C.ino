#include <Adafruit_NeoPixel.h>
#include <Adafruit_NeoMatrix.h>
#include <Adafruit_GFX.h> 
#include <Wire.h>

#define PIXEL_PIN    6
#define PIXEL_COUNT 64

Adafruit_NeoPixel strip = Adafruit_NeoPixel(PIXEL_COUNT, PIXEL_PIN, NEO_GRB + NEO_KHZ800);
Adafruit_NeoPixel strip_b = Adafruit_NeoPixel(30, 5, NEO_GRB + NEO_KHZ800);

int deviceNumber = 4;
void setup() {
  // Start I2C as slave
  Wire.begin(deviceNumber);
  Wire.onReceive(receiveEvent);
  int arduinoElevatorPosition = 0;
  Serial.begin(9600);
  // Initialize LED Strip
  strip.begin();
      setColor(148, 0, 211);
  strip.show();
}

void receiveEvent(int bytes) {
  // Read the last number sent (0-30)
  // int signalStatus = Wire.read();
  int signalStatus = 29;
  if (signalStatus <= 30){
    for(int i = 0; i >= 29-signalStatus; i++){
          strip.setPixelColor(i, 215, 40, 50);
    }
    for(int i = 0; i <= signalStatus; i++){
          strip.setPixelColor(i, 40, 215, 160);
    }   
    strip.show();
  }
  
  Serial.println(signalStatus);
}

void loop() {}

void setColor(int red, int green, int blue) {
  for(int i = 0; i < PIXEL_COUNT; i++){
    strip.setPixelColor(i, red, green, blue);
  }
  
  strip.show();
}
