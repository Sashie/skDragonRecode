package me.sashie.skdragon.debug;

import me.sashie.skdragon.SkDragonRecode;

/**
 * Created by Sashie on 12/12/2016.
 */

@SuppressWarnings("serial")
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