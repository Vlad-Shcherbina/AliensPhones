package com.medphone.aliens;

public class SampleProcess extends AliensProcess {

	public String getName() {
		return "SampleProcess";
	}

	public void event() {
		switch (stage) {
		
		case 0:
			addNotification("Тестовый процесс начался");
			setAttr("status", "ты чувствуешь, как протекает тестовый процесс");
			important();
			stage = 1;
			schedule(5);
			break;
			
		case 1:
			addNotification("Тестовый процесс закончился");
			break;
			
		}
	}

}
