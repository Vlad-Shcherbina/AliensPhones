package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class MetanolCyanide extends AliensProcess {

	public String getName() {
		return "MetanolCyanide";
	}

	public void event() {
		switch(stage) {
		case 0:
			addNotification("Болезненный укол");
			stage = 1;
			schedule(1);
			break;
		case 1:
			stage = 2;
			// reduce pain, improve mobility - all in AliensEngine
			schedule(10);
			break;
		case 2:
			stage++;
			setAttr("painEverywhere", "1");
			setAttr("weakness", "1");
			schedule(3);
			break;
		case 3:
			stage++;
			setAttr("painEverywhere", "2");
			setAttr("weakness", "2");
			schedule(3);
			break;
		case 4:
			stage++;
			setAttr("painEverywhere", "3");
			setAttr("weakness", "4");
			schedule(2);
			break;
		case 5:
			stage++;
			setAttr("painEverywhere", "2");
			setAttr("weakness", "3");
			schedule(3);
			break;
		case 6:
			stage++;
			setAttr("painEverywhere", "1");
			setAttr("weakness", "2");
			schedule(3);
			break;
		}
	}

}
