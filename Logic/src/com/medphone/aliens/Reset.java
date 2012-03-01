package com.medphone.aliens;

public class Reset extends AliensProcess {

	public String get_name() {
		return "Reset";
	}

	public void event() {
		a().reset();
		add_notification("Состояние сброшено");
	}

}
