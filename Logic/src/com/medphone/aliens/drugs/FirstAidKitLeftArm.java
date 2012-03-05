package com.medphone.aliens.drugs;

import com.medphone.Process;
import com.medphone.aliens.AliensProcess;

public class FirstAidKitLeftArm extends AliensProcess {

	protected String getMemberName() {
		return "LeftArm";
	}
	
	protected String getMemberText() {
		return "на левой руке";
	}

	public String getName() {
		return "FirstAidKit"+getMemberName();
	}
	
	public void event() {
		switch (stage) {
		case 0:
			addNotification("Пластырь-пакет налеплен.");
			setAttr("status", "* у меня "+getMemberText()+" пластырь-пакет");
			stage = 1;
			schedule(1);
			break;
		case 1:
			Process p = a().findProcess(getMemberName());
			if (p != null) {
				p.setAttr("bleedingStopped", "zzz");
				important();
			}
			stage = 2;
			schedule(15);
			break;
		case 2:
			addNotification("Пластырь-пакет отпал.");
		}
	}

}
