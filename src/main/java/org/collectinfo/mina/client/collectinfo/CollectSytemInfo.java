package org.collectinfo.mina.client.collectinfo;

import org.collectinfo.mina.client.util.GetLocalIP;
import org.hyperic.sigar.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 14-12-10
 * Time: 下午2:27
 * To change this template use File | Settings | File Templates.
 */
public class CollectSytemInfo {
    private final long constant=1024L;
    private final GetLocalIP getLocalIP=new GetLocalIP();
    public Map<String,Float> getMemory(){
        float totalMem=0f;
        float usedMem=0f;
        float freeMem=0f;
        float totalSwp=0f;
        float usedSwp=0f;
        float freeSwp=0f;
        Map<String,Float> memInfoMap=new HashMap<String, Float>();
        try{
            Sigar sigar=new Sigar();
            Mem mem=sigar.getMem();
            Swap swap=sigar.getSwap();
            totalMem=mem.getTotal()/constant;
            usedMem=mem.getUsed()/constant;
            freeMem=mem.getFree()/constant;
            totalSwp=swap.getTotal()/constant;
            usedSwp=swap.getUsed()/constant;
            freeSwp=swap.getFree()/constant;
            memInfoMap.put(SystemParameter.totalMem,totalMem);
            memInfoMap.put(SystemParameter.usedMem,usedMem);
            memInfoMap.put(SystemParameter.freeMem,freeMem);
            memInfoMap.put(SystemParameter.totalSwp,totalSwp);
            memInfoMap.put(SystemParameter.usedSwp,usedSwp);
            memInfoMap.put(SystemParameter.freeSwp,freeSwp);
        }catch (Exception e){
            e.printStackTrace();
        }
        return memInfoMap;
    }
    public Map<String,String> getCpu(){
        String cpuUsed=null;
        String cpuSysUsed=null;
        String cpuWait=null;
        String cpuError=null;
        String cpuIdle=null;
        String totalUsed=null;
        Map<String,String> cpuInfoMap=new HashMap<String, String>();
        try{
           Sigar sigar=new Sigar();
            CpuInfo infos[]=sigar.getCpuInfoList();
            CpuPerc cpuList[]=null;
            cpuList=sigar.getCpuPercList();
                cpuUsed=CpuPerc.format(cpuList[0].getUser());
                cpuSysUsed=CpuPerc.format(cpuList[0].getSys());
                cpuWait=CpuPerc.format(cpuList[0].getWait());
                cpuError=CpuPerc.format(cpuList[0].getNice());
                cpuIdle=CpuPerc.format(cpuList[0].getIdle());
                totalUsed=CpuPerc.format(cpuList[0].getCombined());
                cpuInfoMap.put(SystemParameter.cpuUsed,cpuUsed);
                cpuInfoMap.put(SystemParameter.cpuSysUsed,cpuSysUsed);
                cpuInfoMap.put(SystemParameter.cpuWait,cpuWait);
                cpuInfoMap.put(SystemParameter.cpuError,cpuError);
                cpuInfoMap.put(SystemParameter.cpuIdle,cpuIdle);
                cpuInfoMap.put(SystemParameter.totalUsed,totalUsed);
        }catch (Exception e){
            e.printStackTrace();
        }
        return cpuInfoMap;
    }
    public List<Map<String,Long>> getNetInfo(){
        String netName=null;  //网络设备名
        String ipAddr=null;   //IP地址
        String subNet=null;    //子网掩码
        long receiveTotalPacket=0;
        long sendTotalPacket=0;
        long receiveTotalByte=0;
        long sendTotalByte=0;
        long receiveErrorPacket=0;
        long sendErrorPacket=0;
        long receiveQuitPacket=0;
        long sendQuitPacket=0;
        Map<String,Long> netInfoMap=new HashMap<String, Long>();
        List<Map<String,Long>> netInfos=new ArrayList<Map<String, Long>>();
        try{
         Sigar sigar=new Sigar();
         String ifNames[]=sigar.getNetInterfaceList();
         for(int i=0;i<ifNames.length;i++){
         String name=ifNames[i];
         NetInterfaceConfig ifConfig=sigar.getNetInterfaceConfig(name);
         name=ifConfig.getName();
         ipAddr=ifConfig.getAddress();
         subNet=ifConfig.getNetmask();
         if((ifConfig.getFlags()&1L)<=0L){
             System.out.println("!IFF_UP...skipping getNetInterfaceStat");
             continue;
         }
         NetInterfaceStat ifStat=sigar.getNetInterfaceStat(name);
         receiveTotalPacket=ifStat.getRxPackets();
         sendTotalPacket=ifStat.getTxPackets();
         receiveTotalByte=ifStat.getRxBytes();
         sendTotalByte=ifStat.getTxBytes();
         receiveErrorPacket=ifStat.getRxErrors();
         sendErrorPacket=ifStat.getTxErrors();
         receiveQuitPacket=ifStat.getRxDropped();
         sendQuitPacket=ifStat.getTxDropped();
         netInfoMap.put(SystemParameter.receiveTotalPacket,receiveTotalPacket);
         netInfoMap.put(SystemParameter.sendTotalPacket,sendTotalPacket);
         netInfoMap.put(SystemParameter.receiveTotalByte,receiveTotalByte);
         netInfoMap.put(SystemParameter.sendTotalByte,sendTotalByte);
         netInfoMap.put(SystemParameter.receiveErrorPacket,receiveErrorPacket);
         netInfoMap.put(SystemParameter.sendErrorPacket,sendErrorPacket);
         netInfoMap.put(SystemParameter.receiveQuitPacket,receiveQuitPacket);
         netInfoMap.put(SystemParameter.sendQuitPacket,sendQuitPacket);
         netInfos.add(netInfoMap);
         }
        }catch (Exception e){
            e.printStackTrace();
        }
        return netInfos;
    }
    public int getThreadCount(){
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        ThreadGroup topGroup = group;
        // 遍历线程组树，获取根线程组
        while (group != null) {
            topGroup = group;
            group = group.getParent();
        }
        // 激活的线程数加倍
        int estimatedSize = topGroup.activeCount() * 2;
        Thread[] slackList = new Thread[estimatedSize];
         // 获取根线程组的所有线程
        int actualSize = topGroup.enumerate(slackList);
         // copy into a list that is the exact size
        Thread[] list = new Thread[actualSize];
        System.arraycopy(slackList, 0, list, 0, actualSize);
        System.out.println("Thread list size == " + list.length);
        return list.length;
    }
    public String getSystemInfo(){
       String systemInfo=null;
       float cpu=getCpuUse();  //cpu使用率
       float mem=getMemoryUse();   //内存使用率
       float net=getNetUse();
       int thread=getThreadCount();
       String localIP=getLocalIP.getLocalIP().getHostAddress();
       //systemInfo="{\""+localIP+"\":{\"cpu\":\""+cpu+"\",\"mem\":\""+mem+"\",\"net\":\""+net+"\",\"thread\":\""+thread+"\"}}";
       systemInfo=localIP+":"+cpu+" "+mem+" "+net+" "+thread;
       return systemInfo;
    }
    public float getCpuUse(){      //使用cpu利用率
        String cpuStr=null;
        String cpu=getCpu().get(SystemParameter.totalUsed);
        if(cpu.contains("%")){
          cpuStr=cpu.substring(0,cpu.indexOf("%"));
        }
       return Float.parseFloat(cpuStr)/100;
    }
    public float getMemoryUse(){     //计算内存使用率
        float usedMem=getMemory().get(SystemParameter.usedMem);
        float totalMem=getMemory().get(SystemParameter.totalMem);
        return usedMem/totalMem;
    }
    public float getNetUse(){     //计算网络使用率
        List<Map<String,Long>> list=getNetInfo();
        float sum=0;
        for(Map<String,Long> map:list){
            sum+=Float.parseFloat(map.get(SystemParameter.receiveTotalPacket).toString())+Float.parseFloat(map.get(SystemParameter.sendTotalPacket).toString())/2;
        }
        return sum/list.size();
    }
    public static void main(String[] args){
        CollectSytemInfo col=new CollectSytemInfo();
        System.out.println(col.getSystemInfo());
    }
}
