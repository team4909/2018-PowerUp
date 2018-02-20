#include <Adafruit_NeoPixel.h>
#include <Wire.h>

#define PIXEL_PIN    6
#define PIXEL_COUNT 64
Adafruit_NeoPixel strip = Adafruit_NeoPixel(PIXEL_COUNT, PIXEL_PIN, NEO_GRB + NEO_KHZ800);

// Set Configuration
int lastValue = 0;
int deviceNumber = 4;

void setup() {
  // Start I2C as slave
  Wire.begin(deviceNumber);
  Wire.onReceive(receiveEvent);

  Serial.begin(9600);

  // Initialize LED Strip
  strip.begin();
      setColor(148, 0, 211);
  strip.show();
}

void receiveEvent(int bytes) {
  // Read the last number sent (0-255)
  lastValue = Wire.read();
  Serial.println(lastValue);
}

void loop() {
  //Add Pre-Programmed cases here:
  switch (lastValue) {
    case 0:
      setColor(0, 255, 0);
      break;
    case 1:
      setColor(255, 0, 0);
      break;
    case 2:
      setColor(30, 255, 30);
      break;
    case 3:
      setColor(0, 0, 255);
      break;
    case 4:
      setColor(255, 204, 0);
      break;
    // ENABLE
    case 6:
      setColor(0, 0, 0);
      delay(225);
      setColor(255, 15, 0);
      delay(225);
      break;
    // DISABLE
    case 7:
      setColor(255, 25, 0);
      break;
    default:
      setColor(0, 255, 0);
      break;
  }
}

void setColor(int red, int green, int blue) {
  for(int i = 0; i < PIXEL_COUNT; i++){
    strip.setPixelColor(i, red, green, blue);
  }
  
  strip.show();
}
