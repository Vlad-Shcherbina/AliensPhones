package com.medphone.aliens;

public class YellowDemo extends AliensProcess {

	public String get_name() {
		return "YellowDemo";
	}

	public void event() {
		add_notification("Я услышал{/а} тихое шипение.");
	}

}
