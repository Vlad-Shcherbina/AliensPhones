package com.medphone.aliens;

class Safe extends AliensProcess {

	public String getName() {
		return "Safe";
	}

	public void event() {
		if (a().air != a().SAFE)
			addNotification("Я вернул{ся/ась} в помещение с фильтруемым воздухом.");
		else
			addNotification("Я уже в помещении с фильтруемым воздухом.");
		a().air = a().SAFE;
	}

}
