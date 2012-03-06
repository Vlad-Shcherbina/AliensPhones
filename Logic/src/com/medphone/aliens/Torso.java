package com.medphone.aliens;

public class Torso extends AliensProcess {

	public String getName() {
		return "Torso";
	}

	public void event() {
		
		// TODO: other wounds
		AliensProcess[] wounds = {new LiverWound()};
		
		for (int i = 0; i < wounds.length; i++)
			if (a().hasProcess(wounds[i].getName())) {
				addNotification("Меня отбросило. Кровавые брызги во все стороны!");
				a().blood -= 500;
				return;
			}
		
		a().schedule(AliensTables.createProcess(wounds[a().rand(wounds.length)].getName()), 0);
	}

}
