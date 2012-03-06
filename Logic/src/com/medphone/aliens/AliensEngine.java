package com.medphone.aliens;

import java.util.Hashtable;
import java.util.Vector;

import com.medphone.Engine;
import com.medphone.Process;
import com.medphone.Serializer;
import com.medphone.aliens.drugs.*;

public class AliensEngine extends Engine {

	final static String VERSION = "v0.1";

	Hashtable used_codes;
	Hashtable index_by_code = null;

	public void initialize() {
		index_by_code = new Hashtable();
		Object[] t = AliensTables.codes;
		int idx = -1;
		for (int i = 0; i < t.length; i++)
			if (t[i] instanceof String)
				idx = i;
			else if (t[i] instanceof Integer)
				index_by_code.put(t[i], new Integer(idx));
		reset();
	}

	public int codeStatus(int code) {
		if (used_codes.containsKey("" + code))
			return USED_CODE;
		if (index_by_code.containsKey(new Integer(code)))
			return VALID_CODE;
		else
			return INVALID_CODE;
	}

	public void receiveCode(int code) {

		Object[] t = AliensTables.codes;
		int idx = ((Integer) index_by_code.get(new Integer(code))).intValue();

		String name = (String) t[idx];
		boolean reusable = ((Boolean) t[idx + 1]).booleanValue();

		if (!reusable)
			used_codes.put("" + code, "used");

		Process p = AliensTables.createProcess(name);

		if (p != null) {
			if (!p.getName().equals(name))
				System.out.println("********* SHIT! CLASS NAME! " + name);
			schedule(p, 0);
		}
	}

	static final String[] PAIN_AREAS = { 
		"Everywhere", 
		"LeftArm", "RightArm", "LeftLeg", "RightLeg", 
		"Stomach", "Chest", "Loin", "Head" };

	static final String[] PAIN_AREA_NAMES = { 
		"всё тело", 
		"левая рука", "правая рука", "левая нога", "правая нога", 
		"живот", "в груди", "в пояснице", "голова" };

	static final String[] PAIN_STRENGTH = { 
		null, "немного болит", "!! болит", "!!! дико болит" };

	static int getPainAreaIndex(String area) {
		for (int i = 0; i < PAIN_AREAS.length; i++)
			if (area.equals(PAIN_AREAS[i]))
				return i;
		return -1;
	}

	// /// internal state //////

	int time;

	boolean alive;
	public boolean conscious;

	public int blood;

	public static final int SAFE = 0;
	public static final int GASP = 1;
	public static final int RESP = 2;
	public static final int MASK = 3;
	public int air;

	public int lungs;
	public int lungsRenewable;

	public static final String NO_POISONING = "no poisoning";
	public static final String LUNGS_POISONING = "lungs poisoning";
	public static final String LIVER_POISONING = "liver poisoning";
	public static final String KIDNEY_POISONING = "kidney poisoning";
	public String poisoning;

	public int liver;
	public int kidneys;

	int painTolerance;
	
	public boolean sepsis;
	public int sepsisHits;
	
	int prevMobility;

	// //////////////////////

	public void reset() {
		super.reset();

		used_codes = new Hashtable();

		time = 0;

		alive = true;
		conscious = true;

		blood = 3000;

		air = SAFE;
		lungs = 120;
		lungsRenewable = 120;
		poisoning = NO_POISONING;

		liver = 600;
		kidneys = 600;

		painTolerance = 5;
		
		sepsis = false;
		sepsisHits = 120;
		
		prevMobility = 0;
	}

	public void serialize(Serializer ser) {
		// for (int i = 0; i < 200; i++)
		// ser.writeString("zzz whatever");

		ser.writeInt("time", time);

		ser.writeBool(alive, "alive", "dead");
		ser.writeBool(conscious, "conscious", "unconscious");

		ser.writeInt("blood", blood);

		ser.writeInt("air", air);
		ser.writeInt("lungs", lungs);
		ser.writeInt("lungsRenewable", lungsRenewable);
		ser.writeString(poisoning);
		ser.writeInt("liver", liver);
		ser.writeInt("kidneys", kidneys);
		ser.writeInt("painTolerance", painTolerance);
		ser.writeBool(sepsis, "sepsis", "no sepsis");
		ser.writeInt("sepsisHits", sepsisHits);
		ser.writeInt("prevMobility", prevMobility);

		super.serialize(ser);

		ser.writeDict(used_codes);
	}

