package com.medphone.aliens;

public class TorsoDemo extends AliensProcess {

	public String getName() {
		return "TorsoDemo";
	}

	public void event() {
		switch (stage) {
		case 0:
			a().cancelProcess(getName());
			addNotification(
					"Достаю из кармана портсигар и целую его; " +
					"он разбит пулей.");
			setAttr("status", "я шокирован{/а} и исполнен{/а} благоговения");
			stage = 1;
			schedule(10+a().rand(5));
			break;
		}
	}

}
