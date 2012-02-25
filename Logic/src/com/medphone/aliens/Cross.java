package com.medphone.aliens;

class Cross extends AliensProcess {

	public String get_name() {
		return "Cross";
	}

	public void event() {
		a().male = !a().male;
		if (a().male)
			add_notification("Я мужчина.");
		else
			add_notification("Я женщина.");
	}
	
}
