package com.medphone.aliens;

public class YellowDemo extends AliensProcess {

	public String getName() {
		return "YellowDemo";
	}

	public void event() {
		addNotification("Я услышал{/а} тихое шипение.");
	}

}
