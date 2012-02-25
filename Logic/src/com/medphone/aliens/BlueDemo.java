package com.medphone.aliens;

public class BlueDemo extends AliensProcess {

	public String get_name() {
		return "BlueDemo";
	}

	public void event() {
		add_notification(
				"Я услышал{/а} аццкое шипение и почувствовал{/а} " +
				"кислый привкус во рту.");
	}

}
