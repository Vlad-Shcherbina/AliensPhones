package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class FybrinogenLocalTorso extends AliensProcess {

	public String getName() {
		return "FybrinogenLocalTorso";
	}

	public void event() {
		addNotification("Укол!");
	}
}
