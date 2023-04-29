package logging;

/**
 * The ConsoleLogger class implements the ILogger interface and provides
 * a logger that writes log messages to the console.
 */
public class ConsoleLogger implements ILogger {

    /**
     * Writes the specified long parameter to the console.
     *
     * @param parameter the long parameter to be written to the console.
     */
    @Override
    public void write(long parameter) {
        System.out.println(parameter);
    }

    /**
     * Writes the specified string parameter to the console.
     *
     * @param parameter the string parameter to be written to the console.
     */
    @Override
    public void write(String parameter) {
        System.out.println(parameter);
    }

    /**
     * Writes the specified array of objects to the console.
     *
     * @param values the array of objects to be written to the console.
     */
    @Override
    public void write(Object... values) {
        for(Object obj : values){
            System.out.print(obj + " ");
        }
        System.out.println();
    }

    /**
     * Writes the specified time parameter and time unit to the console.
     *
     * @param parameter the time parameter to be written to the console.
     * @param unit the time unit to be written to the console.
     */
    @Override
    public void writeTime(long parameter, TimeUnit unit) {
        System.out.println(unit.timeConversion(parameter) + " " + unit);
    }

    /**
     * Writes the specified message, time parameter, and time unit to the console.
     *
     * @param s the message to be written to the console.
     * @param time the time parameter to be written to the console.
     * @param unit the time unit to be written to the console.
     */
    @Override
    public void writeTime(String s, long time, TimeUnit unit) {
        System.out.println(s + " " + unit.timeConversion(time) + " " + unit);
    }

    /**
     * Closes the logger.
     */
    @Override
    public void close() {
        // do nothing
    }

}