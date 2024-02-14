package me.sashie.skdragon.api.debug;

import me.sashie.skdragon.SkDragonRecode;

public class ParticleException extends RuntimeException {

	public ParticleException(String error) {
		//super(error);
		super(error, null, true, false);
	}

	public ParticleException(String error, SkriptNode skriptNode) {
		//super(error);
		super(error, null, true, false);
		SkDragonRecode.error(error, skriptNode);
	}
	
	/*
	@Override
	public Throwable fillInStackTrace() {
		return null;
	} 
	*/
}