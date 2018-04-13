import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Queue;



/**
 * 调度器类
 */
public class Dispatcher {

    private static int mode; //调度策略：1 表示FCFS策略 2 表示按优先级调度策略
    private static Queue<Process> New = new ArrayDeque<Process>(); //新建队列
    private static Queue<Process> ready = new ArrayDeque<Process>();  //就绪队列
    private static Queue<Process> blocked = new ArrayDeque<Process>(); //阻塞队列
    private static Process running;    //当前正在运行的进程
    private static SimpleDateFormat format; //时间格式化工具

    static{
        format = new SimpleDateFormat("HH:mm:ss");
    }


    /**
     * 创建进程：申请PCB，填写相关信息，挂就绪队列
     */
    public static void Create(int priorityLevel,int totalTime) throws IOException {

        Process process = new Process("new",priorityLevel,totalTime);
        New.add(process);
        System.out.println(format.format(new Date()) + " 进程" + (Process.getNowId() - 1) + "被创建");
        Print();
    }

    /**
     * 撤销进程：分别在运行、新建、就绪和阻塞队列里寻找目标进程，找到就撤销该进程
     * @param id 要撤销的进程id
     */
    public static void Terminate(int id){
        boolean isFind = false;
        if(running != null){
            running = null;
            isFind = true;
        }

        else{
            for(Process p:New){
                if(id == p.getId()){
                    New.remove(p);
                    isFind = true;
                    break;
                }
            }
            for(Process p:ready){
                if(id == p.getId()){
                    ready.remove(p);
                    isFind = true;
                    break;
                }
            }
            for(Process p:blocked){
                if(id == p.getId()){
                    blocked.remove(p);
                    isFind = true;
                    break;
                }
            }

        }
        if(isFind){
            System.out.println(format.format(new Date()) + " 进程" + id + "被撤销");
            Print();
        }
        else
            System.out.println("该进程尚未创建或者已经被撤销！");

    }

    /**
     * 阻塞进程：查看是否有进程在运行，如果有就阻塞该进程
     */
    public static void Block() {
        if(running != null){
            running.setState("blocked");
            blocked.add(running);
            System.out.println(format.format(new Date()) + " 进程" + running.getId() + "被阻塞");
            running = null;
            Print();
        }
        else
            System.out.println("没有正在运行的进程，无法阻塞！");

    }

    /**
     * 唤醒进程：查看目标进程是否在阻塞队列中，如果在就唤醒，挂就绪队列
     * @param id 要唤醒的进程id
     */
    public static void Wakeup(int id){
        boolean isFind = false;
        Process temp = null;
        for(Process p:blocked){
            if(id == p.getId()){
                isFind = true;
                temp = p;
                blocked.remove(p);
                break;
            }
        }
        if(isFind){
            temp.setState("ready");
            ready.add(temp);
            System.out.println(format.format(new Date()) + " 进程" + id + "被唤醒");
            Print();
        }
        else
            System.out.println("该进程未处于阻塞状态，无需唤醒！");

    }

    /**
     * 调度线程
     * 提供了两种调度策略供选择
     */
    public static void Schedule() throws FileNotFoundException {
        if(mode == 1)
            ScheduleByFCFS();
        else
            ScheduleByPriority();
    }


    /**
     * 从Ready队列里按照FCFS策略选择进程运行
     */
    public static void ScheduleByFCFS() throws FileNotFoundException {
        if(ready.size() > 0){
            Process front = ready.poll();
            front.setState("running");
            if (running != null) {
                running.setNowTime(running.getNowTime() + 10);
                if(running.getNowTime() < running.getTotalTime()){
                    System.out.println("保存正在运行的进程" + running.getId() + "的现场。。。。。。。");
                    running.setState("ready");
                    ready.add(running);
                }
                else{
                    System.out.println(format.format(new Date()) + " 进程" + running.getId() + "执行完毕！");
                    running = null;
                    Print();
                }
                System.out.println("按照FCFS策略在ready队列里选择新进程运行。。。。。。");

            }
            running = front;
            System.out.println("恢复新进程的现场。。。。。。\n");
            System.out.println(format.format(new Date()) + " 进程" + running.getId() + "被调度运行");
            Print();
        }
        else if(running != null){
            running.setNowTime(running.getNowTime() + 10);
            if (running.getNowTime() >= running.getTotalTime()) {
                System.out.println(format.format(new Date()) + " 进程" + running.getId() + "执行完毕！");
                running = null;
                Print();
            }
            else{
                System.out.println(format.format(new Date()) + " 进程" + running.getId() + "被调度运行");
                Print();
            }
        }
    }