	public void deserialize(Serializer ser) {
		// for (int i = 0; i < 200; i++)
		// if (!ser.readString().equals("zzz whatever"))
		// throw new RuntimeException("zzz whatever");

		time = ser.readInt("time");

		alive = ser.readBool("alive", "dead");
		conscious = ser.readBool("conscious", "unconscious");

		blood = ser.readInt("blood");

		air = ser.readInt("air");
		lungs = ser.readInt("lungs");
		lungsRenewable = ser.readInt("lungsRenewable");
		poisoning = ser.readString();
		liver = ser.readInt("liver");
		kidneys = ser.readInt("kidneys");
		painTolerance = ser.readInt("painTolerance");
		sepsis = ser.readBool("sepsis", "no sepsis");
		sepsisHits = ser.readInt("sepsisHits");
		prevMobility = ser.readInt("prevMobility");

		super.deserialize(ser);

		used_codes = ser.readDict();
	}

	
	public int countProcesses(String name) {
		int result = 0;
		for (int i = queue.size() - 1; i >= 0; i--) {
			Event e = (Event) queue.elementAt(i);
			if (e.process.getName().equals(name))
				result++;
		}
		return result;
	}

	public Process findProcess(String name) {
		for (int i = queue.size() - 1; i >= 0; i--) {
			Event e = (Event) queue.elementAt(i);
			if (e.process.getName().equals(name))
				return e.process;
		}
		return null;
	}
	
	public boolean hasProcess(String name) {
		return findProcess(name) != null;
	}

	public boolean hasProcess(String name, int stage) {
		for (int i = queue.size() - 1; i >= 0; i--) {
			Event e = (Event) queue.elementAt(i);
			if (e.process.getName().equals(name) && e.process.stage == stage)
				return true;
		}
		return false;
	}

	public void cancelProcess(String name) {
		for (int i = 0; i < queue.size(); i++) {
			Event e = (Event) queue.elementAt(i);
			if (e.process.getName().equals(name)) {
				queue.removeElementAt(i);
				i--;
			}
		}
	}

	void die() {
		die("Я умер{/ла}");
	}

	public void die(String msg) {
		if (alive) {
			addNotification(msg);
			important();
			alive = false;
		}
	}

	public String getDebugInfo() {
		String s = VERSION + " ";

		s += "lungs("+lungs+","+lungsRenewable+"); ";
		s += "LK("+liver+","+kidneys+"); ";

		s += "[";
		for (int i = 0; i < queue.size(); i++) {
			Event e = (Event) queue.elementAt(i);
			if (i > 0)
				s += ", ";
			s += e.process.getName() + "@" + e.process.stage + " in " + e.t
					+ "m";
		}
		s += "]";
		return s;

	}
	
	public boolean hasAlien() {
		return hasProcess(new Facehugger().getName()) ||
				hasProcess(new Queen().getName());
	}
	
	public boolean operationSuccessful(String loc) {
		return
		    !conscious ||
			hasProcess(new MethMorthine().getName(), 1) ||
			hasProcess(new MethMorthine().getName(), 2) || 
			hasProcess(new MethMorthine().getName(), 3) || 
			hasProcess(new MetanolCyanide().getName(), 1) ||
			hasProcess(new MetanolCyanide().getName(), 2) ||
			hasProcess("Urcaine"+loc);
	}
	
	int getBleeding(Process p) {
		if (p.hasAttr("bleeding") && !p.hasAttr("bleedingStopped"))
			try {
				return Integer.parseInt(p.getAttr("bleeding"));
			}
			catch (NumberFormatException e)
			{}
		return 0;
	}
	
