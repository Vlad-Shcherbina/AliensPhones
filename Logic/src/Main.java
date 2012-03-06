

import java.io.*;
import java.util.Arrays;
import java.util.Hashtable;

import com.medphone.Engine;
import com.medphone.Serializer;
import com.medphone.Engine.TickResult;
import com.medphone.aliens.AliensEngine;


public final class Main {
	
	static int phonewordToCode(String s) {
		String[] keys = {"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
		int result = 0;
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			for (int j = 0; j < keys.length; j++)
				if (keys[j].indexOf(c) != -1) {
					result *= 10;
					result += 2+j;
					break;
				}
		}
		return result;
	}
	
	static void processCode(int code) {
	
		int code_status = engine.codeStatus(code);
		if (code_status == engine.INVALID_CODE) {
			out.println("invalid code");
			return;
		}
		if (code_status == engine.USED_CODE) {
			out.println("code was already used");
			return;
		}
		
		//out.println("Receiving code "+code);
		engine.receiveCode(code);
		later();
		time_passed = -1; // because we are losing one minute on every code
		tick();
	}
	
	static void wait(int t, boolean break_flag) {
		for (int i = 0; i<t; i++)
			if (tick() && break_flag)
				break;
	}
		
	static String prev_status = "no status whatsoever";
	static Engine engine;
	//tick_result;
	static int time_passed;

	static void later() {
		if (time_passed > 0) {
			/*int t = time_passed*60;
			if (t / 60 > 0)
				out.print(t/60+" minutes ");
			if (t % 60 > 0)
				out.print(t%60+" seconds ");
			out.println("later...");*/
			out.println(time_passed+" minutes later...");
			time_passed = 0;
		}
	}
	
	static void testSerialization() {
		Serializer ser = new Serializer();
		engine.serialize(ser);
		//out.println("*************");
		//ser.print();
		byte[] b = ser.getBytes();
		
		ser = new Serializer();
		ser.setBytes(b);
		Engine e = new AliensEngine();
		e.initialize();
		e.deserialize(ser);
		
		ser = new Serializer();
		e.serialize(ser);
		//out.println("*************");
		//ser.print();
		if (!Arrays.equals(b, ser.getBytes())) {
			out.println("*** SERIALIZATION/DESERIALIZATION PROBLEM");
		}
		
	}
	
	// return whether something important happened
	static boolean tick() {
		testSerialization();
		Engine.TickResult tick_result = engine.tick();
		time_passed++;
		
		for (int i = 0; i < tick_result.notifications.size(); i++) {
			later();
			String s = (String)tick_result.notifications.elementAt(i);
			out.println("*** MESSAGE: "+s);
		}

		if (tick_result.importance_flag) {
			later();
			out.println("*** BEEP!");
		}
		
		if (!tick_result.status.equals(prev_status)) {
			prev_status = tick_result.status;
			later();
			out.println("STATUS: "+tick_result.status);
		}
		
		return tick_result.notifications.size() > 0 || tick_result.importance_flag;
	}
	
	static BufferedReader in;
	static PrintStream out;
	
	public static void main(String[] args) throws IOException {

		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintStream(System.out, true, "866");
		
		engine = new AliensEngine();
		engine.initialize();
		time_passed = -1;
		tick();
		
		while (true) {
			later();
			System.out.print(">>> ");
			String s = in.readLine();
			
			if (s.equals("exit"))
				break;
			
			if (s.equals("help")) {
				out.println("Commands:");
				out.println("    help - show this help message");
				out.println("    exit - exit, obviously");
				out.println("    <code> - take corresponding drug");
				out.println("    wait - wait for something interesting");
				out.println("    wait <time in minutes> - wait N minutes, but stop when something interesting happens");
				out.println("    sleep <time in minutes> - wait all the period no matter what would happen");
				out.println("    <phoneword> - well, enter phoneword");
				out.println("    ser - dump internal state and test serialization/deserialization");
				continue;
			}
			
			try {
				int code = Integer.parseInt(s);
				processCode(code);
				continue;
			}
			catch (NumberFormatException e) {
			}
			
			if (s.equals("wait")) {
				wait(1000, true);
				continue;
			}
			
			if (s.startsWith("wait ")) {
				try {
					int t = Integer.parseInt(s.substring(5));
					wait(t, true);
					continue;
				}
				catch (NumberFormatException e) {
				}
			}

			if (s.startsWith("sleep ")) {
				try {
					int t = Integer.parseInt(s.substring(6));
					wait(t, false);
					continue;
				}
				catch (NumberFormatException e) {
				}
			}
			
			if (s.equals("reset")) {
				engine.reset();
				continue;
			}
			
			if (s.equals("dump")) {
				Serializer ser = new Serializer();
				engine.serialize(ser);
				ser.print();
				continue;
			}
			
			if (s.equals("ser")) {
				Serializer ser = new Serializer();
				engine.serialize(ser);
				ser.print();
				byte[] b = ser.getBytes();
				engine.reset();
				engine.deserialize(ser);
				
				ser = new Serializer();
				engine.serialize(ser);
				if (!Arrays.equals(b, ser.getBytes()))
					out.println("*** SERIALIZATION/DESERIALIZATION PROBLEM");
				continue;
			}
			
			int code = phonewordToCode(s);
			out.println("phoneword "+code);
			processCode(code);
			continue;
			
			//out.println("Unrecognized command (type 'help' for list of supported commands).");
		}
	}
}
