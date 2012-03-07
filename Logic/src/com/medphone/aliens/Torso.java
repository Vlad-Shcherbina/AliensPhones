package com.medphone.aliens;

public class Torso extends AliensProcess {

	public String getName() {
		return "Torso";
	}

	public void event() {
		
		AliensProcess[] wounds = {
			new LiverWound(), 
			new IntestineWound(),
			new LungWound(),
			};
		
		for (int i = 0; i < wounds.length; i++)
			if (a().hasProcess(wounds[i].getName())) {
				addNotification("Меня отбросило. Кровавые брызги во все стороны!");
				a().blood -= 500;
				return;
			}
		
		a().schedule(AliensTables.createProcess(wounds[a().rand(wounds.length)].getName()), 0);
	}

}
