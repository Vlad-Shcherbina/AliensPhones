package com.medphone.aliens;

class Antialvin extends AliensProcess {

	public
	String get_name() {
		return "Antialvin";
	}

	public void event() {
		if (stage == 0) {
			add_notification("Укол!");
			stage = 1;
			schedule(30);
		}
	}

}
