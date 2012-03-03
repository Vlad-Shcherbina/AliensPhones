package com.medphone.aliens.atmosphere;

import com.medphone.aliens.AliensProcess;

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
		
		if (dmg > 100)
			addNotification("Я аццки закашлял{ся/ась} (TODO: Н3 5 мин)");
		else if (dmg > 10)
			addNotification("Я закашлял{ся/ась} (TODO: Н1 1 мин)");
		
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
		a().cancelProcess(new Yellow().getName());
		a().cancelProcess(new Blue().getName());
		addNotification(getMessage());
		instantDamage(getDamage());
	}

}
