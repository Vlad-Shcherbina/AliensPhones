package com.medphone.aliens;

class Resp extends AliensProcess {

	public String getName() {
		return "Resp";
	}

	public void event() {
		if (a().air != a().RESP)
			addNotification("Я надел{/а} респиратор и выш{ел/ла} в коридоры.");
		else
			addNotification("Я уже в респираторе.");
		a().air = a().RESP;
	}

}
