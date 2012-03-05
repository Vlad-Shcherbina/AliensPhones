package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class WarfareenSalicylat extends AliensProcess {

	public String getName() {
		return "WarfareenSalicylat";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("Укол!");
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
