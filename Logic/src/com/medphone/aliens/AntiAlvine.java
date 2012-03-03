package com.medphone.aliens;

class AntiAlvine extends AliensProcess {

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
