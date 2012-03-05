package com.medphone.aliens;

public class UnbearablePain extends AliensProcess {

	public String getName() {
		return "UnbearablePain";
	}

	public void event() {
		a().cancelProcess(getName());
		switch (stage) {
		case 0:
			addNotification("Невыносимая боль!");
			setAttr("unconscious", "zzz");
			stage = 1;
			schedule(5);
			break;
		case 1:
			a().painTolerance = 5;
		}
	}

}
