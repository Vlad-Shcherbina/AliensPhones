package com.medphone.aliens.drugs;


public class Citosolute extends com.medphone.aliens.AliensProcess {

	public String getName() {
		return "Citosolute";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("Бодрый укол!");
			stage = 1;
			schedule(4);
			break;
		case 1:
			stage = 2;
			schedule(21);
			break;
		}
	}
}
