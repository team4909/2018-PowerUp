#include <Adafruit_NeoPixel.h>
#include <Wire.h>

#define PIXEL_PIN    5
#define PIXEL_COUNT 64

Adafruit_NeoPixel strip = Adafruit_NeoPixel(PIXEL_COUNT, PIXEL_PIN, NEO_GRB + NEO_KHZ800);
//Adafruit_NeoPixel strip_b = Adafruit_NeoPixel(30, 5, NEO_GRB + NEO_KHZ800);

int deviceNumber = 4;
int LED = 0;
void setup() {
  // Start I2C as slave
  Wire.begin(deviceNumber);
  Wire.onReceive(receiveEvent);
  Serial.begin(9600);
  // Initialize LED Strip
  strip.begin();
  strip.setBrightness(135);
//    setColor(148, 0, 211);
  strip.show();
}

void receiveEvent() {
  // Read the last number sent (0-30)
 LED = Wire.read();
  Serial.print(LED);
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
  WheelPos = 0 + WheelPos;
  if(WheelPos > 85) {
    return strip.Color(255 - WheelPos * 3, 0, WheelPos * 3);
  }
  if(WheelPos > 170) {
    WheelPos += 85;
    return strip.Color(0, WheelPos * 3, 255 - WheelPos * 3);
  }
  WheelPos += 170;
  return strip.Color(WheelPos * 3, 255 - WheelPos * 3, 0);
}

void Fire(int Cooling, int Sparking, int SpeedDelay) {
  static byte heat[30]; 
  int cooldown; 
  
  // Step 1.  Cool down every cell a little
  for( int i = 0; i < 30; i++) {
    cooldown = random(0, ((Cooling * 10) / 30) + 2); 
    
    if(cooldown>heat[i]) {
      heat[i]=0;
    } else {
      heat[i]=heat[i]-cooldown;
    }
  }
  
  // Step 2.  Heat from each cell drifts 'up' and diffuses a little
  for( int k= 30 - 1; k >= 2; k--) {
    heat[k] = (heat[k - 1] + heat[k - 2] + heat[k - 2]) / 3;
  }
    
  // Step 3.  Randomly ignite new 'sparks' near the bottom
  if( random(255) < Sparking ) {
    int y = random(7);
    heat[y] = heat[y] + random(160,255);
    //heat[y] = random(160,255);
  }

  // Step 4.  Convert heat to LED colors  
  for( int j = 0; j < 30; j++) {
    setPixelHeatColor(j, heat[j] );
  }

  showStrip();
  delay(SpeedDelay);
}

void setPixelHeatColor (int Pixel, byte temperature) {
  // Scale 'heat' down from 0-255 to 0-191
  byte t192 = round((temperature/255.0)*191);
 
  // calculate ramp up from
  byte heatramp = t192 & 0x3F; // 0..63
  heatramp <<= 2; // scale up to 0..252
 
  // figure out which third of the spectrum we're in:
  if( t192 > 0x80) {                     // hottest
    setPixel(Pixel, 255, 255, heatramp);
  } else if( t192 > 0x40 ) {             // middle
    setPixel(Pixel, 255, heatramp, 0);
  } else {                               // coolest
    setPixel(Pixel, heatramp, 0, 0);
  }
}


void showStrip() {
 #ifdef ADAFRUIT_NEOPIXEL_H 
   // NeoPixel
   strip.show();
 #endif
 #ifndef ADAFRUIT_NEOPIXEL_H
   // FastLED
   FastLED.show();
 #endif
}

void setPixel(int Pixel, byte red, byte green, byte blue) {
 #ifdef ADAFRUIT_NEOPIXEL_H 
   // NeoPixel
   strip.setPixelColor(Pixel, strip.Color(red, green, blue));
 #endif
 #ifndef ADAFRUIT_NEOPIXEL_H 
   // FastLED
   leds[Pixel].r = red;
   leds[Pixel].g = green;
   leds[Pixel].b = blue;
 #endif
}

void greenWhite(){
  for(int i = 0; i <= 40; i++){
    int i1 = i-1;
    int i2 = i;
    int i3 = i+1; 
    strip.setPixelColor(i1, 0, 255, 0);
    strip.setPixelColor(i2, 0, 255, 0);
    strip.setPixelColor(i3, 0, 255, 0);
    
    int a1 = i+6;
    int b1 = i-6;

    strip.setPixelColor(a1, 255, 255, 255);
    strip.setPixelColor(b1, 255, 255, 255);
    strip.show();
     
    delay(100);
  };
}

int color(int r, int g, int b){
  for(int i=0; i<30; i++){
    strip.setPixelColor(i, r, g, b);
    strip.show();  
  }
}

int randUp(int wait){
  int r=random(0, 255);
  int g=random(0, 255);
  int b=random(0, 255);
  for(int i=0; i<30; i++){
    strip.setPixelColor(i, r, g, b);
    strip.show();  
    delay(wait);
  }
}

bool ran = false;

int climbing(int wait){
  if (ran==false){
     for(int i=0; i<30; i++){
    strip.setPixelColor(i, 25, 190, 6);
    strip.show();  
    delay(wait);
    ran = true;
  }
}
}

int pong(int wait){
  for(int i=0; i<30; i++){
    strip.setPixelColor(i, 25, 190, 6);
    strip.setPixelColor(i-6, 0, 0, 0);
    strip.show();
     if (i!=0 and i!=30){
      delay(wait);
    }  
  }
  for(int i=30; i>0; i=i-1){
    strip.setPixelColor(i, 25, 190, 6);
    strip.setPixelColor(i+6, 0, 0, 0);
    strip.show();  
    if (i!=0 and i!=30){
      delay(wait);
    }
  }
}

void loop() {
  if (LED == 1){
    // A rainbow line goes up
    rainbow1(25); 
  }
  else if (LED == 2){
    //Whole strip is rainbow
    rainbow2(0.7);
  }
  else if (LED == 3){
    //Looks like fire
    Fire(55,120,15);
  }
  else if (LED == 4){
    //A green line goes up with a white background
    greenWhite();
  }
  else if (LED==5){
    //Sets all pixels to green
    color(25, 190, 6);
  }
  else if (LED==6){
    //Sets all pixels to blue
    color(0, 0, 255);
  }
  else if (LED==7){
    //Sets all pixels to red
    color(255, 0, 0);
  }
  else if (LED==8){
    //Every pixel turns a random color
    randUp(100);
  }
  else if(LED==9){
    //Every pixel slowly turns green
    climbing(100);
  }
  else if (LED==10){
    //Green Line goes up and down
    pong(25);
  }
}
