package com.medphone.aliens;


class DebugProcess extends AliensProcess {

	public String getName() {
		return "DebugProcess";
	}

	public void event() {
		switch (stage) {
		case 0:
			addNotification("* "+a().getDebugInfo());
			break;
		}
	}

}
