package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class BenzylAlienat extends AliensProcess {

	public String getName() {
		return "BenzylAlienat";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("Я проглотил{/а} таблетку.");
			stage = 1;
			schedule(15);
			break;
		case 1:
			stage = 2;
			schedule(90);
			break;
		}
	}

}
