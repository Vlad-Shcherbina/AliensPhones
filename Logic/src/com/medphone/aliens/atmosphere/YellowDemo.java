package com.medphone.aliens.atmosphere;

import com.medphone.aliens.AliensProcess;

public class YellowDemo extends AliensProcess {

	public String getName() {
		return "YellowDemo";
	}

	public void event() {
		addNotification("Я услышал{/а} тихое шипение.");
	}

}
