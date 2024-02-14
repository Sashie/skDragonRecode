package me.sashie.skdragon.api.debug;

/**
 * Represents a runtime exception that is thrown if a bukkit version is not compatible with this library
 * <p>
 * This class is part of the <b>ParticleEffect Library</b> and follows the same usage conditions
 *
 * @author DarkBlade12
 * @since 1.5
 */
public class VersionIncompatibleException extends RuntimeException {
	private static final long serialVersionUID = 3203085387160737484L;

	/**
	 * Construct a new version incompatible exception
	 *
	 * @param message Message that will be logged
	 * @param cause   Cause of the exception
	 */
	public VersionIncompatibleException(String message, Throwable cause) {
		super(message, cause);
	}

}