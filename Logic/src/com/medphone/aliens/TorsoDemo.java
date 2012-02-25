package com.medphone.aliens;

public class TorsoDemo extends AliensProcess {

	public String get_name() {
		return "TorsoDemo";
	}

	public void event() {
		switch (stage) {
		case 0:
			a().cancel_process(get_name());
			add_notification(
					"Достаю из кармана портсигар и целую его; " +
					"он разбит пулей.");
			set_attr("status", "я шокирован{/а} и исполнен{/а} благоговения");
			stage = 1;
			schedule(10+a().rand(5));
			break;
		}
	}

}
