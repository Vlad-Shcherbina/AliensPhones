package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;
import com.medphone.aliens.LiverWound;

public class VascularSurgeryTorso extends AliensProcess {

	public String getName() {
		return "VascularSurgeryTorso";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("* Меня оперируют...");
			if (a().operationSuccessful("no local urcaine")) {
				addNotification("* Уже зашивают. Как быстро!");
				a().cancelProcess(new LiverWound().getName());
			}
			else {
				addNotification("* Очень больно!");
				addNotification("* Я стал{/а} метаться; врач задел сосуды и хлынула кровь.");
				addNotification("* Хорошо что успели пережать!");
				a().blood -= 500;
			}
			
			if (a().air != a().SAFE)
				a().sepsis = true;
			
			stage = 1;
			schedule(5);
			break;
		case 1:
			stage = 2;
			schedule(25);
			break;
		case 2:
			stage = 3;
			schedule(25);
			break;
		}
		
	}

}
