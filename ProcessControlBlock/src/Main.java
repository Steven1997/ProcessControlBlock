import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Timer;

/**
 * 主测试类
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {

        Scanner in = new Scanner(System.in);
        System.out.println("请选择时间片轮转调度策略，输入对应编号：\n1.FCFS\n2.按优先级调度，优先级越大的越先分配时间片");
        int mode = -1;
        mode = in.nextInt(); //选择调度策略
        if(mode != -1){
            Dispatcher.setMode(mode);
            Timer timer = new Timer();
            timer.schedule(new Prompt(),0,5000); //定时向用户询问操作
            Thread th = new Thread(new ScheduleThread());
            th.start();
            while(true){
                Thread.sleep(1000);
            }
        }


    }
}
