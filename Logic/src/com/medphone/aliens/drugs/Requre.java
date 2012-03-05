package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;
import com.medphone.aliens.LiverWound;

public class Requre extends AliensProcess {

	public String getName() {
		return "Requre";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("Жгучий укол!");
			stage = 1;
			schedule(2);
			break;
		case 1:
			if (a().hasProcess(new LiverWound().getName())) {
				addNotification("В ране на корпусе образовались какие-то " +
						"фиброзные спайки и я умер{/ла}.");
			}
			a().cancelProcess("LeftArm");
			a().cancelProcess("RightArm");
			a().cancelProcess("LeftLeg");
			a().cancelProcess("RightLeg");
			important();
			break;
		}
	}

}
