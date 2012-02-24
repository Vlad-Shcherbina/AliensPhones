package com.medphone.aliens;
import java.util.Hashtable;
import java.util.Vector;

import com.medphone.Engine;
import com.medphone.Process;
import com.medphone.Engine.Event;
import com.medphone.Engine.TickResult;



public class AliensEngine extends Engine {
	
	Hashtable used_codes = new Hashtable();
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
		return;
	}
	
	public int code_status(int code) {
		if (used_codes.containsKey(new Integer(code)))
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
			used_codes.put(new Integer(code), new Integer(42));
		
		Process p = AliensTables.create_process(name);
		
		if (p != null) {
			if (!p.get_name().equals(name))
				System.out.println("********* SHIT!!!!!! "+name);
			schedule(p, 0);
		}
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
	
	static final int SAFE = 0;
	static final int MORON = 1;
	static final int RESP = 2;
	static final int MASK = 3;
	int air = SAFE;
	
	int lungs = 120;
	int lungs_renewable = 120;
		
	boolean alive = true;
	
	void die() {
		die("Я умер{/ла}");
	}
	
	void die(String msg) {
		if (alive) {
			add_notification(msg);
			important();
		}
		alive = false;
	}
	
	int time = 0;
	
	public String get_debug_info() {
		String s = "";
		
		s += "lungs("+lungs+","+lungs_renewable+"); ";
		
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
	
	protected String get_status() {
		
		if (!alive)
			return "Я МЕРТВ{/А}";
		
		String result = "";
		String[] ss = collect_attrs("status");
		for (int i = 0; i < ss.length; i++) {
			if (i > 0) result += "; ";
			result += ss[i];
		}
		
		
		if (lungs < 25)
			result += ic("боль в груди, кашель с кровью; ");
		else if (lungs < 50)
			result += ic("постоянный сильный кашель, одышка, слабость; ");
		else if (lungs < 75)
			result += ic("сухой кашель, давит грудь; ");
		else if (lungs < 100)
			result += ic("хрипы, затруднённое дыхание; ");
		
		
		if (air == RESP)
			result += "дышу через респиратор; ";
		else if (air == MASK)
			result += "дышу через полную маску; ";
		
		if (result.equals(""))
			result = "жалоб нет";
		return result;
	}
	
	protected void idle() {
		time++;
		
		int dmg = 0;
		if (air == SAFE)
			lungs_renewable++;
		else if (air == MORON)
			dmg = 2;
		else if (air == RESP)
			dmg = 1;
		else if (air == MASK)
			dmg = time%2;
		
		if (has_process("Antialvin"))
			dmg = dmg*(time/2%2);
		
		if (lungs_renewable > 120)
			lungs_renewable = 120;
		
		lungs_renewable -= dmg;
		if (lungs_renewable < 0) {
			lungs += lungs_renewable;
			lungs_renewable = 0;
		}
				
		if (lungs > 120)
			lungs = 120;
		
		if (lungs < 0)
			die("Мои лёгкие превратились в говно и я задохнул{ся/ась}");
		
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
