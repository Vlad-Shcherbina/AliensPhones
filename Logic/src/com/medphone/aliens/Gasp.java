package com.medphone.aliens;

public class Gasp extends AliensProcess {

	public String get_name() {
		return "Gasp";
	}

	public void event() {
		if (a().air != a().GASP)
			add_notification("Я оказал{ся/ась} в коридоре без защиты дыхания.");
		else
			add_notification("Я уже в коридорах без защиты дыхания.");
		a().air = a().GASP;
	}

}
