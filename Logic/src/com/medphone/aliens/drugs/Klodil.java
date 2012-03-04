package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class Klodil extends AliensProcess {

	public String getName() {
		return "Klodil";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("Болезненный укол!");
			// no break
		case 1:
		case 2:
		case 3:
		case 4:
			stage ++;
			a().sepsis = false;
			schedule(1);
			a().liver -= 10;
			break;
		case 5:
			a().cancelProcess(getName());
			stage++;
			setAttr("status", "(ic) меня тошнит");
			a().liver -= 75;
			schedule(14);
			break;
		}

	}

}
