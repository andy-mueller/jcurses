package jcurses.widgets;

import jcurses.system.InputChar;
import jcurses.system.Toolkit;

class WindowManagerInputThread extends Thread {

	private boolean _run = true;
	private boolean _read = true;
	WindowManagerInputThread(){
		super("JCURSES-INPUT");
		setDaemon(true);
    }


	public void run() {
		while(isRunning()) {
			if (isReading()) {
				InputChar inputChar =  Toolkit.readCharacter();
				WindowManager.handleInput(inputChar);
			}
		}
	}


	protected void block(WindowManagerBlockingCondition cond) {
		Toolkit.endPainting();
		while(cond.evaluate() && isRunning()) {
			if (isReading()) {
				InputChar inputChar =  Toolkit.readCharacter();
				WindowManager.handleInput(inputChar);
			}
		}
	}


	protected synchronized void end() {
		_run = false;
	}


	protected synchronized void deactivate() {
		_read = false;
	}


	protected synchronized boolean isRunning() {
		return _run;
	}


	protected synchronized boolean isReading() {
		return _read;
	}


}
