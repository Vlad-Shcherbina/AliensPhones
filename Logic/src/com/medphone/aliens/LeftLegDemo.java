package com.medphone.aliens;

public class LeftLegDemo extends AliensProcess {

	public String getName() {
		return "LeftLegDemo";
	}

	public void event() {
		addNotification("Пуля чудом просвистела в дюйме от моей левой ноги.");
	}

}
