package com.medphone.aliens;

public class GetAnalysis extends AliensProcess {

	public String getName() {
		return "GetAnalysis";
	}

	public void event() {
		addNotification("* У меня берут анализ крови и тканей.");
		addNotification("* (Игрок, перепиши следующие коды на пробирку)");
		
		String s = "";
		
		if (a().sepsis)
			s += "NQCHL; ";
		
		if (!a().poisoning.equals(a().NO_POISONING))
			s += "XMPQC; ";
		
		if (!a().hasMatureAlien())
			s += "SWPXK; ";
		
		if (a().liver <= 200)
			s += "SDHMN; ";
		else if (a().liver < 500)
			s += "BAZCL; ";
		
		if (a().kidneys <= 200)
			s += "NSRTA; ";
		else if (a().kidneys < 400)
			s += "STWRC; ";

		if (s.equals("")) {
			String[] zzz = {"XTEMA", "LQSAR", "KRSWS"};
			s = zzz[a().rand(zzz.length)];
		}
		
		addNotification("* "+s);
		
		/*
NQCHL - Если есть сепсис (процесс)
XMPQC - Если есть отравление (процесс)
SWPXK - Если чужинец на заметной стадии
SDHMN - Печень <= 200 (вторая стадия уже проявилась)
BAZCL - Печень > 200 и < 500
NSRTA - Почки <= 200
STWRC - Почки < 400 и > 200

XTEMA - Если все ок
LQSAR - если все ок
KRSWS - если все ок

		 */
	}

}
