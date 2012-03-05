package com.medphone.aliens.drugs;

import com.medphone.Process;
import com.medphone.aliens.AliensProcess;

public class FybrinogenLocalLeftArm extends AliensProcess {

	protected String getMemberName() {
		return "LeftArm";
	}
	
	public String getName() {
		return "FybrinogenLocal"+getMemberName();
	}
	

	public void event() {
		switch (stage) {
		case 0:
			addNotification("Укол!");
			stage = 1;
			schedule(2);
			break;
		case 1:
			Process p = a().findProcess(getMemberName());
			if (p != null) {
				p.setAttr("bleedingStopped", "zzz");
				important();
			}
		}
	}

}
