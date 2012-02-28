import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.CustomItem;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.StringItem;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.medphone.Engine;
import com.medphone.aliens.AliensEngine;

public class Main extends MIDlet implements CommandListener {

	static final int TICK_DURATION = 10*1000;
	static final int NOTIFICATION = 5*1000;
	
	private Display display;
	private Form mainScreen;
	private Timer timer;
	
	private Command ok;
	
	private String code = "";
	
	Engine engine = new AliensEngine();
	
	Engine.TickResult tick_result = null;
	boolean need_tick = false;
	
	public Main() {
		display = Display.getDisplay(this);
		mainScreen = new Form(" ");
		ok = new Command("Ok", Command.OK, 1);

		mainScreen.append(new KeyCatcher());
		mainScreen.append("status");
		mainScreen.append(new KeyCatcher());

		mainScreen.setCommandListener(this);

		timer = new Timer();
		
		engine.initialize();

		tick();
	}
	
	protected void startApp() throws MIDletStateChangeException {
		
		display.setCurrent(mainScreen);
	}
	
	synchronized void tick() {
		need_tick = false;
		tick_result = engine.tick();
		pendingStuff();
	}
	
	synchronized void pendingStuff() {
		if (tick_result.notifications.size() == 0) {
			if (tick_result.importance_flag) {
				code = "";
				setMainText(tick_result.status);
				beep();
			}
			else
				if (code.equals(""))
					setMainText(tick_result.status);
			need_tick = true;
			timer.schedule(new TestTimerTask(), TICK_DURATION);
		}
		else {
			notify((String)tick_result.notifications.elementAt(0));
			timer.schedule(new TestTimerTask(), NOTIFICATION);
		}
	}
	
	synchronized void schedule(int dt) {
		timer.cancel();
		timer = new Timer();
		timer.schedule(new TestTimerTask(), dt);
	}
		
	synchronized public void commandAction(Command command, Displayable displayable) {
		if (command == ok) {
			if (tick_result.notifications.size() > 0)
				tick_result.notifications.removeElementAt(0);
			display.setCurrent(mainScreen);
			timer.cancel();
			timer = new Timer();
			pendingStuff();
		}
	}
	
	private class TestTimerTask extends TimerTask {
		public final void run() {
			if (need_tick)
				tick();
			else
				pendingStuff();
		}
	}

	synchronized void keyPressed(int keyCode) {
		if (keyCode >= '0' && keyCode <= '9') {
			code += (char)keyCode;
			
			if (code.length() > 8) {
				Alert a = new Alert(" ", "Слишком длинный код", null, AlertType.ERROR);
				a.setTimeout(1000);
				a.getType().playSound(display);
				display.setCurrent(a, mainScreen);
				code = "";				
			}
			else
			try {
				int c = Integer.parseInt(code);
				int code_status = engine.code_status(c);
				//System.out.println("Code "+c+", code status "+code_status);
				if (code_status == engine.USED_CODE) {
					Alert a = new Alert(code, "Код уже использовался", null, AlertType.ERROR);
					a.setTimeout(1000);
					a.getType().playSound(display);
					display.setCurrent(a, mainScreen);
					code = "";
				}
				else if (code_status == engine.VALID_CODE) {
					beep();
					code = "";
					engine.receive_code(c);
					timer.cancel();
					timer = new Timer();
					tick();
				}
			}
			catch (NumberFormatException e) {
				code = "";
			}
		}
		if (keyCode == (int)'#' || keyCode == (int)'*') {
			code = "";
		}
		if (keyCode < 0) {
			if (code.length() > 0)
				code = code.substring(0, code.length()-1);
		}
		
		if (code.equals(""))
			setMainText(tick_result.status);
		else
			setMainText("Код: "+code+"...");
	}
	
	void setMainText(String s) {
		StringItem textItem = (StringItem) mainScreen.get(1);
		if (!textItem.getText().equals(s)) // to avoid autorewind
			textItem.setText(s); 
	}
	
	void notify(String s) {
		Alert a = new Alert(" ", s, null, AlertType.ALARM);
		a.setTimeout(Alert.FOREVER);
		a.setCommandListener(this);
		a.addCommand(ok);
		a.getType().playSound(display);
		
		display.setCurrent(a, mainScreen);
	}
	
	void beep() {
		Alert a = new Alert(" ", "Перечитай текст", null, AlertType.ALARM);
		a.setTimeout(300);
		a.getType().playSound(display);
		
		display.setCurrent(a, mainScreen);		
	}
	
	class KeyCatcher extends CustomItem {
		protected void keyPressed(int keyCode) {
			Main.this.keyPressed(keyCode);
		}
		public KeyCatcher() {
			super("");
		}
		public int getMinContentWidth() {
			return 1;
		}
		public int getMinContentHeight() {
			return 1;
		}
		public int getPrefContentWidth(int width) {
			return getMinContentWidth();
		}
		public int getPrefContentHeight(int height) {
			return getMinContentHeight();
		}
		protected void paint(Graphics g, int w, int h) {
		}

	}
	
	protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {}


	protected void pauseApp() {}

}
