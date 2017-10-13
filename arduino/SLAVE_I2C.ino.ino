#include <Wire.h>
//Set PWM ports
int RED = 9;
int GREEN = 10;
int BLUE = 11;
int x;
void setup() {
  //Set Outputs (Needs to be modified to work with LED strip)
  pinMode(RED,OUTPUT);
  pinMode(GREEN,OUTPUT);
  pinMode(BLUE,OUTPUT);
  //Start I2C as slave (With device number as 9)
  Wire.begin(9);
  Wire.onReceive(receiveEvent);
}

void receiveEvent(int bytes){
  //Read the last number sent (0-255)
  x = Wire.read();
}

void loop() {
  //Add Pre-Programmed cases here:
  switch(x){
    case 0:
    setColor(255,0,0);
    break;
    default:
    setColor(255,255,255);
    break;
  }

}
void setColor(int red, int green, int blue){
  //Write the color to the board
  analogWrite(RED,red);
  analogWrite(GREEN, green);
  analogWrite(BLUE, blue);
}
