package com.medphone.aliens.atmosphere;

public class Blue extends Yellow {
	
	public String getName() {
		return "Blue";
	}
	
	protected String getMessage() {
		return "Я почувствовал{/а} резкий кислый привкус во рту.";
	}
	
	protected int getDamage() {
		return 450;
	}
}
