package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;

public class PlasmaToxone extends AliensProcess {

	public String getName() {
		return "PlasmaToxone";
	}

	public void event() {
		switch (stage) {
		case 0:
			a().sepsisHits += 15;
			addNotification("Я почувствовал{/а} обычный, казалось бы, укол.");
			stage = 1;
			schedule(1);
			break;
		case 1:
			a().sepsisHits += 15;
			a().poisoning = a().NO_POISONING;
			a().cancelProcess(new MonoFloxacyne().getName());
			a().cancelProcess(new Klodil().getName());
			// TODO: cancel methmorthine, methanol-cyanide
			break;
		}

	}

}
