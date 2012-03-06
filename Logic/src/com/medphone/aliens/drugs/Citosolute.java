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
			schedule(30);
			break;
		}
	}
}
