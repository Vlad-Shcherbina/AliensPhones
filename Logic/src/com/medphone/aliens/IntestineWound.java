package com.medphone.aliens;

public class IntestineWound extends AliensProcess {

	public String getName() {
		return "IntestineWound";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("Я ранен{/а} в живот!");
			stage = 1;
			if (a().rand(10) < 8)
				setAttr("bleeding", "1");
			else
				setAttr("bleeding", "3");
			setAttr("painStomach", "3");
			// fall through
		case 1:
			a().sepsis = true;
			schedule(5);
			break;
		}
	}

}
