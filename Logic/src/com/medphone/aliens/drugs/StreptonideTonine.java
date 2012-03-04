package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class StreptonideTonine extends AliensProcess {

	public String getName() {
		return "StreptonideTonine";
	}

	public void event() {
		addNotification("Пронзительный укол!");
		a().kidneys -= 55;
	}

}
