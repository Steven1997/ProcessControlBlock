/**
 * 进程实体类
 */
public class Process {

    private int id; //进程标识信息
    private String state; //进程状态
    private int priorityLevel; //进程优先级
    private int totalTime; //进程运行所需总时间
    private int nowTime; //进程已运行时间
    private int address; //进程存储地址
    private static int nowAdd; //即将分配的进程地址
    private static int nowId; //即将分配的进程id

    static {
        nowId = 1; //第一个分配的进程id
        nowAdd = 1000; //第一个分配的进程地址
    }

    public Process(String state, int priorityLevel, int totalTime) {
        this.id = nowId;
        this.state = state;
        this.priorityLevel = priorityLevel;
        this.totalTime = totalTime;
        this.address = nowAdd;
        this.nowTime = 0;
        nowId++;
        nowAdd += 4;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getNowTime() {
        return nowTime;
    }

    public void setNowTime(int nowTime) {
        this.nowTime = nowTime;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public static int getNowAdd() {
        return nowAdd;
    }

    public static void setNowAdd(int nowAdd) {
        Process.nowAdd = nowAdd;
    }

    public static int getNowId() {
        return nowId;
    }

    public static void setNowId(int nowId) {
        Process.nowId = nowId;
    }

    @Override
    public String toString() {
        return "PCB"+ id + ": state = " + state + " , priorityLevel = "
                + priorityLevel + " , totalTime = " + totalTime + " , nowTime = " + nowTime
                + " , address = " + address;
    }
}
