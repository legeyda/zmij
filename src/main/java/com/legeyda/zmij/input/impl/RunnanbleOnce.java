package com.legeyda.zmij.input.impl;

public class RunnanbleOnce implements Runnable {

	private Runnable orig;

	public RunnanbleOnce(Runnable orig) {
		this.orig = orig;
	}

	@Override
	public void run() {
		if(null!=orig) {
			orig.run();
			orig = null;
		}
	}
}
