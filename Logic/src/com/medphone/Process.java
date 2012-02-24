package com.medphone;
import java.util.Hashtable;



public abstract class Process {
	abstract public String get_name();
	
	public int stage = 0;
	protected Engine engine;
	Hashtable attrs = new Hashtable();
	
	abstract public void event();
	
	protected void schedule(int dt) {
		engine.schedule(this, dt);
	}
	
	protected void add_notification(String s) {
		engine.add_notification(s);
	}
	
	protected void important() {
		engine.important();
	}
	
	protected void set_attr(String key, String value) {
		attrs.put(key, value);
	}
	
	void del_attr(String key) {
		attrs.remove(key);
	}
}
