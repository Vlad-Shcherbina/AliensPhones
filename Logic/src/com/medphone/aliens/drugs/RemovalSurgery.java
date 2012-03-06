package com.medphone.aliens.drugs;

import com.medphone.aliens.Facehugger;
import com.medphone.aliens.Queen;

public class RemovalSurgery extends TorsoSurgery {

	public String getName() {
		return "RemovalSurgery";
	}

	public void onSuccess() {
		if (a().hasAlien()) {
			if (a().hasProcess(new Friz().getName()) &&
				a().hasProcess(new Citosolute().getName())) {
				addNotification("* Уже зашивают. Как быстро!");
				addNotification("* Извлечёна странная недоразвитая хрень.");
				addNotification("* Код для анализатора - ZZZZZ.");
				a().cancelProcess(new Facehugger().getName());
				a().cancelProcess(new Queen().getName());
			}
			else {
				addNotification("* Чужинец задёргался при приближении скальпеля.");
				a().die("Порванный чужинцем, я умер{/ла}.");
			}
		}
		else {
			addNotification("* В ходе операции никаких инородных тел обнаружено не было");
		}
	}


}
