#include <Wire.h>
int x = 0;
void setup() {
  //Start I2C as Master
  Wire.begin();
}

void loop() {
  Wire.beginTransmission(9); //Match number to the other board.
  Wire.write(x);
  Wire.endTransmission();
  //Loop however you want for testing purposes
  x++;
  if(x>6) x = 0;
  delay(500);

}
