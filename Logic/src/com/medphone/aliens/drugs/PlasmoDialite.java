package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class PlasmoDialite extends AliensProcess {

	public String getName() {
		return "PlasmoDialite";
	}

	public void event() {
		if (stage == 0)
			addNotification("Укол!");
		if (stage < 60) {
			a().kidneys += 5;
			stage++;
			schedule(1);
		}		
	}

}
