package com.medphone.aliens;

public class Reset extends AliensProcess {

	public String getName() {
		return "Reset";
	}

	public void event() {
		a().reset();
		addNotification("Состояние сброшено");
	}

}
