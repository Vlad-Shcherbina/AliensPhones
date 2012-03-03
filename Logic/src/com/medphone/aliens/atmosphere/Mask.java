package com.medphone.aliens.atmosphere;

import com.medphone.aliens.AliensProcess;

public class Mask extends AliensProcess {

	public String getName() {
		return "Mask";
	}

	public void event() {
		if (a().air != a().MASK)
			addNotification("Я надел{/а} полную маску и выш{ел/ла} в коридоры.");
		else
			addNotification("Я уже в полной маске.");
		a().air = a().MASK;		
	}

}
