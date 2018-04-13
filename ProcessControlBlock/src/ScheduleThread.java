import java.io.FileNotFoundException;

/**
 * 模拟时间片轮转调度的线程，每隔10秒在ready队列里按照相应的调度策略选择一个进程运行
 */
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