	void addBloodStatus() {
		
		boolean hasFriz = hasProcess(new Friz().getName(), 2);
		boolean hasWar =
				hasProcess(new Warfareen().getName(), 2)
			 || hasProcess(new WarfareenSalicylat().getName(), 2);
		boolean hasEhi = hasProcess(new Ehinospore().getName(), 2);
		
		if (hasWar) {
			if (blood < 800)
				die("У меня остановилось сердце и я умер");
			else if (blood < 1200)
				addStatus(ic("! мне нечем дышать"));
		}
		
		int numActivePerf = 0;
		
		for (int i = 0; i < queue.size(); i++) {
			Event e = (Event)queue.elementAt(i);
			
			if (e.process instanceof Perftoran && ((Perftoran)e.process).stage <= 5)
				numActivePerf++;
			
			int k = getBleeding(e.process);
			
			// TODO: friz

			if (hasEhi && k > 0)
				k += 1;
			
			if (hasWar)
				k -= 2;
			
			if (hasFriz)
				k -= 1;
			
			if (k < 0)
				k = 0;
			if (k > 5)
				k = 5;
			
			if (k > 0) {
				String s = "";
				if (k == 1) {
					s = "* кровь сочится";
					blood -= 25;
				} else if (k == 2) {
					s = ic("*! кровь течёт");
					blood -= 50;
				} else if (k == 3) {
					s = ic("*! кровь обильно течёт");
					blood -= 100;
				} else if (k == 4) {
					s = ic("*!! кровь хлещет");
					blood -= 160;
				} else {
					s = ic("*!! кровь бьёт фонтаном");
					blood -= 270;
				}
				
				String name = e.process.getName();
				if (name.equals("LeftArm"))
					s += " из левой руки";
				else if (name.equals("RightArm"))
					s += " из правой руки";
				else if (name.equals("LeftLeg"))
					s += " из левой ноги";
				else if (name.equals("RightLeg"))
					s += " из правой ноги";
				else if (name.equals("LiverWound"))
					s += " из раны на правом боку";
				else if (name.equals("IntestineWound"))
					s += " из раны на животе";
				else if (name.equals("LungWound"))
					s += " из раны на груди";
				else if (name.equals("RandomEventEvil"))
					s += " из царапины на правой ноге";
				else
					System.out.println("Unrecognized bleeding place "+name);
				
				addStatus(s);
			}
		}
		
		if (numActivePerf == 1)
			addStatus("* на мне пакет с красной жидкостью");
		else if (numActivePerf >= 2 && numActivePerf <= 4)
			addStatus("* на мне "+numActivePerf+" пакета с красной жидкостью");
		else if (numActivePerf >= 5)
			addStatus("* на мне "+numActivePerf+" пакетов с красной жидкостью");
		

		int speed = 30; // per hour

		if (hasFriz)
			speed = 0;
		
		if (hasProcess(new Erpoiteen().getName()))
			speed *= 20;
		
		speed = speed*liver/600;
		
		blood += speed / 60;
		if (rand(60) < speed%60)
			blood += 1;
		
		if (blood > 3000)
			blood = 3000;
		
		if (blood < 0) {
			die("Из меня вытекло слишком много крови и я умер{/ла}.");
		}
		else if (blood < 1000) {
			setWeakness(4);
		}
		else if (blood < 1500) {
			setWeakness(3);
			addStatus("темнеет в глазах");
		}
		else if (blood < 2000) {
			setWeakness(2);
			addStatus("меня мучает жажда");
		}
		else if (blood < 2500) {
			setWeakness(1);
		}
	}

	void addLungStatus() {
		int x = lungs;
		if (poisoning.equals(LUNGS_POISONING)) {
			x -= 25;
			lungs -= 1;
		}
		
		String df = new DichloFlu().getName();
		boolean maskCough = hasProcess(df, 2) || hasProcess(df, 3);
		
		if (x < 0) {
			if (maskCough)
				addStatus(ic("у меня лёгкий кашель"));
			else
				addStatus(ic("! у меня жесточайший кровавый кашель"));
			addStatus("задыхаюсь");
			setWeakness(3);
			setPain("Chest", 3);
		} else if (x < 25) {
			if (maskCough)
				addStatus("я слегка покашливаю");
			else
				addStatus(ic("! кашель с кровью"));
			setWeakness(3);
			setPain("Chest", 2);
		} else if (x < 50) {
			if (!maskCough)
				addStatus(ic("мучительный кашель"));
			addStatus("тяжело дышать");
			setWeakness(2);
		} else if (x < 75) {
			if (!maskCough)
				addStatus(ic("у меня сухой кашель"));
			setWeakness(1);
		} else if (x < 100) {
			if (!maskCough)
				addStatus(ic("иногда кашляю"));
		}
	}

