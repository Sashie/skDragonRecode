package me.sashie.skdragon.api.debug;

import me.sashie.skdragon.SkDragonRecode;


public class ParticleEffectException extends RuntimeException {

	public ParticleEffectException(String error) {
		//super(error);
		super(error, null, true, false);
		//SKDragonAddon.error(error);
	}

	public ParticleEffectException(String error, SkriptNode skriptNode) {
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