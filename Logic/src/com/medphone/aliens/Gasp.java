package com.medphone.aliens;

public class Gasp extends AliensProcess {

	public String getName() {
		return "Gasp";
	}

	public void event() {
		if (a().air != a().GASP)
			addNotification("Я оказал{ся/ась} в коридоре без защиты дыхания.");
		else
			addNotification("Я уже в коридорах без защиты дыхания.");
		a().air = a().GASP;
	}

}
