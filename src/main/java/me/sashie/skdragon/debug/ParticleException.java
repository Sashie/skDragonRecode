package me.sashie.skdragon.debug;

import me.sashie.skdragon.SkDragonRecode;

public class ParticleException extends RuntimeException {

	public ParticleException(String error, SkriptNode skriptNode) {
		//super(error);
		super(error, null, true, false);
		SkDragonRecode.error(error, skriptNode);
	}

}