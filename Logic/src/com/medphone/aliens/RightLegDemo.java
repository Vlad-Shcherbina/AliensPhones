package com.medphone.aliens;

public class RightLegDemo extends AliensProcess {

	public String get_name() {
		return "RightLegDemo";
	}

	public void event() {
		add_notification("Пуля просвистела в дюйме от моей правой ноги.");
	}

}
