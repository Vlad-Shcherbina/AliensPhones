package com.medphone.aliens;

public class Blue extends Yellow {
	
	public String getName() {
		return "Blue";
	}
	
	protected String getMessage() {
		return "Я услышал{/а} аццкое шипение и почувствовал{/а} " +
				"кислый привкус во рту.";
	}
	
	protected int getDamage() {
		return 450;
	}
}
