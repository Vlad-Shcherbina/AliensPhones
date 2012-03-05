package com.medphone.aliens.drugs;



public class UrcaineTorso extends com.medphone.aliens.AliensProcess {

	public String getName() {
		return "UrcaineTorso";
	}

	public void event() {
		addNotification("Укол, практически неощутимый.");
	}

}
