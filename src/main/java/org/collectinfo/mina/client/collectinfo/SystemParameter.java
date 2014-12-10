package org.collectinfo.mina.client.collectinfo;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 14-12-10
 * Time: 下午3:42
 * To change this template use File | Settings | File Templates.
 */
public class SystemParameter {
    public static String totalMem="totalMem";        //内存总量
    public static String usedMem="usedMem";           //当前内存使用量
    public static String freeMem="freeMem";             //当前内存剩余量
    public static String totalSwp="totalSwp";           //交换区总量
    public static String usedSwp="usedSwp";             //当前交换区使用量
    public static String freeSwp="freeSwp";               //当前交换区剩余量
    public static String cpuUsed="cpuUsed";              //用户使用率
    public static String cpuSysUsed="cpuSysUsed";          //系统使用率
    public static String cpuWait="cpuWait";                 //当前等待率
    public static String cpuError="cpuError";               //当前错误率
    public static String cpuIdle="cpuIdle";                 //当前空闲率
    public static String totalUsed="totalUsed";              //总的使用率
    public static String receiveTotalPacket="receiveTotalPacket";  //接收到的总包裹数
    public static String sendTotalPacket="sendTotalPacket";         //发送的总包裹数
    public static String receiveTotalByte="receiveTotalByte";       //接收到的总字节数
    public static String sendTotalByte="sendTotalByte";              //发送的总字节数
    public static String receiveErrorPacket="receiveErrorPacket";    //接收到的错误包数
    public static String sendErrorPacket="sendErrorPacket";           //发送数据包时的错误数
    public static String receiveQuitPacket="receiveQuitPacket";      //接收时丢弃的包数
    public static String sendQuitPacket="sendQuitPacket";            //发送时丢弃的包数
}
