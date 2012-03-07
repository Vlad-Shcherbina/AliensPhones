package com.medphone.aliens;

import com.medphone.Process;

public class Larva extends AliensProcess {

	public String getName() {
		return "Larva";
	}

	
	public void event() {
		switch (stage) {
		case 0:
			if (a().hasProcess(new Queen().getName()))
				addNotification("* Кажется, ничего не произошло.");
			else {
				addNotification("* Ничего не произошло.");
				break;
			}
			stage = 1;
			schedule(10);
			break;
		case 1:
			Process p = a().findProcess(new Queen().getName());
			if (p != null)
				p.stage = 10;
		}

	}

}
