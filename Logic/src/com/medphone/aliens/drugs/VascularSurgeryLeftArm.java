package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class VascularSurgeryLeftArm extends AliensProcess {

	protected String getMemberName() {
		return "LeftArm";
	}

	public String getName() {
		return "VascularSurgery"+getMemberName();
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("* Меня оперируют...");
			if (a().operationSuccessful(getMemberName())) {
				addNotification("* Уже зашивают. Как быстро!");
				a().cancelProcess(getMemberName());
			}
			else {
				addNotification("* Очень больно!");
				addNotification("* Я стал{/а} метаться; врач задел сосуды и хлынула кровь.");
				addNotification("* Хорошо что успели пережать!");
				a().blood -= 500;
			}
			stage = 1;
			schedule(5);
			break;
		case 1:
			stage = 2;
			schedule(30);
			break;
		case 2:
			stage = 3;
			schedule(30);
			break;
		}

	}

}
