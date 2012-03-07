package com.medphone.aliens;

public class Erupt extends AliensProcess {

	public String getName() {
		return "Erupt";
	}

	public void event() {
		switch (stage) {
		case 0:
			if (a().hasMatureAlien())
				addNotification("* Кажется, ничего не произошло.");
			else {
				addNotification("* Ничего не произошло.");
				break;
			}
			stage = 1;
			schedule(13);
			break;
		case 1:
			if (a().hasMatureAlien()) {
				addNotification("* Дыхание перехватило, что-то шевелится!");
				addNotification("* БООЛЬ! Падаю, корчусь, не могу вдохнуть!");
				addNotification("* Кажется отпустило...");
				addNotification("* Что-то в боку колет!!");
				addNotification("* БООООЛЬ!! Выгибаюсь и ору от боли!!");
				addNotification("* Зловешее шипение, из грудной клетки вылезла зубастая башка!");
				addNotification("* Не могу вдохнуть, все вокруг темнеет...");
				a().die("Я умер{/ла}.");
			}
			break;
		}

	}

}
