package com.medphone.aliens;

public class PlasmaToxone extends AliensProcess {

	public String getName() {
		return "PlasmaToxone";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("Я почувствовал{/а} обычный, казалось бы, укол.");
			stage = 1;
			schedule(1);
			break;
		case 1:
			a().poisoning = false;
			// TODO: cancel methmorthine, methanol-cyanide, monofloxacyne
			break;
		}

	}

}
