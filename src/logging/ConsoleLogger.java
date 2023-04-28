package logging;

public class ConsoleLogger implements ILogger{

    @Override
    public void write(long parameter) {
        System.out.println(parameter);
    }

    @Override
    public void write(String parameter) {
        System.out.println(parameter);

    }

    @Override
    public void write(Object... values) {
        for(Object obj : values){
            System.out.print(obj + " ");
        }

        System.out.println();


    }


    @Override
    public void writeTime(long parameter, TimeUnit unit) {
        System.out.println(unit.timeConversion(parameter) + " " + unit);
    }

    @Override
    public void writeTime(String s, long time, TimeUnit unit) {
        System.out.println(s + " " + unit.timeConversion(time) + " " + unit);
    }

    @Override
    public void close() {
        // do nothing

    }

}
