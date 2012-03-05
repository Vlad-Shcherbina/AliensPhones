package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class Perftoran extends AliensProcess {

	public String getName() {
		return "Perftoran";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("Ко мне присоединён пакет с красной жидкостью.");
			// fall through
		case 1:
		case 2:
		case 3:
		case 4:
			stage++;
			a().blood += 100;
			schedule(1);
			break;
		case 5:
			addNotification("Пакет с красной жидкостью отпал.");
			stage++;
			schedule(25);
			break;
		}
	}

}
