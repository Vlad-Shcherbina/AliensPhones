package com.medphone.aliens;

import com.medphone.Process;

public class LeftArm extends AliensProcess {

	public String getName() {
		return "LeftArm";
	}
	
	protected String getMessage() {
		return "Меня ранило в левую руку!";
	}

	public void event() {
		switch (stage) {
		case 0:
			Process prev = a().findProcess(getName());
			if (prev != null) {
				if (a().rand(2) == 0) {
					addNotification("* Мне разворотило старую рану");
					prev.stage = 2;
					prev.setAttr("bleeding", "3");
					prev.setAttr("pain"+getName(), "3");
				}
				else
					addNotification("На этот раз меня только оцарапало.");
				break;
			}
			
			addNotification("* "+getMessage());
			if (a().rand(10) < 6)
				setAttr("pain"+getName(), "1");
			else
				setAttr("pain"+getName(), "2");
			
			if (a().rand(2) == 0) {
				stage = 1;
				setAttr("bleeding", "1");
			}
			else {
				stage = 2;
				setAttr("bleeding", "3");
			}
			
			schedule(5);
			break;
		case 1:
		case 2:
			if (a().rand(1000) < 80)
				a().sepsis = true;
			
			if (getAttr("pain"+getName()) == "3")
				setAttr("pain"+getName(), "2");
			
			schedule(5); // ad infinitum until cured
			break;
		}
	}

}
