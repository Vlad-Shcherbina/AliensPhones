package com.medphone;

import java.util.Random;
import java.util.Vector;


public abstract class Engine {
	
	public static final int USED_CODE = 1;
	public static final int VALID_CODE = 2;
	public static final int INVALID_CODE = 3;
	
	public abstract int codeStatus(int code);
	public abstract void receiveCode(int code);
	
	public abstract void initialize();

	Random random = new Random();
	
	public int rand(int n) {
		int x = random.nextInt();
		if (x < 0) x = -x;
		if (x < 0) x = 0; // it is possible for minint
		return x%n;
	}
	
	public void reset() {
		queue = new Vector();
		male = true;
	}
	
	public void serialize(Serializer ser) {
		ser.writeBool(male, "male", "female");
		ser.writeInt("queue size", queue.size());
		for (int i = 0; i < queue.size(); i++) {
			Event e = (Event)queue.elementAt(i);
			ser.writeInt("t", e.t);
			ser.writeString(e.process.getName());
			ser.writeInt("stage", e.process.stage);
			ser.writeDict(e.process.attrs);
		}
	}
	
	public void deserialize(Serializer ser) {
		male = ser.readBool("male", "female");
		int queue_size = ser.readInt("queue size");
		queue = new Vector();
		for (int i = 0; i < queue_size; i++) {
			
			int t = ser.readInt("t");
			// TODO: decouple from aliens stuff
			Process p = com.medphone.aliens.AliensTables.createProcess(ser.readString());
			p.stage = ser.readInt("stage");
			p.attrs = ser.readDict();
			schedule(p, t);
		}
	}
	
	void deserialize() {
	}	
	
	public static class Event {
		public int t;
		public Process process;
	}
	
	protected Vector queue = new Vector();
	

	public void schedule(Process process, int dt) {
		process.engine = this;
		Event q = new Event();
		q.t = dt;
		q.process = process;
		queue.addElement(q);
	}

	Event popEvent() {
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
	
	protected TickResult result;
	
	public void addNotification(String s) {
		result.notifications.addElement(s);
	}
	
	protected void important() {
		result.importance_flag = true;
	}
	
	protected String[] collectAttrs(String key) {
		Vector result = new Vector();
		for (int i = 0; i < queue.size(); i++) {
			Event e = (Event)queue.elementAt(i);
			if (e.process.hasAttr(key))
				result.addElement(e.process.getAttr(key));
		}
		
		String[] ss = new String[result.size()];
		for (int i = 0; i < result.size(); i++)
			ss[i] = (String)result.elementAt(i);
		return ss;
	}
	
	protected abstract String getStatus();
	protected abstract void idle();
	
	public TickResult tick() {
		result = new TickResult();
		result.notifications = new Vector();
		
		result.importance_flag = false;
		
		while (true) {
			Event e = popEvent();
			if (e == null)
				break;
			e.process.event();
		}
		
		idle();
		
		for (int i = 0; i < queue.size(); i++)
			((Event)queue.elementAt(i)).t--;
		
		result.status = getStatus();
		
		TickResult res = result;
		
		res.status = fixGender(res.status);
		for (int i = 0; i < res.notifications.size(); i++)
			res.notifications.setElementAt(
					fixGender((String)res.notifications.elementAt(i)),
					i);
		
		result = null;
		return res;
	}
	
	public boolean male = true;
	String fixGender(String s) {
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
