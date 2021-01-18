package sv3.options;

public enum TimerUnit {

	SECONDS("s",  1_000_000_000),

	MILLIS ("ms", 1_000_000),

	MICROS ("μs", 1_000),

	NANOS  ("ns", 1);


	public static TimerUnit getDefault() {
		return MILLIS;
	}

	public final String symbol;
	// nanos / divisor = this unit
	public final long divisor;

	TimerUnit(String s, long d) {
		symbol = s;
		divisor = d;
	}

	@Override
	public String toString() {
		return symbol;
	}

}
