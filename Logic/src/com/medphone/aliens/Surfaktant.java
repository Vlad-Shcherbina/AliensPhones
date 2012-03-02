package com.medphone.aliens;

class Surfaktant extends AliensProcess {

	public
	String get_name() {
		return "Surfaktant";
	}

	public
	void event() {
		if (stage == 0)
			add_notification("Ай, укол!");
		if (stage < 60) {
			a().lungs += 1;
			stage += 1;
			schedule(1);
		}
	}

}
