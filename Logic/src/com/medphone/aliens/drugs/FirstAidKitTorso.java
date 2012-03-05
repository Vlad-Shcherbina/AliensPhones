package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class FirstAidKitTorso extends AliensProcess {

	public String getName() {
		return "FirstAidKitTorso";
	}

	public void event() {
		addNotification("Пластырь-пакет плохо приклеился и тут же отвалился.");
	}

}
