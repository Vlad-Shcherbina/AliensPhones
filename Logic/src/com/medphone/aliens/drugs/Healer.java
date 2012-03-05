package com.medphone.aliens.drugs;

public class Healer extends Requre {
	public String getName() {
		return "Healer";
	}
	
	public void event() {
		if (stage == 0)
			a().liver -= 80;
		super.event();
	}
}
