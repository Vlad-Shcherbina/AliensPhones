package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class Erpoiteen extends AliensProcess {

	public String getName() {
		return "Erpoiteen";
	}

	public void event() {
		if (stage == 0) {
			addNotification("Еле ощутимый укол.");
			stage = 1;
			schedule(60);
		}
	}

}
