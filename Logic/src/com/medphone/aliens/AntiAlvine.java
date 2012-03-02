package com.medphone.aliens;

class AntiAlvine extends AliensProcess {

	public
	String get_name() {
		return "AntiAlvine";
	}

	public void event() {
		if (stage == 0) {
			add_notification("Укол!");
			stage = 1;
			schedule(30);
		}
	}

}
