#include <Wire.h>
//Set PWM ports
int RED = 9;
int GREEN = 10;
int BLUE = 11;

int lastValue = 0;
int deviceNumber = 4;

void setup() {
  //Set Outputs (Needs to be modified to work with LED strip)
  pinMode(RED,OUTPUT);
  pinMode(GREEN,OUTPUT);
  pinMode(BLUE,OUTPUT);
	
  //Start I2C as slave
  Wire.begin(deviceNumber);
  Wire.onReceive(receiveEvent);
}

void receiveEvent(int bytes){
  //Read the last number sent (0-255)
  lastValue = Wire.read();
}

void loop() {
  //Add Pre-Programmed cases here:
  switch(lastValue){
    case 0:
    	setColor(0,255,0);
    	break;
    case 1:
    	setColor(255,0,0);
	break;
    case 2:
   	setColor(30,255,30);
    	break;
    case 3:
    	setColor(0,0,255);
    	break;
    case 4:
    	setColor(255,204,0);
   	break;
    //ENABLE
    case 6:
    	setColor(255, 25, 0);
    	delay(200);
    	setColor(0,0,0);
    	delay(200);
    	break;
    //DISABLE
    case 7:
    	setColor(255,25,0);
    	break;
    default:
    	setColor(0,255,0);
    	break;
  }

}
void setColor(int red, int green, int blue){
  //Write the color to the board
  analogWrite(RED,red);
  analogWrite(GREEN, green);
  analogWrite(BLUE, blue);
}
