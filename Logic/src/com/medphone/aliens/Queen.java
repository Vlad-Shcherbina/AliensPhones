package com.medphone.aliens;

public class Queen extends AliensProcess {

	public String getName() {
		return "Queen";
	}

	public void event() {
		switch (stage) {
		case 0:
			if (a().hasAlien()) {
				addNotification("Ничего не произошло");
				return;
			}
			addNotification("* d1");
			stage = 2;
			schedule(60);
			break;
		default:
			stage++;
			if (stage < 10) {
				schedule(60);
				break;
			}
			if (stage % 2 == 0) {
				setAttr("status", "(ic) сухой кашель"); //
				schedule(5);
			} else {
				if (stage >= 31 && stage%6 == 1) {
					addNotification("Как будто в груди что-то пошевелилось...");
					// TODO: random messages
				}
				delAttr("status");
				schedule(55);
			}
			break;
		}
	}


}
