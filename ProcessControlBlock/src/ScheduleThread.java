import java.io.FileNotFoundException;

public class ScheduleThread implements Runnable {
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Dispatcher.Schedule();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
