package com.medphone.aliens;

public class Sambuca extends AliensProcess {

	public String get_name() {
		return "Sambuca";
	}

	public void event() {
		switch (stage) {
		case 0:
			a().cancel_process(get_name());
			add_notification("Я принял{/а} этот обжигающий напиток.");
			
			if (a().has_process("LeftArmDemo")) {
				add_notification("В общем-то, хрен с ним, с костюмом!");
				a().cancel_process("LeftArmDemo");
			}
			
			set_attr("status", "я ощущаю эйфорию и подъём");
			schedule(6+a().rand(4));
			stage = 1;
			break;
		case 1:
			set_attr("status", "я готов говорить с кем угодно о чём угодно");
			important();
			schedule(8+a().rand(4));
			stage = 2;
			break;
		case 2:
			add_notification("Надо бы догнаться!");
			set_attr("status", "я ощущаю лёгкую тупость и упадок");
			stage = 3;
			schedule(10+a().rand(4));
			break;
		}
	}

}
