package com.medphone.aliens;

public class RightLegDemo extends AliensProcess {

	public String getName() {
		return "RightLegDemo";
	}

	public void event() {
		addNotification("Пуля просвистела в дюйме от моей правой ноги.");
	}

}
