package com.medphone.aliens;

public class DamageLungs extends AliensProcess {

	public String getName() {
		return "DamageLungs";
	}

	public void event() {
		a().lungs -= 22;
		addNotification("Лёгких стало "+a().lungs);

	}

}
