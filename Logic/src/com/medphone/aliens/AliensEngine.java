package com.medphone.aliens;
import java.util.Hashtable;

import com.medphone.Engine;
import com.medphone.Process;
import com.medphone.Serializer;



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
		
	public int code_status(int code) {
		if (used_codes.containsKey(""+code))
			return USED_CODE;
		if (index_by_code.containsKey(new Integer(code)))
			return VALID_CODE;
		else
			return INVALID_CODE;
	}
	
	public void receive_code(int code) {

		Object[] t = AliensTables.codes;
		int idx = ((Integer)index_by_code.get(new Integer(code))).intValue();
		
		String name = (String)t[idx];
		boolean reusable = ((Boolean)t[idx+1]).booleanValue();
		
		if (!reusable)
			used_codes.put(""+code, "used");
		
		Process p = AliensTables.createProcess(name);
		
		if (p != null) {
			if (!p.get_name().equals(name))
				System.out.println("********* SHIT! CLASS NAME! "+name);
			schedule(p, 0);
		}
	}

	int time;
	
	static final int SAFE = 0;
	static final int GASP = 1;
	static final int RESP = 2;
	static final int MASK = 3;
	int air;
	
	int lungs;
	int lungsRenewable;
	
	boolean poisoning;
	
	int liver;
	int kidneys;
		
	boolean alive;

	public void reset() {
		super.reset();
		
		used_codes = new Hashtable();
		
		time = 0;
		
		alive = true;
		
		air = SAFE;
		lungs = 120;
		lungsRenewable = 120;
		poisoning = false;
		
		liver = 600;
		kidneys = 600;
	}
	
	public void serialize(Serializer ser) {
		//for (int i = 0; i < 200; i++)
		//	ser.writeString("zzz whatever");
		
		ser.writeInt("time", time);
		ser.writeBool(alive, "alive", "dead");
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
		//for (int i = 0; i < 200; i++)
		//	if (!ser.readString().equals("zzz whatever"))
		//		throw new RuntimeException("zzz whatever");
		
		time = ser.readInt("time");
		alive = ser.readBool("alive", "dead");
		air = ser.readInt("air");
		lungs = ser.readInt("lungs");
		lungsRenewable = ser.readInt("lungs_renewable");
		poisoning = ser.readBool("poisoning", "no poisoning");
		liver = ser.readInt("liver");
		kidneys = ser.readInt("kidneys");
		
		super.deserialize(ser);
		
		used_codes = ser.readDict();
	}
	
	boolean has_process(String name) {
		for (int i = queue.size()-1; i >= 0; i--) {
			Event e = (Event)queue.elementAt(i);
			if (e.process.get_name().equals(name))
				return true;
		}
		return false;
	}
	
	void cancel_process(String name) {
		for (int i = 0; i < queue.size(); i++) {
			Event e = (Event)queue.elementAt(i);
			if (e.process.get_name().equals(name)) {
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
			add_notification(msg);
			important();
			alive = false;
		}
	}
		
	public String getDebugInfo() {
		String s = "";
		
		s += "lungs("+lungs+","+lungsRenewable+"); ";
		
		s += "[";
		for (int i = 0; i < queue.size(); i++) {
			Event e = (Event)queue.elementAt(i);
			if (i > 0)
				s += ", ";
			s += e.process.get_name()+"@"+e.process.stage+" in "+e.t+"m";
		}
		s += "]";
		return s;
		
	}
	
	void addLungStatus() {
		int x = lungs;
		if (poisoning)
			x -= 25;
		// TODO: phrasing, effects
		if (x < 0)
			status += ic("у меня жесточайший кровавый кашель, Б3 в груди, частое дыхание; ");
		else if (x < 25)
			status += ic("боль в груди Б???, кашель с кровью, С3; ");
		else if (x < 50)
			status += ic("у меня одышка, С2; ");
		else if (x < 75)
			status += ic("у меня сухой кашель, С1; ");
		else if (x < 100)
			status += ic("мне тяжело дышать; ");		
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
			status += "боль в правом подреберье(?!) Б1, С1; ";
			break;
		case 2:
			status += ic("Б2, меня постоянно тошнит, С1; ");
			break;
		case 3:
		case 4:
			status += ic("Б3, меня рвёт, С1; ");
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
			status += "я хочу пить; ";
			break;
		case 2:
			status += ic("я хочу пить; болит голова Б???; ");
			break;
		case 3:
			status += ic("Б3, боль в пояснице, С3; ");
		}
		
		if (kidneys < 0)
			die("Мои почки превратилась в говно и я сдох{/ла}");		
	}
	
	String status;
	protected String getStatus() {
				
		status = "";
		String[] ss = collect_attrs("status");
		for (int i = 0; i < ss.length; i++) {
			status += ss[i] + "; ";
		}
		
		addLungStatus();
		addLiverStatus();
		addKidneyStatus();
		
		if (air == SAFE)
			status += "дышу чистым воздухом; ";
		else if (air == GASP)
			status += "дышу нефильтрованным воздухом; ";
		else if (air == RESP)
			status += "дышу через респиратор; ";
		else if (air == MASK)
			status += "дышу через полную маску; ";
				
		if (status.equals(""))
			status = "жалоб нет";
		
		if (!alive)
			return "Я МЕРТВ{/А}";

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
			dmg = time%2;
		
		if (has_process("Antialvin"))
			dmg = dmg*(time/2%2);
		
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