	void addLiverStatus() {
		
		if (liver > 600)
			liver = 600;
		
		int s = 0;
		if (liver < 100)
			s = 3;
		else if (liver < 200)
			s = 2;
		else if (liver < 300)
			s = 1;
		
		if (poisoning.equals(LIVER_POISONING)) {
			s++;
			liver -= 10;
		}

		switch (s) {
		case 0:
			break;
		case 1:
			setWeakness(1);
			setPain("Stomach", 1); // TODO: правое подреберье?
			break;
		case 2:
			addStatus(ic("! меня постоянно тошнит"));
			setWeakness(1);
			setPain("Stomach", 2);
			break;
		case 3:
		case 4:
			addStatus(ic("! меня рвёт"));
			setWeakness(1);
			setPain("Stomach", 3);
			break;
		}
		if (liver < 0)
			die("Моя печень отказала и я умер{/ла} в жутких мучениях.");
	}

	private void addKidneyStatus() {
		if (kidneys > 600)
			kidneys = 600;
		
		int perf = countProcesses(new Perftoran().getName());
		if (perf > 2)
			kidneys -= (perf-2)*2;
		
		int s = 0;
		if (kidneys < 100)
			s = 3;
		else if (kidneys < 300)
			s = 2;
		else if (kidneys < 500)
			s = 1;
		
		if (poisoning.equals(KIDNEY_POISONING)) {
			kidneys -= 10;
			s++;
		}

		switch (s) {
		case 0:
			break;
		case 1:
			addStatus("меня мучает жажда");
			break;
		case 2:
			addStatus("меня мучает жажда");
			setPain("Head", 1);
			break;
		case 3:
			setPain("Loin", 3);
			setWeakness(3);
			break;
		default:
			break;
		}

		if (kidneys < 0)
			die("Почки кольнули меня пару раз и я умер{/ла}.");
	}

	void addWeaknessStatus() {
		String[] ww = collectAttrs("weakness");
		for (int i = 0; i < ww.length; i++) {
			try {
				int w = Integer.parseInt(ww[i]);
				setWeakness(w);
			} catch (NumberFormatException e) {
				System.out.println("error parsing weakness");
			}
		}
		
		if (hasProcess(new MetanolCyanide().getName(), 2) ||
			hasProcess(new Ehinospore().getName(), 2))
			weakness -= 2;
		else if (hasProcess(new DichloFlu().getName(), 2) ||
				 hasProcess(new Ehinospore().getName(), 1))
			weakness -= 1;
		
		if (hasProcess(new Friz().getName(), 2))
			if (weakness < 3)
				weakness = 3;
		
		int mobility = 0;
		
		if (weakness < 0)
			weakness = 0;
		switch (weakness) {
		case 0:
			break;
		case 1:
			addStatus("я немного слаб{/а}");
			break;
		case 2:
			addStatus(ic("! я слаб{/а}"));
			mobility = 1;
			break;
		case 3:
			addStatus(ic("! я очень слаб{/а}"));
			mobility = 2;
			break;
		case 4:
		case 5:
			addStatus(ic("! я дико ослаб{/ла}"));
			mobility = 3;
			break;
		default:
			break;
		}
		
		if (mobility > 0) {
			if (mobility > 3)
				mobility = 3;
			String[] mob = {
					null,
					"без опоры подкашиваются ноги",
					"не могу встать даже на четвереньки",
					"не могу даже пальцем пошевелить"};
			addStatus("! "+mob[mobility]);
			if (alive && prevMobility < mobility)
				addNotification(mob[mobility]);
		}
		prevMobility = mobility;
	}

