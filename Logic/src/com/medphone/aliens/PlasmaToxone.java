package com.medphone.aliens;

public class PlasmaToxone extends AliensProcess {

	public String get_name() {
		return "PlasmaToxone";
	}

	public void event() {
		switch (stage) {
		case 0:
			add_notification("Я почувствовал{/а} обычный, казалось бы, укол.");
			stage = 1;
			schedule(1);
			break;
		case 1:
			a().poisoning = false;
			// TODO: cancel metmorph, methanol-cyanide, monofloxacyne
			break;
		}

	}

}
