package com.mjitech.model;

import java.io.Serializable;

public class CommonKey implements Serializable{
	
	private static final long serialVersionUID = -23718296478368723L;
	private int currentKey = 0;
	private int initialKey = 0;
	private String keyName;
	private int step = 1;
	
	public int getCurrentKey() {
		return currentKey;
	}
	public void setCurrentKey(int currentKey) {
		this.currentKey = currentKey;
	}
	public int getInitialKey() {
		return initialKey;
	}
	public void setInitialKey(int initialKey) {
		this.initialKey = initialKey;
	}
	public String getKeyName() {
		return keyName;
	}
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}

	
	
}
