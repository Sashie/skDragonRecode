package me.sashie.skdragon.effects;

/**
 * All skDragon particle effects have certain properties that can be interacted
 * with such as the location or the type of particle it uses
 * 
 * Some effects have special properties that aren't necessarily available to
 * other effects
 * 
 * This enum is used to permit an expression to use the correct property for an
 * effect otherwise an error will report that said effect can't use the property
 * 
 * Any property not listed here is assumed to be used by all effects
 */
public enum EffectProperty {
	/**
	 * 
	 */

	STYLE,

	// --> IMAGE STUFF
	FILE,
	// <-- IMAGE STUFF
	
	// --> ITEM STUFF
	ITEM,
	// <-- ITEM STUFF

	TEXT,
	WINGS,

	STEP_TYPES,
	AUTO_FACE,
	ROTATIONAL_PLANE,
	
	DENSITY,
	EXTRA,
	RADIUS,
	BOOLEAN,
	TIMESPAN,

	ROTATE_VELOCITY,
	
	AXIS,

	/**
	 * 
	 */
	DISPLACEMENT,
	SWING_STEP,
	ORBIT;

	public String getName() {
		return toString().toLowerCase().replace("_", " ");
	}


}