package com.medphone.aliens;

public class Facehugger extends AliensProcess {

	public String getName() {
		return "Facehugger";
	}

	public void event() {
		switch (stage) {
		case 0:
			if (a().hasAlien()) {
				addNotification("Ничего не произошло");
				return;
			}
			addNotification("Ничего не вижу!");
			addNotification("Я вскрикнул{/а}.");
			setAttr("unconscious", "zzz");
			stage = 1;
			schedule(5);
			break;
		case 1:
			delAttr("unconscious");
			addNotification("Что это было? Наверное, под газ попал{/а}...");
			stage = 2;
			schedule(60);
			break;
		default:
			if (stage % 2 == 0) {
				setAttr("status", "(ic) сухой кашель"); //
				schedule(15);
			} else {
				if (stage > 6 && stage%4 == 3) {
					addNotification("Не могу вдохнуть - больно!");
					addNotification("А, не, нормально.");
				}
				delAttr("status");
				schedule(45);
			}
			stage++;
			break;
		}
	}

}
