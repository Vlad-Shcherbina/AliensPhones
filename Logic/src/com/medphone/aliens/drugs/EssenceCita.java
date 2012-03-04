package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class EssenceCita extends AliensProcess {

	public String getName() {
		return "EssenceCita";
	}

	public void event() {
		if (stage == 0)
			addNotification("Укол!");
		if (stage < 60) {
			if (a().liver < 480)
				a().liver += 5;
			stage++;
			schedule(1);
		}
	}

}
