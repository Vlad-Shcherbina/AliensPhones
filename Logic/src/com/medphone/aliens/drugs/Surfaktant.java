package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class Surfaktant extends AliensProcess {

	public
	String getName() {
		return "Surfaktant";
	}

	public
	void event() {
		if (stage == 0)
			addNotification("Ай, укол!");
		if (stage < 60) {
			a().lungs += 1;
			stage += 1;
			schedule(1);
		}
	}

}
