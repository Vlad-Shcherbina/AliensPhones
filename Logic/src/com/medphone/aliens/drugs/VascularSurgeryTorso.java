package com.medphone.aliens.drugs;

import com.medphone.aliens.LiverWound;

public class VascularSurgeryTorso extends TorsoSurgery {

	public String getName() {
		return "VascularSurgeryTorso";
	}

	public void onSuccess() {
		a().cancelProcess(new LiverWound().getName());
		addNotification("* Уже зашивают. Как быстро!");
	}

}
