package com.medphone.aliens.drugs;

import com.medphone.aliens.AliensProcess;
import com.medphone.aliens.LeftArmDemo;

public class Sambuca extends AliensProcess {

	public String getName() {
		return "Sambuca";
	}

	public void event() {
		switch (stage) {
		case 0:
			a().cancelProcess(getName());
			addNotification("Я принял{/а} этот обжигающий напиток.");
			
			if (a().hasProcess(new LeftArmDemo().getName())) {
				addNotification("В общем-то, хрен с ним, с костюмом!");
				a().cancelProcess(new LeftArmDemo().getName());
			}
			
			setAttr("status", "я ощущаю эйфорию и подъём");
			schedule(6+a().rand(4));
			stage = 1;
			break;
		case 1:
			setAttr("status", "я готов говорить с кем угодно о чём угодно");
			important();
			schedule(8+a().rand(4));
			stage = 2;
			break;
		case 2:
			addNotification("Надо бы догнаться!");
			setAttr("status", "я ощущаю лёгкую тупость и упадок");
			setAttr("weakness", "1");
			stage = 3;
			schedule(10+a().rand(4));
			break;
		}
	}

}
