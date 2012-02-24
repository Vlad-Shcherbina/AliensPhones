package com.medphone.aliens;
import com.medphone.Engine;
import com.medphone.Engine.Event;


class DebugProcess extends AliensProcess {

	public String get_name() {
		return "DebugProcess";
	}

	public void event() {
		switch (stage) {
		case 0:
			add_notification(a().get_debug_info());
			break;
		}
	}

}
