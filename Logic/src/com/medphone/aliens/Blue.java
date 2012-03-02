package com.medphone.aliens;

public class Blue extends Yellow {
	
	public String get_name() {
		return "Blue";
	}
	
	public void event() {
		a().cancel_process(new Yellow().get_name());
		a().cancel_process(new Blue().get_name());
		add_notification(
				"Я услышал{/а} аццкое шипение и почувствовал{/а} " +
				"кислый привкус во рту.");
		instantDamage(450);
	}
}
