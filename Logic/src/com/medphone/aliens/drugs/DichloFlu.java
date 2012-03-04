package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class DichloFlu extends AliensProcess {

	public String getName() {
		return "DichloFlu";
	}
	
	public void event() {
		switch (stage) {
		case 0:
			addNotification("Мерзкая на вкус таблетка");
			stage = 1;
			schedule(15);
			break;
		case 1:
			stage = 2;
			schedule(60);
			break;
		case 2:
			stage = 3;
			schedule(120);
			break;
		}
	}

}
