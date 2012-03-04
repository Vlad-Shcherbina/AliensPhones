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
	boolean conscious;

	int blood;

	public static final int SAFE = 0;
	public static final int GASP = 1;
	public static final int RESP = 2;
	public static final int MASK = 3;
	public int air;

	public int lungs;
	public int lungsRenewable;

	public boolean poisoning;

	int liver;
	int kidneys;

	int painTolerance;

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
		poisoning = false;

		liver = 600;
		kidneys = 600;

		painTolerance = 10;
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
		ser.writeBool(poisoning, "poisoning", "no poisoning");
		ser.writeInt("liver", liver);
		ser.writeInt("kidneys", kidneys);
		ser.writeInt("painTolerance", painTolerance);

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
		poisoning = ser.readBool("poisoning", "no poisoning");
		liver = ser.readInt("liver");
		kidneys = ser.readInt("kidneys");
		painTolerance = ser.readInt("painTolerance");

		super.deserialize(ser);

		used_codes = ser.readDict();
	}

	public boolean hasProcess(String name) {
		for (int i = queue.size() - 1; i >= 0; i--) {
			Event e = (Event) queue.elementAt(i);
			if (e.process.getName().equals(name))
				return true;
		}
		return false;
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

	void die(String msg) {
		if (alive) {
			addNotification(msg);
			important();
			alive = false;
		}
	}

	public String getDebugInfo() {
		String s = VERSION + " ";

		s += "lungs(" + lungs + "," + lungsRenewable + "); ";

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

	void addLungStatus() {
		int x = lungs;
		if (poisoning)
			x -= 25;
		// TODO: dichloflu effects
		if (x < 0) {
			addStatus(ic("! у меня жесточайший кровавый кашель; частое дыхание"));
			setWeakness(3);
			setPain("Chest", 3);
		} else if (x < 25) {
			addStatus(ic("! кашель с кровью"));
			setWeakness(3);
			setPain("Chest", 2);
		} else if (x < 50) {
			addStatus(ic("у меня одышка"));
			setWeakness(2);
		} else if (x < 75) {
			addStatus(ic("у меня сухой кашель"));
			setWeakness(1);
		} else if (x < 100)
			addStatus(ic("мне тяжело дышать"));
	}

	void addLiverStatus() {
		int s = 0;
		if (liver < 100)
			s = 3;
		else if (liver < 200)
			s = 2;
		else if (liver < 300)
			s = 1;
		if (poisoning)
			s++;

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
			setPain("Stomach", 2); // TODO: правое подреберье?
			break;
		case 3:
		case 4:
			addStatus(ic("! меня рвёт"));
			setWeakness(1);
			setPain("Stomach", 3); // TODO: правое подреберье?
			break;
		}
		if (liver < 0)
			die("Моя печень превратилась в говно и я сдох{/ла}");
	}

	private void addKidneyStatus() {
		int s = 0;
		if (kidneys < 100)
			s = 3;
		else if (kidneys < 300)
			s = 2;
		else if (kidneys < 500)
			s = 3;
		if (poisoning)
			s++;

		switch (s) {
		case 0:
			break;
		case 1:
			addStatus("хочу пить");
			break;
		case 2:
			addStatus("хочу пить");
			setPain("Head", 1); // TODO: clarify level
			break;
		case 3:
			// TODO: head?
			setPain("Loin", 3);
			setWeakness(3);
			break;
		default:
			break;
		}

		if (kidneys < 0)
			die("Мои почки превратилась в говно и я сдох{/ла}");
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
		// TODO: modify by echinospore, friz, methanol-cyanide
		switch (weakness) {
		case 0:
			break;
		case 1:
			addStatus("я немного слаб{/а}");
			break;
		case 2:
			addStatus("! я слаб{/а}");
			addStatus("! без опоры подкашиваются ноги");
			break;
		case 3:
			addStatus(ic("! я очень слаб{/а}"));
			addStatus("! не могу встать даже на четвереньки");
			break;
		case 4:
		case 5:
			addStatus(ic("! я дико ослаб{/ла}"));
			addStatus("! не могу даже пальцем пошевелить");
			break;
		default:
			break;
		}
	}

	void addPainStatus() {
		for (int i = 0; i < pain.length; i++) {
			String[] pp = collectAttrs("pain" + PAIN_AREAS[i]);
			for (int j = 0; j < pp.length; j++) {
				try {
					int level = Integer.parseInt(pp[j]);
					setPain(PAIN_AREA_NAMES[i], level);
				} catch (NumberFormatException e) {
					System.out.println("Error parsing pain");
				}
			}
		}

		// TODO: анестетики
		
		int everywhereIndex = getPainAreaIndex("Everywhere");
		
		boolean hasBenz = hasProcess(new BenzylAlienat().getName(), 2);
		for (int i = 0; i < pain.length; i++) {
			if (hasBenz && pain[i] != 3 && pain[i] != 0)
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

		if (max == 3)
			painTolerance--;
		else
			painTolerance = 9;

		if (painTolerance <= 0)
			schedule(new UnbearablePain(), 0);

		if (alive && conscious) {
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
				if (time % 2 == 0) {
					important();
				}
		}
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

		addLungStatus();
		addLiverStatus();
		addKidneyStatus();

		addWeaknessStatus();

		addPainStatus();

		if (air == SAFE)
			addStatus("дышу чистым воздухом");
		else if (air == GASP)
			addStatus("дышу нефильтрованным воздухом");
		else if (air == RESP)
			addStatus("дышу через респиратор");
		else if (air == MASK)
			addStatus("дышу через полную маску");

		if (status.size() == 0) {
			addStatus("жалоб нет");
		}

		if (!alive && !conscious)
			new_conscious = true; // you wake up before death to suffer

		if (conscious && !new_conscious)
			addNotification("Я потерял{/а} сознание.");
		if (!conscious && new_conscious) {
			result.notifications.insertElementAt("Я приш{ел/ла} в себя.", 0);
		}

		if (!conscious && !new_conscious) {
			if (result.notifications.size() > 0) {
				result.notifications = new Vector();
				addNotification("Я ничего не почувствовал{/а}.");
			}
			result.importance_flag = false;
		}

		conscious = new_conscious;

		if (!alive) {
			queue = new Vector();
			return "Я МЕРТВ{/А}";
		}

		if (!conscious) {
			for (int i = 0; i < status.size(); i++) {
				String s = (String) status.elementAt(i);
				if (!s.startsWith("* "))
					status.removeElementAt(i--);
			}
			status.insertElementAt("я без сознания", 0);
		}

		// sort by priorities
		String[] priorities = { "!!! ", "!! ", "* ", "! ", "" };

		String result = "";
		for (int p = 0; p < priorities.length; p++) {
			String prefix = priorities[p];
			for (int i = 0; i < status.size(); i++) {
				String s = (String) status.elementAt(i);
				if (s.startsWith(prefix)) {
					s = s.substring(prefix.length());
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

		if (poisoning)
			lungs--;

		if (lungs > 120)
			lungs = 120;

		if (lungs < 0)
			die("Я выкашлял{/а} фрагменты лёгких и умер{/ла}.");
	}

	protected void idle() {
		time++;

		lungsIdle();

		if (poisoning) {
			liver -= 10;
			kidneys -= 10;
		}
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
