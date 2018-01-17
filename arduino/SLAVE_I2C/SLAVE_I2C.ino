#include <Adafruit_NeoPixel.h>
#include <Wire.h>

#define PIXEL_PIN    6
#define PIXEL_COUNT 64
Adafruit_NeoPixel strip = Adafruit_NeoPixel(PIXEL_COUNT, PIXEL_PIN, NEO_GRB + NEO_KHZ800);

// Set Configuration
int lastValue = 0;
int deviceNumber = 4;

int redValue0 = 0;
int greenValue0 = 255;
int blueValue0 = 0;
//Case 0 (First LED Strip) RGB Values

int redValue1 = 255;
int greenValue1 = 0;
int blueValue1 = 0;
//Case 1 (Second LED Strip) RGB Values

int redValue2 = 30;
int greenValue2 = 255;
int blueValue2 = 30;
//Case 2 (Third LED Strip) RGB Values

int redValue3 = 0;
int greenValue3 = 0;
int blueValue3 = 255;
//Case 3 (Fourth LED Strip) RGB Values

int redValue4 = 255;
int greenValue4 = 204;
int blueValue4 = 0;
//Case 4 (Fifth LED Strip) RGB Values

int redValue5 = 0;
int greenValue5 = 50;
int blueValue5 = 255;
//Case 5 (Sixth LED Strip) RGB Values

int redValue6 = 255;
int greenValue6 = 15;
int blueValue6 = 0;
//Case 6 (Seventh LED Strip) RGB Values

int redValue7 = 255;
int greenValue7 = 25;
int blueValue7 = 0;
//Case 7 (Eighth LED Strip) RGB Values

void setup() {
  // Start I2C as slave
  Wire.begin(deviceNumber);
  Wire.onReceive(receiveEvent);

  Serial.begin(9600);

  // Initialize LED Strip
  strip.begin();
      setColor(0, 255, 0);
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
    case disableArray:
      setColor(0,0,0);
      break;
          
    case 0:
      setColor(redValue0, greenValue0, blueValue0);
      break;
    case 1:
      setColor(redValue1, greenValue1, blueValue1);
      break;
    case 2:
      setColor(redValue2, greenValue2, blueValue2);
      break;
    case 3:
      setColor(redValue3, greenValue3, blueValue3);
      break;
    case 4:
      setColor(redValue4, greenValue4, blueValue4);
      break;
    case 5:
      setColor(redValue5, greenValue5, blueValue5);
    // ENABLE
    case 6:
      setColor(0, 0, 0);
      delay(225);
      setColor(redValue6, greenValue6, blueValue6);
      delay(225);
      break;
    // DISABLE
    case 7:
      setColor(redValue7, greenValue7, blueValue7);
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
