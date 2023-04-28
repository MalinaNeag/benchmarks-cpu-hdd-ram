package logging;

import java.io.IOException;

/**
 * An interface for a logger that can write longs, strings, and variable argument lists of objects.
 */
public interface ILogger {

    /**
     * Writes a long value to the log.
     *
     * @param param The value to write.
     */
    void write(long param);

    /**
     * Writes a string value to the log.
     *
     * @param param The value to write.
     */
    void write(String param);

    /**
     * Writes multiple values to the log as a formatted string.
     *
     * @param values The values to write.
     */
    void write(Object ...values);

    /**
     * Writes a time measurement value to the log with the specified time unit.
     *
     * @param value The time measurement value to write.
     * @param unit The time unit used to measure the value.
     */
    void writeTime(long value, TimeUnit unit);

    /**
     * Writes a time measurement value with a specific label to the log with the specified time unit.
     *
     * @param string The label to use for the time measurement value.
     * @param value The time measurement value to write.
     * @param unit The time unit used to measure the value.
     */
    void writeTime(String string, long value, TimeUnit unit);

    /**
     * Closes the logger and releases any resources it may be holding.
     */
    void close();
}


