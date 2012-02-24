package com.medphone.aliens;

class Safe extends AliensProcess {

	public String get_name() {
		return "Safe";
	}

	public void event() {
		if (a().air != a().SAFE)
			add_notification("Я вернул{ся/ась} в помещение с фильтруемым воздухом");
		else
			add_notification("Я уже в помещении с фильтруемым воздухом");
		a().air = a().SAFE;
	}

}
