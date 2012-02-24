package com.medphone;

import java.util.Vector;


public abstract class Engine {
	
	public static final int USED_CODE = 1;
	public static final int VALID_CODE = 2;
	public static final int INVALID_CODE = 3;
	
	public abstract int code_status(int code);
	public abstract void receive_code(int code);
	
	public abstract void initialize();
	
	public static class Event {
		public int t;
		public Process process;
	}
	
	protected Vector queue = new Vector();
	
	protected void schedule(Process process, int dt) {
		process.engine = this;
		Event q = new Event();
		q.t = dt;
		q.process = process;
		queue.addElement(q);
	}

	Event pop_event() {
		// in reverse order to ensure LIFO for simultaneous events
		for (int i = queue.size()-1; i >= 0; i--) {
			Event e = (Event)queue.elementAt(i);
			if (e.t == 0) {
				queue.removeElementAt(i);
				return e;
			}
		}
		return null;
	}

	public static class TickResult {
		public Vector notifications;
		public String status;
		public boolean importance_flag;
	}
	
	int t = 0;	
	
	TickResult result;
	
	public void add_notification(String s) {
		result.notifications.addElement(s);
	}
	
	protected void important() {
		result.importance_flag = true;
	}
	
	protected String[] collect_attrs(String key) {
		Vector result = new Vector();
		for (int i = 0; i < queue.size(); i++) {
			Event e = (Event)queue.elementAt(i);
			Object v = e.process.attrs.get(key);
			if (v != null)
				result.addElement((String)v);
		}
		
		String[] ss = new String[result.size()];
		for (int i = 0; i < result.size(); i++)
			ss[i] = (String)result.elementAt(i);
		return ss;
	}
	
	protected abstract String get_status();
	protected abstract void idle();
	
	public TickResult tick() {
		result = new TickResult();
		result.notifications = new Vector();
		
		result.importance_flag = false;
		
		while (true) {
			Event e = pop_event();
			if (e == null)
				break;
			e.process.event();
		}
		
		idle();
		
		t += 1;
		
		for (int i = 0; i < queue.size(); i++)
			((Event)queue.elementAt(i)).t--;
		
		result.status = get_status();
		
		TickResult res = result;
		
		res.status = fix_gender(res.status);
		for (int i = 0; i < res.notifications.size(); i++)
			res.notifications.setElementAt(
					fix_gender((String)res.notifications.elementAt(i)),
					i);
		
		result = null;
		return res;
	}
	
	void serialize() {
	}
	
	void deserialize() {
	}
	
	public boolean male = true;
	String fix_gender(String s) {
		String result = "";
		int pos = 0;
		int i;
		while (true) {
			i = s.indexOf('{', pos);
			
			if (i == -1) {
				result += s.substring(pos);
				break;
			}
			result += s.substring(pos, i);
			int i2 = s.indexOf('/', i);
			int i3 = s.indexOf('}', i2);
			if (i2 == -1 || i3 == -1) {
				result += "***INVALID GENDER CONSTRUCTION***";
				break;
			}
			if (male)
				result += s.substring(i+1, i2);
			else
				result += s.substring(i2+1, i3);
			pos = i3+1;
		}
		return result;
	}

}
