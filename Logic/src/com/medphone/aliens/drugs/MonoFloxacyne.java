package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class MonoFloxacyne extends AliensProcess {

	public String getName() {
		return "MonoFloxacyne";
	}

	public void event() {
		if (stage == 0) {
			addNotification("Неприятный укол!");
			stage = 1;
			schedule(5);
		}
		else if (stage <= 10) {
			a().sepsis = false;
			a().kidneys -= 6;
			stage++;
			schedule(1);
		}
	}

}
