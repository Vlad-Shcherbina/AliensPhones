package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class Friz extends AliensProcess {

	public String getName() {
		return "Friz";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("Леденящий укол!");
			setAttr("status", "любой свет режет глаза");
			setAttr("weakness", "2");
			stage = 1;
			schedule(1);
			break;
		case 1:
			a().cancelProcess(getName());
			a().cancelProcess(new FybrinogenLocalLeftArm().getName());
			a().cancelProcess(new FybrinogenLocalRightArm().getName());
			a().cancelProcess(new FybrinogenLocalLeftLeg().getName());
			a().cancelProcess(new FybrinogenLocalRightLeg().getName());

			a().cancelProcess(new Healer().getName());
			a().cancelProcess(new Requre().getName());
			a().cancelProcess(new Surfaktant().getName());
			a().cancelProcess(new PlasmoDialite().getName());
			a().cancelProcess(new EssenceCita().getName());
			stage = 2;
			schedule(30);
			break;
		}
	}

}
