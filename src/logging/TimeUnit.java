package logging;

/**
 * The TimeUnit enum provides time unit conversions for use with logging.
 * It defines the conversion factors for nanoseconds, microseconds, milliseconds, and seconds.
 */
public enum TimeUnit {
    Nano(1L),
    Micro(1_000L),
    Milli(1_000_000L),
    Sec(1_000_000_000L);

    private final double factor;

    /**
     * Constructs a TimeUnit object with a given conversion factor.
     *
     * @param factor the conversion factor for the time unit.
     */
    TimeUnit(double factor) {
        this.factor = factor;
    }

    /**
     * Converts a time value in nanoseconds to the time unit represented by this enum constant.
     *
     * @param nanos the time value in nanoseconds to be converted.
     * @return the converted time value.
     */
    public double timeConversion(long nanos) {
        return nanos / factor;
    }
}