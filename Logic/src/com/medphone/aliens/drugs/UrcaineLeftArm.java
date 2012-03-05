package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class UrcaineLeftArm extends AliensProcess {

	public String getName() {
		return "UrcaineLeftArm";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("Укол и прохлада в левой руки.");
			stage = 1;
			schedule(1);
			break;
		case 1:
			stage = 2;
			schedule(10);
			break;
		}
	}

}
