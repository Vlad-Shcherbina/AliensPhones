package com.medphone.aliens;

public class Yellow extends AliensProcess {

	public String get_name() {
		return "Yellow";
	}
	
	protected void instantDamage(int dmg) {
		if (a().air == a().RESP || a().air == a().SAFE)
			dmg /= 3;
		else if (a().air == a().MASK)
			dmg /= 9;
		
		if (dmg > 100)
			add_notification("Я аццки закашлял{ся/ась} (TODO: Н3 5 мин)");
		else if (dmg > 10)
			add_notification("Я закашлял{ся/ась} (TODO: Н1 1 мин)");
		
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
		a().cancel_process(new Yellow().get_name());
		a().cancel_process(new Blue().get_name());
		add_notification("Я услышал{/а} тихое шипение.");
		instantDamage(150);
	}

}
