package com.medphone.aliens;

public class LeftLegDemo extends AliensProcess {

	public String get_name() {
		return "LeftLegDemo";
	}

	public void event() {
		add_notification("Пуля чудом просвистела в дюйме от моей левой ноги.");
	}

}
