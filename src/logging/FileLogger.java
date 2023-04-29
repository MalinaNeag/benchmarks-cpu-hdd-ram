/**
 * The FileLogger class implements the ILogger interface and provides
 * a logger that writes to a file specified by the filename parameter.
 */
package logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements ILogger {

    private BufferedWriter writer;

    /**
     * Constructs a FileLogger object with the specified filename.
     *
     * @param filename the name of the file to write to
     */
    public FileLogger(String filename) {
        try {
            writer = new BufferedWriter(new FileWriter(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a long value to the log file.
     *
     * @param value the long value to write
     */
    public void write(long value) {
        try {
            writer.write(String.valueOf(value));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a time value to the log file using the specified time unit.
     *
     * @param parameter the time value to write
     * @param unit the time unit to use
     */
    @Override
    public void writeTime(long parameter, TimeUnit unit) {
        try {
            writer.write(String.valueOf(unit.timeConversion(parameter)));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a message with a time value to the log file using the specified time unit.
     *
     * @param s the message to write
     * @param time the time value to write
     * @param unit the time unit to use
     */
    @Override
    public void writeTime(String s, long time, TimeUnit unit) {
        try {
            writer.write(s);
            writer.write(String.valueOf(unit.timeConversion(time)));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a string value to the log file.
     *
     * @param value the string value to write
     */
    public void write(String value) {
        try {
            writer.write(value);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a list of objects to the log file.
     *
     * @param values the objects to write
     */
    public void write(Object... values) {
        StringBuilder builder = new StringBuilder();
        for (Object value : values) {
            builder.append(value.toString()).append(" ");
        }
        try {
            writer.write(builder.toString().trim());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Closes the file writer.
     */
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}