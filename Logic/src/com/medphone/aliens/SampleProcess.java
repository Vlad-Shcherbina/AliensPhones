package com.medphone.aliens;

public class SampleProcess extends AliensProcess {

	public String get_name() {
		return "SampleProcess";
	}

	public void event() {
		switch (stage) {
		
		case 0:
			add_notification("Тестовый процесс начался");
			set_attr("status", "ты чувствуешь, как протекает тестовый процесс");
			important();
			stage = 1;
			schedule(5);
			break;
			
		case 1:
			add_notification("Тестовый процесс закончился");
			break;
			
		}
	}

}
