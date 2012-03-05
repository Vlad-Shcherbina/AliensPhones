package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class Cyprostane extends AliensProcess {

	public String getName() {
		return "Cyprostane";
	}

	public void event() {
		if (stage == 0) {
			addNotification("Я проглотил{/а} капсулу.");
			stage = 1;
			schedule(30);
		}
		else if (stage <= 30) {
			a().sepsis = false;
			stage++;
			schedule(1);
		}
	}

}
