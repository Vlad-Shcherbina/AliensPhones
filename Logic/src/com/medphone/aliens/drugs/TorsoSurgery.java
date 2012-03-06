package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;
import com.medphone.aliens.LiverWound;

public abstract class TorsoSurgery extends AliensProcess {

	abstract public void onSuccess();
	
	public void event() {
		switch (stage) {
		case 0:
			addNotification("* Меня оперируют...");
			if (a().operationSuccessful("no local urcaine")) {
				onSuccess();
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
