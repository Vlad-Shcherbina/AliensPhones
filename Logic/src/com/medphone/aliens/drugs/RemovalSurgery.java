package com.medphone.aliens.drugs;

import com.medphone.aliens.Facehugger;
import com.medphone.aliens.Queen;

public class RemovalSurgery extends TorsoSurgery {

	public String getName() {
		return "RemovalSurgery";
	}

	public void onSuccess() {
		/*
QSBLH - Чужинец успешно извлечен
WNDBQ - Чужинец метался и порвал
WTTCG - Королеву успешно извлекли
		 */
		if (a().hasMatureAlien()) {
			if (a().hasProcess(new Friz().getName()) &&
				a().hasProcess(new Citosolute().getName())) {
				addNotification("* Врач извлек какие-то пораженные ткани...");
				addNotification("* (Игрок! Перепиши следующий код на пробирку)");
				if (a().hasProcess(new Queen().getName()))
					addNotification("* (Код: WTTCG)");
				else
					addNotification("* (Код: QSBLH)");
				
				addNotification("* Уже зашивают. Как быстро!");
				a().cancelProcess(new Facehugger().getName());
				a().cancelProcess(new Queen().getName());
			}
			else {
				addNotification("* (Игрок! Перепиши следующий код на пробирку)");
				addNotification("* (Код: WNDBQ)");
				addNotification("* Врач стал что-то делать...");
				addNotification("* ВНЕЗАПНО что-то пошло не так!");
				addNotification("* БОЛЬ!! ОРУ ИЗО ВСЕХ СИЛ! ");
				addNotification("* Червяк с зубастой башкой рвется и шипит в ране!");
				addNotification("* Кровь и кислота льются во все стороны!");
				addNotification("* Грудь разорванна, не могу вдохнуть!");
				a().die("Все вокруг темнеет... Я умер{/ла}.");
			}
		}
		else {
			addNotification("* В ходе операции никаких инородных тел обнаружено не было");
		}
	}


}
