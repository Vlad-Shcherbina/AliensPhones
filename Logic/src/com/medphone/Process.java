package com.medphone;
import java.util.Hashtable;



public abstract class Process {
	abstract public String getName();
	
	public int stage = 0;
	protected Engine engine;
	Hashtable attrs = new Hashtable();
	
	abstract public void event();
	
	protected void schedule(int dt) {
		engine.schedule(this, dt);
	}
	
	protected void addNotification(String s) {
		engine.addNotification(s);
	}
	
	protected void important() {
		engine.important();
	}
	
	public void setAttr(String key, String value) {
		attrs.put(key, value);
	}
	
	protected void delAttr(String key) {
		attrs.remove(key);
	}
	
	public boolean hasAttr(String key) {
		return attrs.containsKey(key);
	}
	
	public String getAttr(String key) {
		if (hasAttr(key))
			return (String)attrs.get(key);
		else
			return "";
	}
}
