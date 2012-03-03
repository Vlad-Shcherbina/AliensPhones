package com.medphone.aliens;

public class BlueDemo extends AliensProcess {

	public String getName() {
		return "BlueDemo";
	}

	public void event() {
		addNotification(
				"Я услышал{/а} аццкое шипение и почувствовал{/а} " +
				"кислый привкус во рту.");
	}

}
