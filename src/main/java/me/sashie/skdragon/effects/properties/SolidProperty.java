package me.sashie.skdragon.effects.properties;

public class SolidProperty {

    private boolean solid;

    /**
     * Whether all particles are played at the same time or one at a time
     * <p>
     * Certain effects shouldn't use this
     */
    public boolean isSolid() {
        return this.solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

}
