package sv3.utils;

/*
 * Static utility class for Runnable's
 */
public class Runnables {

	private Runnables() {}

	// Return a runnable which executes the first Runnable, and then the second Runnable
	public static Runnable andThen(Runnable base, Runnable andThen) {
		return () -> {
			base.run();
			andThen.run();
		};
	}

	// Chain multiple Runnables into a single Runnable
	public static Runnable chain(Runnable... runnables) {
		return () -> {
			for (Runnable r : runnables) {
				r.run();
			}
		};
	}

}
