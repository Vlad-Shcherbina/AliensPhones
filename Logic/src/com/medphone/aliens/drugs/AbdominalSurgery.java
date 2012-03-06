package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;
import com.medphone.aliens.IntestineWound;
import com.medphone.aliens.LiverWound;
import com.medphone.aliens.LungWound;

public class AbdominalSurgery extends TorsoSurgery {

	public String getName() {
		return "AbdominalSurgery";
	}
	
	public void onSuccess() {
		a().cancelProcess(new IntestineWound().getName());
		a().cancelProcess(new LungWound().getName());
		addNotification("* Уже зашивают. Как быстро!");
	}

}