    /**
     * 从Ready队列里按照优先级策略选择进程运行
     */
    public static void ScheduleByPriority() throws FileNotFoundException {
        int maxi = -1;
        Process temp = null;
        for (Process p : ready) {
            if(p.getPriorityLevel() > maxi){
                maxi = p.getPriorityLevel();
                temp = p;
            }
        }
        if(maxi != -1){
            temp.setState("running");
            if (running != null) {
                running.setNowTime(running.getNowTime() + 10);
                if(running.getNowTime() < running.getTotalTime()){
                    System.out.println("保存正在运行的进程" + running.getId() + "的现场。。。。。。。");
                    running.setState("ready");
                    ready.add(running);
                }
                else{
                    System.out.println(format.format(new Date()) + " 进程" + running.getId() + "执行完毕！");
                    running = null;
                    Print();
                }
                System.out.println("按照优先级策略在ready队列里选择新进程运行。。。。。。");
            }
            running = temp;
            ready.remove(temp);
            System.out.println("恢复新进程的现场。。。。。。\n");
            System.out.println(format.format(new Date()) + " 进程" + running.getId() + "被调度运行");
            Print();

        }
        else if(running != null){
            running.setNowTime(running.getNowTime() + 10);
            if (running.getNowTime() >= running.getTotalTime()) {
                System.out.println(format.format(new Date()) + " 进程" + running.getId() + "执行完毕！");
                running = null;
                Print();
            }
            else{
                System.out.println(format.format(new Date()) + " 进程" + running.getId() + "被调度运行");
                Print();
            }
        }
    }

    /**
     * 使某处于new状态的线程进入Ready队列
     * @param id
     */
    public static void Ready(int id){

        boolean isFind = false;
        Process temp = null;
        for(Process p:New){
            if(id == p.getId()){
                isFind = true;
                temp = p;
                New.remove(p);
                break;
            }
        }
        if(isFind){
            temp.setState("ready");
            ready.add(temp);
            System.out.println(format.format(new Date()) + " 进程" + id + "就绪");
            Print();
        }
        else
            System.out.println("该进程未处于新建状态！");
    }

    /**
     * 打印各进程状态
     */
    public static void Print(){
        System.out.println("**********当前各进程状态如下**********");
        System.out.println("新建队列：");
        if(New.size() == 0){
            System.out.println("无进程");
        }
        for(Process p:New){
            System.out.println(p);
        }
        System.out.println("正在运行的进程");
        if(running == null){
            System.out.println("无进程");
        }
        else
            System.out.println(running);
        System.out.println("阻塞队列：");
        if(blocked.size() == 0){
            System.out.println("无进程");
        }
        for(Process p:blocked){
            System.out.println(p);
        }
        System.out.println("就绪队列：");
        if(ready.size() == 0){
            System.out.println("无进程");
        }
        for(Process p:ready){
            System.out.println(p);
        }
        System.out.println("\n");
    }

    public static int getMode() {
        return mode;
    }

    public static void setMode(int mode) {
        Dispatcher.mode = mode;
    }

    public static Queue<Process> getNew() {
        return New;
    }

    public static void setNew(Queue<Process> aNew) {
        New = aNew;
    }

    public static Queue<Process> getReady() {
        return ready;
    }

    public static void setReady(Queue<Process> ready) {
        Dispatcher.ready = ready;
    }

    public static Queue<Process> getBlocked() {
        return blocked;
    }

    public static void setBlocked(Queue<Process> blocked) {
        Dispatcher.blocked = blocked;
    }

    public static Process getRunning() {
        return running;
    }

    public static void setRunning(Process running) {
        Dispatcher.running = running;
    }

    public static SimpleDateFormat getFormat() {
        return format;
    }

    public static void setFormat(SimpleDateFormat format) {
        Dispatcher.format = format;
    }


}
