package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class Ehinospore extends AliensProcess {

	public String getName() {
		return "Ehinospore";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("Таблетка со свежим мятным вкусом.");
			stage = 1;
			schedule(5);
			break;
		case 1:
			stage = 2;
			// at this stage affests weakness and bleeding
			schedule(60);
			break;
		}
	}

}
