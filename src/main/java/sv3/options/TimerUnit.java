package sv3.options;

public enum TimerUnit {

	SECONDS("s",  1_000_000_000),

	MILLIS ("ms", 1_000_000),

	MICROS ("μs", 1_000),

	NANOS  ("ns", 1);


	public static TimerUnit getDefault() {
		return MILLIS;
	}

	private String symbol;
	// nanos / divisor = this unit
	private long divisor;

	private TimerUnit(String s, long d) {
		symbol = s;
		divisor = d;
	}

	public long divisor() {
		return divisor;
	}

	@Override
	public String toString() {
		return symbol;
	}

}
