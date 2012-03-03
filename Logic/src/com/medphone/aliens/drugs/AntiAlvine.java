package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class AntiAlvine extends AliensProcess {

	public
	String getName() {
		return "AntiAlvine";
	}

	public void event() {
		if (stage == 0) {
			addNotification("Укол!");
			stage = 1;
			schedule(30);
		}
	}

}
