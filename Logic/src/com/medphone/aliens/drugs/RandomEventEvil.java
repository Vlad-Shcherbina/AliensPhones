package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class RandomEventEvil extends AliensProcess {

	public String getName() {
		return "RandomEventEvil";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("Я ободрал{/а} правую ногу о какой-то штырь!");
			stage = 1;
			setAttr("painRightLeg", "1");
			setAttr("bleeding", "1");
			a().sepsis = true;
			schedule(1);
			break;
		}

	}

}
