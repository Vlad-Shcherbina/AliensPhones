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
		
		if (dmg > 100) {
			addNotification("Я адски закашлял{ся/ась}");
			setAttr("unconscious", "zzz");
			stage = 1;
			schedule(4);
		}
		else if (dmg > 10)
			addNotification("Я закашлял{ся/ась}, у меня закружилась голова.");
		
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
			delAttr("unconscious");
			setAttr("weakness", "2");
			
			stage = 2;
			schedule(2);
			break;
		}
	}

}
