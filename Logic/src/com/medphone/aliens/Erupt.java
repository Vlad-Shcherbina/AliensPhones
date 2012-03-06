package com.medphone.aliens;

public class Erupt extends AliensProcess {

	public String getName() {
		return "Erupt";
	}

	public void event() {
		switch (stage) {
		case 0:
			if (a().hasAlien())
				addNotification("* Кажется, ничего не произошло.");
			else {
				addNotification("* Ничего не произошло.");
				break;
			}
			stage = 1;
			schedule(10);
			break;
		case 1:
			if (a().hasAlien()) {
				stage = 2;
				setAttr("unconscious", "zzz");
				schedule(1);
			}
			break;
		case 2:
			addNotification("* У меня в груди что-то зашевелилось.");
			addNotification("* Оттуда выскочил грибо-червяк.");
			addNotification("* МЯСО.");
			a().die("Чужинец резво ускакал, а я умер{/ла}.");
			break;
		}

	}

}
