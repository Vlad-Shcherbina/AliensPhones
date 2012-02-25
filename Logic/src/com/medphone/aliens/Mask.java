package com.medphone.aliens;

public class Mask extends AliensProcess {

	public String get_name() {
		return "Mask";
	}

	public void event() {
		if (a().air != a().MASK)
			add_notification("Я надел{/а} полную маску и выш{ел/ла} в коридоры.");
		else
			add_notification("Я уже в полной маске.");
		a().air = a().MASK;		
	}

}
