package com.medphone.aliens;

public class RightArmDemo extends AliensProcess {

	public String getName() {
		return "RightArmDemo";
	}

	public void event() {
		addNotification("Пуля просвистела в дюйме от моего правого плеча.");
	}

}
