package org.team4909.bionicframework.hardware.devices;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

int a = 0;  //Case 0 (First LED Strip)
int b = 1;  //Case 1 (Second LED Strip)
int c = 2;  //Case 2 (Third LED Strip)
int d = 3;  //Case 3 (Fourth LED Strip)
int e = 4;  //Case 4 (Fifth LED Strip)
int f = 5;  //Case 5 (Sixth LED Strip)
int g = 6;  //Case 6 (Seventh LED Strip)
int h = 7;  //Case 7 (Eighth LED Strip)

int redValue = 0;  //Red Value
int greenValue = 255;  //Green Value
int blueValue = 0;  //Blue Value

public class Arduino {
	 public static enum State {
		 disabled(a),
         disabled(b),
         disabled(c),
         disabled(d),
         enabled(e),
         enabled(f),
         enabled(g),
         enabled(h);
         int disableArray = [a,b,c,d]  //Array of LEDs to turn off
         int enableArray = [e,f,g,h]  //Array of LEDs to turn on
             
         public Command send(State state) {
             case disableArray:
               setColor(0,0,0); //Turn off all LEDs in disableArray
             
             case enableArray:
               setColor(redValue,greenValue,blueValue);  //Set RGB value of LEDs in enableArrray
         }
		 
		 public final int signal;
		 State(int signal) {
			 this.signal = signal;
		 }
	}
	 
	private I2C i2c;
	
	public Arduino(int address) {
		i2c = new I2C(I2C.Port.kOnboard, address);
	}
	
	public void sendSignal(State state) {
		byte[] toSend = { (byte) state.signal };
		i2c.transaction(toSend, 1, null, 0);
	}
	
	public Command send(State state) {
		return new SendCommand(state);
	}
	
	private class SendCommand extends InstantCommand {
		State state;
		
		public SendCommand(State state){
			this.state = state;
		}
		
		protected void initialize(){
			sendSignal(state);
		}
	}
}