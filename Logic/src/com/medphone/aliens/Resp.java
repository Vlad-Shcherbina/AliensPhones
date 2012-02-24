package com.medphone.aliens;

class Resp extends AliensProcess {

	public String get_name() {
		return "Resp";
	}

	public void event() {
		if (a().air != a().RESP)
			add_notification("Я надел{/а} респиратор и выш{ел/ла} в коридоры");
		else
			add_notification("Я уже в респираторе");
		a().air = a().RESP;
	}

}
