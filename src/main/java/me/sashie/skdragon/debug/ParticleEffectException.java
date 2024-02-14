package me.sashie.skdragon.debug;

public class ParticleEffectException extends RuntimeException {

    public ParticleEffectException(String error) {
        //super(error);
        super(error, null, true, false);
        //SKDragonAddon.error(error);
    }

}