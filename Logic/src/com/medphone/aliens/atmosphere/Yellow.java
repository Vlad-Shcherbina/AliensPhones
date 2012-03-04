package com.medphone.aliens.atmosphere;

import com.medphone.aliens.AliensProcess;
import com.medphone.aliens.drugs.AntiAlvine;

public class Yellow extends AliensProcess {

	public String getName() {
		return "Yellow";
	}
	
	protected String getMessage() {
		return "Я услышал{/а} тихое шипение.";
	}
	
	protected int getDamage() {
		return 150;
	}
	
	protected void instantDamage(int dmg) {
		if (a().air == a().RESP || a().air == a().SAFE)
			dmg /= 3;
		else if (a().air == a().MASK)
			dmg /= 9;
		
		if (a().hasProcess(new AntiAlvine().getName()))
			dmg = dmg*68/100;
		
		if (dmg > 100) {
			addNotification("Я адски закашлял{ся/ась}.");
			setAttr("status", "! я ничего не соображаю");
			stage = 1;
			schedule(1);
		}
		else if (dmg > 10)
			addNotification("Я закашлял{ся/ась}, у меня закружилась голова.");
		
		if (dmg > a().lungsRenewable + a().lungs - 28)
			dmg = a().lungsRenewable + a().lungs - 28; // to avoid instant death
		
		a().lungsRenewable -= dmg;
		if (a().lungs < 0) {
			a().lungs += a().lungsRenewable;
			a().lungsRenewable = 0;
		}
		
		if (a().rand(100) < dmg)
			a().poisoning = true;
		
		// TODO: clarify antialvine reduction
	}

	public void event() {
		switch (stage) {
		case 0:
			a().cancelProcess(new Yellow().getName());
			a().cancelProcess(new Blue().getName());
			addNotification(getMessage());
			instantDamage(getDamage());
			break;
		case 1:
			setAttr("unconscious", "zzz");
			delAttr("status");
			stage = 2;
			schedule(5);
			break;
		case 2:
			delAttr("unconscious");
			setAttr("weakness", "3");
			
			stage = 2;
			schedule(14);
			break;
		}
	}

}
