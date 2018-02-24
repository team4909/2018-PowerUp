#include <Adafruit_NeoPixel.h>
#include <Adafruit_NeoMatrix.h>
#include <Adafruit_GFX.h> 
#include <Wire.h>

#define PIXEL_PIN    6
#define PIXEL_COUNT 64

Adafruit_NeoPixel strip_b = Adafruit_NeoPixel(30, 5, NEO_GRB + NEO_KHZ800);

Adafruit_NeoPixel strip = Adafruit_NeoPixel(PIXEL_COUNT, PIXEL_PIN, NEO_GRB + NEO_KHZ800);

// Set Configuration
int lastValue = 0;
int deviceNumber = 4;

void setup() {
  // Start I2C as slave
  Wire.begin(deviceNumber);
  Wire.onReceive(receiveEvent);

  Serial.begin(9600);
    
  strip_b.begin();
  strip_b.setBrightness(255);
  strip_b.show();

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
    
    if(arduinoElevatorPosition > 0) {
  if(arduinoElevatorPosition <= 1) {
    for(int n=0; n <= 29; n++) {
    strip_b.setPixelColor(n, 215, 40, 50);
    strip_b.show();
   }}}
 
  if(arduinoElevatorPosition > 1) {
    if(arduinoElevatorPosition <= 2) {
      for(int n=1; n <= 29; n++) {
        for(int i=0; i <= 0; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
     }}}}
  
  if(arduinoElevatorPosition > 2){
    if(arduinoElevatorPosition <= 3) {
      for(int n=2; n <= 29; n++) {
        for(int i=0; i <= 1; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
     }}}}
  
  if(arduinoElevatorPosition > 3){
    if(arduinoElevatorPosition <= 4) {
      for(int n=3; n <= 29; n++) {
        for(int i=0; i <= 2; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
     }}}}
  
  if(arduinoElevatorPosition > 4){
    if(arduinoElevatorPosition <= 5) {
      for(int n=4; n <= 29; n++) {
        for(int i=0; i <= 3; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
     }}}}
  
  if(arduinoElevatorPosition > 5){
    if(arduinoElevatorPosition <= 6) {
      for(int n=5; n <= 29; n++) {
       for(int i=0; i <= 4; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
     }}}}
  
  if(arduinoElevatorPosition > 6){
    if(arduinoElevatorPosition <= 7) {
      for(int n=6; n <= 29; n++) {
        for(int i=0; i <= 5; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
     }}}}
     
  if(arduinoElevatorPosition > 7){
    if(arduinoElevatorPosition <= 8) {
      for(int n=7; n <= 29; n++) {
        for(int i=0; i <= 6; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
     }}}}
  
  if(arduinoElevatorPosition > 8){
    if(arduinoElevatorPosition <= 9) {
      for(int n=8; n <= 29; n++) {
        for(int i=0; i <= 7; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}
    
  if(arduinoElevatorPosition > 9){
    if(arduinoElevatorPosition <= 10) {
      for(int n=9; n <= 29; n++) {
        for(int i=0; i <= 8; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

  if(arduinoElevatorPosition > 10){
    if(arduinoElevatorPosition <= 11) {
      for(int n=10; n <= 29; n++) {
        for(int i=0; i <= 9; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

  if(arduinoElevatorPosition > 11){
    if(arduinoElevatorPosition <= 12) {
      for(int n=11; n <= 29; n++) {
        for(int i=0; i <= 10; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

  if(arduinoElevatorPosition > 12){
    if(arduinoElevatorPosition <= 13) {
      for(int n=12; n <= 29; n++) {
        for(int i=0; i <= 11; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

  if(arduinoElevatorPosition > 13){
    if(arduinoElevatorPosition <= 14) {
      for(int n=13; n <= 29; n++) {
        for(int i=0; i <= 12; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

  if(arduinoElevatorPosition > 14){
    if(arduinoElevatorPosition <= 15) {
      for(int n=14; n <= 29; n++) {
        for(int i=0; i <= 13; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

  if(arduinoElevatorPosition > 15){
    if(arduinoElevatorPosition <= 16) {
      for(int n=15; n <= 29; n++) {
        for(int i=0; i <= 14; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

  if(arduinoElevatorPosition > 16){
    if(arduinoElevatorPosition <= 17) {
      for(int n=16; n <= 29; n++) {
        for(int i=0; i <= 15; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

  if(arduinoElevatorPosition > 17){
    if(arduinoElevatorPosition <= 18) {
      for(int n=17; n <= 29; n++) {
        for(int i=0; i <= 16; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

  if(arduinoElevatorPosition > 18){
    if(arduinoElevatorPosition <= 19) {
      for(int n=18; n <= 29; n++) {
        for(int i=0; i <= 17; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

  if(arduinoElevatorPosition > 19){
    if(arduinoElevatorPosition <= 20) {
      for(int n=19; n <= 29; n++) {
        for(int i=0; i <= 18; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

  if(arduinoElevatorPosition > 20){
    if(arduinoElevatorPosition <= 21) {
      for(int n=20; n <= 29; n++) {
        for(int i=0; i <= 19; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

  if(arduinoElevatorPosition > 21){
    if(arduinoElevatorPosition <= 22) {
      for(int n=21; n <= 29; n++) {
        for(int i=0; i <= 20; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

  if(arduinoElevatorPosition > 22){
    if(arduinoElevatorPosition <= 23) {
      for(int n=22; n <= 29; n++) {
        for(int i=0; i <= 21; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

   if(arduinoElevatorPosition > 23){
    if(arduinoElevatorPosition <= 24) {
      for(int n=23; n <= 29; n++) {
        for(int i=0; i <= 22; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}
    
   if(arduinoElevatorPosition > 24){
    if(arduinoElevatorPosition <= 25) {
      for(int n=24; n <= 29; n++) {
        for(int i=0; i <= 23; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

   if(arduinoElevatorPosition > 25){
    if(arduinoElevatorPosition <= 26) {
      for(int n=25; n <= 29; n++) {
        for(int i=0; i <= 24; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

   if(arduinoElevatorPosition > 26){
    if(arduinoElevatorPosition <= 27) {
      for(int n=26; n <= 29; n++) {
        for(int i=0; i <= 25; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

   if(arduinoElevatorPosition > 27){
    if(arduinoElevatorPosition <= 28) {
      for(int n=27; n <= 29; n++) {
        for(int i=0; i <= 26; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}
    
   if(arduinoElevatorPosition > 28){
    if(arduinoElevatorPosition <= 29) {
      for(int n=28; n <= 29; n++) {
        for(int i=0; i <= 27; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

   if(arduinoElevatorPosition > 29){
    if(arduinoElevatorPosition < 30) {
      for(int n=29; n <= 29; n++) {
        for(int i=0; i <= 28; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.setPixelColor(n, 215, 40, 50);
     strip_b.show();
    }}}}

   if(arduinoElevatorPosition = 30){
    for(int i=0; i <= 29; i++) {
     strip_b.setPixelColor(i, 40, 215, 160);
     strip_b.show();
  }}
}

void setColor(int red, int green, int blue) {
  for(int i = 0; i < PIXEL_COUNT; i++){
    strip.setPixelColor(i, red, green, blue);
  }
  
  strip.show();
}
