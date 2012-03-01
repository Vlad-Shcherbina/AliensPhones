package com.medphone;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class Serializer {

	Vector strings = new Vector();
	
	public void print() {
		for (int i = 0; i < strings.size(); i++) {
			System.out.println(strings.elementAt(i));			
		}
	}
	
	public void writeString(String s) {
		strings.add(s);
	}
	
	public void writeInt(int i) {
		strings.add(""+i);
	}
	
	public void writeInt(String name, int i) {
		strings.add(name+" = "+i);
	}
	
	public void writeBool(boolean b, String t, String f) {
		if (b)
			writeString(t);
		else
			writeString(f);
	}
	
	// keys and values have to be strings
	public void writeDict(Hashtable dict) {
		writeInt("dict size", dict.size());
		Enumeration e = dict.keys();
		while (e.hasMoreElements()) {
			String key = (String)e.nextElement();
			writeString(key);
			writeString((String)dict.get(key));
		}
	}
	
	public String readString() {
		return (String)strings.remove(0); // potentially quadratic, but who cares?
	}
	
	public int readInt() {
		return Integer.parseInt(readString());
	}
	
	public int readInt(String name) {
		String prefix = name+" = ";
		String s = readString();
		if (s.startsWith(prefix))
			return Integer.parseInt(s.substring(prefix.length()));
		else
			throw new RuntimeException("expected named value "+name);
	}
	
	public boolean readBool(String t, String f) {
		String s = readString();
		if (s.equals(t))
			return true;
		else if (s.equals(f))
			return false;
		else
			throw new RuntimeException("Can't read bool "+t+"/"+f);
	}
	
	public Hashtable readDict() {
		int size = readInt("dict size");
		Hashtable result = new Hashtable();
		for (int i = 0; i < size; i++) {
			String key = readString();
			String value = readString();
			result.put(key, value);
		}
		return result;
	}
}
