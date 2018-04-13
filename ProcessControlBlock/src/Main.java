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
        int mode = -1;
        while(mode != 1 && mode != 2){ //请用户选择调度策略：FCFS或者按优先级调度
            if(mode != -1)
                System.out.println("输入不合法！");
            System.out.println("请选择时间片轮转调度策略，输入对应编号：\n1.FCFS\n2.按优先级调度，优先级越大的越先分配时间片");
            mode = in.nextInt();
        }
        if(mode == 1)
            System.out.println("你选择了FCFS调度策略！");
        else
            System.out.println("你选择了按优先级调度！");
        Dispatcher.setMode(mode);
        Timer timer = new Timer();
        timer.schedule(new Prompt(),0,5000); //定时器每隔5秒向用户询问一次操作
        Thread th = new Thread(new ScheduleThread()); //启动一个线程来模拟时间片轮转调度
        th.start();
        while(true){  //确保主线程不退出
                Thread.sleep(1000);
        }



    }
}