	void addPainStatus() {
		//pain[1] = 3;
		for (int i = 0; i < pain.length; i++) {
			String[] pp = collectAttrs("pain" + PAIN_AREAS[i]);
			for (int j = 0; j < pp.length; j++) {
				try {
					int level = Integer.parseInt(pp[j]);
					setPain(PAIN_AREAS[i], level);
				} catch (NumberFormatException e) {
					System.out.println("Error parsing pain");
				}
			}
		}

		int everywhereIndex = getPainAreaIndex("Everywhere");
		
		boolean nonNarc = 
				hasProcess(new BenzylAlienat().getName(), 2)
			 || hasProcess(new WarfareenSalicylat().getName(), 2);
		boolean narc = 
				hasProcess(new MethMorthine().getName(), 1) 
			 ||	hasProcess(new MethMorthine().getName(), 2) 
			 ||	hasProcess(new MethMorthine().getName(), 3) 
			 || hasProcess(new MetanolCyanide().getName(), 1)
			 || hasProcess(new MetanolCyanide().getName(), 2);
		for (int i = 0; i < pain.length; i++) {
			if (narc) {
				pain[i] = pain[i] == 3 ? 1 : 0;
			}
			else if (
					hasProcess("Urcaine"+PAIN_AREAS[i], 2) ||
					hasProcess("FirstAidKit"+PAIN_AREAS[i], 2)) {
				pain[i] = pain[i] == 3 ? 2 : 0;
			}
			else if (nonNarc && pain[i] != 3 && pain[i] != 0)
				pain[i]--;
		}
		
		int max = 0;
		for (int i = 0; i < pain.length; i++) {
			if (pain[i] > max)
				max = pain[i];
			if (pain[i] > pain[everywhereIndex]) {
				String p = PAIN_STRENGTH[pain[i]];
				if (p != null)
					addStatus(p + " " + PAIN_AREA_NAMES[i]);
			}
		}
		String p = PAIN_STRENGTH[pain[everywhereIndex]];
		if (p != null)
			addStatus(p + " " + PAIN_AREA_NAMES[everywhereIndex]);

		if (max > 0)
			ic("pain" + max);

		if (alive && conscious) {

			if (max == 3) {
				painTolerance--;
				Process up = new UnbearablePain();
				if (painTolerance <= 0 && !hasProcess(up.getName()))
					schedule(up, 0);
			} else
				painTolerance = 5;

			
			if (max == 3) {
				if (time % 2 == 0) {
					switch (rand(3)) {
					case 0:
						addNotification("Ору от боли!");
						break;
					case 1:
						addNotification("Адски больно!");
						break;
					case 2:
						addNotification("БОЛЬ! БОЛЬ! БОЛЬ!");
						break;
					}
				}
			} else if (max == 2)
				if (time % 3 == 0) {
					important();
				}
		}
		else
			painTolerance = 5;
	}
	
	void addSepsisStatus() {
		// TODO: friz slowdown
		boolean hasFriz = hasProcess(new Friz().getName(), 2);
		
		if (!hasFriz || time%4 < 2) {
			if (sepsis)
				sepsisHits -= 1+time%2; 
			else
				sepsisHits += 1+time%2;
		}
		
		if (sepsisHits > 120)
			sepsisHits = 120;
		
		boolean maskHeat = hasProcess(new DichloFlu().getName(), 2);
		
		if (sepsisHits < 0)
			die("Моё сознание совсем помутилось, меня адски стошнило и я умер{/ла}.");
		else if (sepsisHits < 30) {
			setWeakness(3);
			setPain("Everywhere", 2);
			if (!hasFriz) {
				if (maskHeat)
					addStatus("у меня слегка повышена температура");
				else {
					addStatus("* у меня сильный жар");
					addStatus("(ic) я дрожу");
				}
			}
		}
		else if (sepsisHits < 60) {
			setWeakness(2);
			if (!maskHeat && !hasFriz)
				addStatus(ic("* у меня жар"));
		}
		else if (sepsisHits < 90) {
			setWeakness(1);
			if (!maskHeat && !hasFriz)
				addStatus("у меня повышенная температура");
		}
		
		if (hasFriz)
			addStatus("мне холодно");
			
	}

	Vector status;

	void addStatus(String s) {
		status.addElement(s);
	}

	int weakness;

	void setWeakness(int w) {
		if (w > weakness)
			weakness = w;
	}

	int[] pain;

	void setPain(String area, int level) {
		int index = getPainAreaIndex(area);
		if (pain[index] < level)
			pain[index] = level;
	}

