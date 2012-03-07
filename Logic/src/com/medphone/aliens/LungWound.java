package com.medphone.aliens;

public class LungWound extends AliensProcess {

	public String getName() {
		return "LungWound";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("Я ранен{/а} в грудь!");
			stage = 1;
			setAttr("bleeding", "2");
			setAttr("painChest", "2");
			setAttr("weakness", "3");
			// fall through
		case 1:
			if (a().rand(1000) < 15)
				a().sepsis = true;
			
			a().lungs -= 2;
			
			schedule(1);
			break;
		}		
	}

}
