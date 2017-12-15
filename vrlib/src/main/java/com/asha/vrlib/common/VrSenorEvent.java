package com.asha.vrlib.common;

public class VrSenorEvent {

	private int accuracy;
	private long timestamp;
    public final float[] values;
    
    public VrSenorEvent(int accuracy,long timestamp,float[] values){
    	this.accuracy = accuracy;
    	this.timestamp = timestamp;
    	this.values = values;
    }
    
	public int getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
    
    
}
