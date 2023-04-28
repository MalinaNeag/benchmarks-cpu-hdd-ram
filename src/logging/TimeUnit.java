package logging;

public enum TimeUnit {
    Nano(1L),
    Micro(1_000L),
    Milli(1_000_000L),
    Sec(1_000_000_000L);

    private final double factor;

    TimeUnit(double factor) {
        this.factor = factor;
    }

    public double timeConversion(long nanos) {
        return (double)(nanos / factor);
    }
}