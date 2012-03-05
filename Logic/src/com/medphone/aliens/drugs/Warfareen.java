package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class Warfareen extends AliensProcess {

	public String getName() {
		return "Warfareen";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("Я разжевал{/а} таблетку.");
			stage = 1;
			schedule(5);
			break;
		case 1:
			stage = 2;
			schedule(15);
			break;
		}
	}

}
