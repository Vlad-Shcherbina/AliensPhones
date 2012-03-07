package com.medphone.aliens;

public class LiverWound extends AliensProcess {

	public String getName() {
		return "LiverWound";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("Я ранен{/а} в живот справа!");
			stage = 1;
			setAttr("bleeding", "2");
			setAttr("painStomach", a().rand(2) == 0 ? "2" : "3");
			// fall through
		case 1:
			if (a().rand(1000) < 15)
				a().sepsis = true;
			
			a().blood -= 50;
			
			schedule(1);
			break;
		}
	}

}
