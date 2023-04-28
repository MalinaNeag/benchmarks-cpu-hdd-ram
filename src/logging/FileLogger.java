package logging;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger implements ILogger{
    private BufferedWriter writer;

    public FileLogger(String filename) {
        try {
            writer = new BufferedWriter(new FileWriter(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(long value) {
        try {
            writer.write(String.valueOf(value));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeTime(long parameter, TimeUnit unit) {
        try {
            writer.write(String.valueOf(unit.timeConversion(parameter)));
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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



    public void write(String value) {
        try {
            writer.write(value);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
