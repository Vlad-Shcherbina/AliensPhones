package com.medphone.aliens;

public class RightArmDemo extends AliensProcess {

	public String get_name() {
		return "RightArmDemo";
	}

	public void event() {
		add_notification("Пуля просвистела в дюйме от моего правого плеча.");
	}

}