	protected String getStatus() {
		pain = new int[PAIN_AREAS.length];

		status = new Vector();
		weakness = 0;
		boolean new_conscious = true;

		String[] ss = collectAttrs("status");
		for (int i = 0; i < ss.length; i++) {
			addStatus(ss[i]);
		}

		String[] uu = collectAttrs("unconscious");
		for (int i = 0; i < uu.length; i++) {
			new_conscious = false;
		}

		addBloodStatus();
		addLungStatus();
		addLiverStatus();
		addKidneyStatus();
		addSepsisStatus();
		
		addWeaknessStatus();

		addPainStatus();

		if (status.size() < 5) {
			if (air == SAFE)
				addStatus("дышу чистым воздухом");
			else if (air == GASP)
				addStatus("дышу нефильтрованным воздухом");
			else if (air == RESP)
				addStatus("дышу через респиратор");
			else if (air == MASK)
				addStatus("дышу через полную маску");
		}

		/*if (status.size() == 0) {
			addStatus("жалоб нет");
		}*/

		if (!alive && !conscious)
			new_conscious = true; // you wake up before death to suffer

		if (conscious && !new_conscious)
			addNotification("Я потерял{/а} сознание.");
		if (!conscious && new_conscious) {
			result.notifications.insertElementAt("Я приш{ел/ла} в себя.", 0);
		}

		Vector nots = result.notifications;
		if (!conscious && !new_conscious) {
			
			int n_maskable = 0;
			for (int i = 0; i < nots.size(); i++) {
				String not = (String)nots.elementAt(i);
				if (!not.startsWith("*")) {
					n_maskable++;
					nots.removeElementAt(i--);
					//nots.setElementAt("masked("+not+")", i);
				}
			}
			if (nots.size() == 0 && n_maskable > 0)
				addNotification("Я ничего не почувствовал{/а}.");
			result.importance_flag = false;
		}
		
		for (int i = 0; i < nots.size(); i++) {
			String not = (String)nots.elementAt(i);
			if (not.startsWith("* "))
				nots.setElementAt(not.substring(2), i);
		}
		
		if (!alive)
			result.importance_flag = false;

		conscious = new_conscious;

		if (!alive) {
			queue = new Vector();
			return "Я МЕРТВ{/А} (Игрок, выключи телефон! Изображай труп а потом иди к мастерам)";
		}

		if (!conscious) {
			for (int i = 0; i < status.size(); i++) {
				String s = (String) status.elementAt(i);
				if (!s.startsWith("* "))
					status.removeElementAt(i--);
			}
			status.insertElementAt("* я без сознания", 0);
		}

		// sort by priorities
		String[] priorities = { "!!! ", "!! ", "*!! ", "*! ", "* ", "! ", "(ic) ", "" };

		// TODO: remove duplicates, like "хочу пить"
		String result = "";
		for (int p = 0; p < priorities.length; p++) {
			String prefix = priorities[p];
			for (int i = 0; i < status.size(); i++) {
				String s = (String) status.elementAt(i);
				if (s.startsWith(prefix)) {
					s = s.substring(prefix.length());
					if (prefix.equals("(ic) "))
						s = ic(s);
					result += s + "; ";
					status.removeElementAt(i--);
				}
			}
		}
		return result;
	}

	private void lungsIdle() {
		int dmg = 0;
		if (air == SAFE)
			lungsRenewable++;
		else if (air == GASP)
			dmg = 2;
		else if (air == RESP)
			dmg = 1;
		else if (air == MASK)
			dmg = time % 3 == 0 ? 0 : 1;

		if (hasProcess(new AntiAlvine().getName()))
			dmg = dmg * (time / 2 % 2);

		if (lungsRenewable > 120)
			lungsRenewable = 120;

		lungsRenewable -= dmg;
		if (lungsRenewable < 0) {
			lungs += lungsRenewable;
			lungsRenewable = 0;
		}

		if (lungs > 120)
			lungs = 120;

		if (lungs < 0)
			die("Я выкашлял{/а} фрагменты лёгких и умер{/ла}.");
	}

	protected void idle() {
		time++;

		lungsIdle();
	}

	int prev_ic_hash = 0;
	int ic_hash = 0;

	String ic(String s) {
		ic_hash += s.hashCode();
		return s;
	}

	public TickResult tick() {
		prev_ic_hash = ic_hash;
		ic_hash = 0;
		TickResult res = super.tick();
		if (prev_ic_hash != ic_hash)
			if (conscious)
				res.importance_flag = true;

		return res;
	}

}
