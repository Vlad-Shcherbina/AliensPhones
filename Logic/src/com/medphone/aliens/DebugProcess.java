package com.medphone.aliens;


class DebugProcess extends AliensProcess {

	public String get_name() {
		return "DebugProcess";
	}

	public void event() {
		switch (stage) {
		case 0:
			add_notification(a().getDebugInfo());
			break;
		}
	}

}
