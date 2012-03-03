package com.medphone.aliens;

import java.util.Hashtable;

import com.medphone.Engine;
import com.medphone.Process;
import com.medphone.Serializer;
import com.medphone.aliens.drugs.*;


public class AliensEngine extends Engine {

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

	int time;

	boolean alive;
	boolean conscious;

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


	public void reset() {
		super.reset();

		used_codes = new Hashtable();

		time = 0;

		alive = true;
		conscious = true;

		air = SAFE;
		lungs = 120;
		lungsRenewable = 120;
		poisoning = false;

		liver = 600;
		kidneys = 600;
	}

	public void serialize(Serializer ser) {
		// for (int i = 0; i < 200; i++)
		// ser.writeString("zzz whatever");

		ser.writeInt("time", time);
		
		ser.writeBool(alive, "alive", "dead");
		ser.writeBool(conscious, "conscious", "unconscious");
		
		ser.writeInt("air", air);
		ser.writeInt("lungs", lungs);
		ser.writeInt("lungs_renewable", lungsRenewable);
		ser.writeBool(poisoning, "poisoning", "no poisoning");
		ser.writeInt("liver", liver);
		ser.writeInt("kidneys", kidneys);

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
		
		air = ser.readInt("air");
		lungs = ser.readInt("lungs");
		lungsRenewable = ser.readInt("lungs_renewable");
		poisoning = ser.readBool("poisoning", "no poisoning");
		liver = ser.readInt("liver");
		kidneys = ser.readInt("kidneys");

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
		String s = "";

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
		// TODO: phrasing, pain
		if (x < 0) {
			addStatus(ic("у меня жесточайший кровавый кашель, Б3 в груди, частое дыхание"));
			setWeakness(3);
		}
		else if (x < 25) {
			addStatus(ic("боль в груди Б???, кашель с кровью"));
			setWeakness(3);
		}
		else if (x < 50) {
			addStatus(ic("у меня одышка"));
			setWeakness(2);
		}
		else if (x < 75) {
			addStatus(ic("у меня сухой кашель"));
			setWeakness(1);
		}
		else if (x < 100)
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
			addStatus("боль в правом подреберье(?!) Б1");
			setWeakness(1);
			break;
		case 2:
			addStatus(ic("Б2, меня постоянно тошнит"));
			setWeakness(1);
			break;
		case 3:
		case 4:
			addStatus(ic("Б3, меня рвёт"));
			setWeakness(1);
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
			addStatus("я хочу пить");
			break;
		case 2:
			addStatus(ic("я хочу пить; болит голова Б???"));
			break;
		case 3:
			addStatus(ic("Б3, боль в пояснице"));
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
			}
			catch (NumberFormatException e) {
				System.out.println("error parsing weakness");
			}
		}
		// TODO: modify by echinospore, friz, methanol-cyanide
		switch (weakness) {
		case 0:
			break;
		case 1:
			addStatus("я немного устал{/а}");
			break;
		case 2:
			addStatus("я слаб{/а}");
			addStatus("без опоры подкашиваются ноги");
			break;
		case 3:
			addStatus(ic("я cущественно ослаб{/ла}"));
			addStatus("не могу встать даже на четвереньки");
			break;
		case 4:
		case 5:
			addStatus(ic("я дико ослаб{/ла}"));
			addStatus("не могу даже пальцем пошевелить");
			break;
		default:
			break;
		}
	}
	
	
	String status;
	
	void addStatus(String s) {
		status += s + "; ";
	}
	
	int weakness;
	
	void setWeakness(int w) {
		if (w > weakness)
			weakness = w;
	}

	

	protected String getStatus() {

		status = "";
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
		

		if (air == SAFE)
			addStatus("дышу чистым воздухом");
		else if (air == GASP)
			addStatus("дышу нефильтрованным воздухом");
		else if (air == RESP)
			addStatus("дышу через респиратор");
		else if (air == MASK)
			addStatus("дышу через полную маску");

		if (status.equals(""))
			status = "жалоб нет";


		if (conscious && !new_conscious)
			addNotification("Я потерял сознание.");
		else if (!conscious && new_conscious)
			addNotification("Я пришел в себя.");
		
		else if (!conscious && !new_conscious) {
			if (result.notifications.size() > 0) {
				result.notifications.clear();
				addNotification("Я ничего не чувствую.");
			}
			result.importance_flag = false;
		}
		
		conscious = new_conscious;

		if (!alive)
			return "Я МЕРТВ{/А}";
		
		if (!conscious) {
			return "я без сознания";
			// TODO: only mask perceptions, not actual symptoms like blood or heat
		}

		return status;
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
			dmg = time % 2;

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
			die("Мои лёгкие превратились в говно и я задохнул{ся/ась}");
	}

	protected void idle() {
		time++;

		lungsIdle();

		if (poisoning) {
			liver -= 10;
			kidneys -= 1;
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
			res.importance_flag = true;

		return res;
	}

}
