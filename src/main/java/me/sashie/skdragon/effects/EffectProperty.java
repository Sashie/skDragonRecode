package me.sashie.skdragon.effects;

/**
 * All skDragon particle effects have certain properties that can be interacted
 * with such as the location or the type of particle it uses
 * <p>
 * Some effects have special properties that aren't necessarily available to
 * other effects
 * <p>
 * This enum is used to permit an expression to use the correct property for an
 * effect otherwise an error will report that said effect can't use the property
 * <p>
 * Any property not listed here is assumed to be used by all effects
 */
public enum EffectProperty {
	 
	STYLE,

	// --> IMAGE STUFF
	FILE,

	// <-- IMAGE STUFF

	// --> ITEM STUFF
	ITEM,
	// <-- ITEM STUFF

	TEXT,
	WINGS,

	SOLID_SHAPE,
	AUTO_FACE,
	ROTATIONAL_PLANE,
	AUTO_ROTATE,


	DENSITY,
	EXTRA,
	RADIUS,
	BOOLEAN,
	TIMESPAN,


	XYZ_ANGULAR_VELOCITY,


	AXIS,

	/**
	 *
	 */
	DISPLACEMENT;


	public String getName() {
		return toString().toLowerCase().replace("_", " ");
	}


}