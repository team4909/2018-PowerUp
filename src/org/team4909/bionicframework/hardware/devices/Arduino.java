package org.team4909.bionicframework.hardware.devices;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

int a;  //Case 0 (First LED Strip)
a = 0;

int b;  //Case 1 (Second LED Strip)
b = 2;

int c;  //Case 2 (Third LED Strip)
c = 3;

int d;  //Case 3 (Fourth LED Strip)
d = 4;

int e;  //Case 4 (Fifth LED Strip)
e = 5;

int f;  //Case 5 (Sixth LED Strip)
f = 6;

int g;  //Case 6 (Seventh LED Strip)
g = 7;

int h;  //Case 7 (Eighth LED Strip)
h = 8;

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
		 
         public static int disableArray[] = {"a","b","c","d"};
         
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