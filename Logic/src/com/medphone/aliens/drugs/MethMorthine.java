package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class MethMorthine extends AliensProcess {

	public String getName() {
		return "MethMorthine";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("Расслабляющий укол.");
			stage = 1;
			schedule(1);
			break;
		case 1:
			setAttr("unconscious", "zzz");
			setAttr("status", "* на самом деле в тяжёлом сне с бредом");
			stage = 2;
			schedule(15);
			break;
		case 2:
			stage = 3;
			delAttr("unconscious");
			setAttr("status", "у меня эйфория");
			schedule(10);
			break;
		case 3:
			stage = 4;
			setAttr("weakness", "2");
			setAttr("painEverywhere", "2");
			setAttr("status", "дрожат руки");
			schedule(10);
			break;
		}

	}

}
