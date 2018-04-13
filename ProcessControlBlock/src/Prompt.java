import java.io.*;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.TimerTask;

/**
 * 定时器类，定时向用户询问进程管理的操作
 */
public class Prompt extends TimerTask {

    @Override
    public void run(){

        Scanner input = new Scanner(System.in); //定时向用户询问操作
        System.out.println("**********请按照需要输入以下指令**********");
        System.out.println("1.创建进程 格式：C 进程优先级 进程运行所需总时间");
        System.out.println("2.使进程就绪 格式：R 进程id");
        System.out.println("3.撤销进程 格式：T 进程id");
        System.out.println("4.阻塞进程 格式：B");
        System.out.println("5.唤醒进程 格式：W 进程id\n\n");

        String str = input.nextLine();
        char op = str.charAt(0);
        switch (op) {
            case 'C':{
                int idx = str.indexOf(' ');
                int priorityLevel = Integer.parseInt(str.substring(idx + 1,str.lastIndexOf(' ')));
                int idx2 = str.lastIndexOf(' ');
                int totalTime = Integer.parseInt(str.substring(idx2 + 1));
                try {
                    Dispatcher.Create(priorityLevel,totalTime);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
            case 'R': {
                int idx = str.lastIndexOf(' ');
                int id = Integer.parseInt(str.substring(idx + 1));
                Dispatcher.Ready(id);
                break;
            }

            case 'T': {
                int idx = str.lastIndexOf(' ');
                int id = Integer.parseInt(str.substring(idx + 1));
                Dispatcher.Terminate(id);
                break;
            }

            case 'B': {
                Dispatcher.Block();
                break;
            }

            case 'W': {
                int idx = str.lastIndexOf(' ');
                int id = Integer.parseInt(str.substring(idx + 1));
                Dispatcher.Wakeup(id);
                break;
            }
            default:
                System.out.println("对不起，命令格式错误！");
        }
    }
}

