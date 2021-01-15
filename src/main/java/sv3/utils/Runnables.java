package sv3.utils;

/*
 * Static utility class for Runnable's
 */
public class Runnables {

	private Runnables() {}
	
	public static Runnable andThen(Runnable base, Runnable andThen) {
		return () -> {
			base.run();
			andThen.run();
		};
	}
	
	public static Runnable chain(Runnable... runnables) {
		return () -> {
			for (Runnable r : runnables) {
				r.run();
			}
		};
	}

}
