package com.medphone.aliens;

class Cross extends AliensProcess {

	public String getName() {
		return "Cross";
	}

	public void event() {
		a().male = !a().male;
		if (a().male)
			addNotification("Я мужчина.");
		else
			addNotification("Я женщина.");
	}
	
}
