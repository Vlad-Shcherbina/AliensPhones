package com.medphone.aliens;

public class Torso extends AliensProcess {

	public String getName() {
		return "Torso";
	}

	public void event() {
		// TODO: other wounds
		a().schedule(new LiverWound(), 0);
	}

}
