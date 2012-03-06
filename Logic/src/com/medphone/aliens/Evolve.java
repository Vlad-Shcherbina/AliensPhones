package com.medphone.aliens;

import com.medphone.Process;

public class Evolve extends AliensProcess {

	public String getName() {
		return "Evolve";
	}

	
	public void event() {
		switch (stage) {
		case 0:
			if (a().hasAlien())
				addNotification("* Кажется, ничего не произошло.");
			else {
				addNotification("* Ничего не произошло.");
				break;
			}
			stage = 1;
			schedule(10);
			break;
		case 1:
			Process p;
			p = a().findProcess(new Facehugger().getName());
			if (p != null)
				p.stage = 6;
			p = a().findProcess(new Queen().getName());
			// TODO
			/*if (p != null) {
				if (p.stage < )
				p.stage = 10;
			}*/
			
		}

	}

}
