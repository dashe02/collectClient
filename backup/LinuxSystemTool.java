package org.collectinfo.mina.client.collectinfo;

import org.collectinfo.mina.client.config.ConfigParser;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 14-12-2
 * Time: 下午10:11
 * To change this template use File | Settings | File Templates.
 */
public class LinuxSystemTool implements CollectSystemInfo{
   private static final ConfigParser config=new ConfigParser();
   private static final String meminfoPath=config.getPros().get("meminfoPath").toString();
   private static final String statinfoPath=config.getPros().get("statinfoPath").toString();
   public  String getMemInfo()
   {
       String memInfo=null;
       File file=null;
       BufferedReader br=null;
       int[] result=null;
       try{
       file=new File(meminfoPath);
       br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
       result=new int[4];
       String str=null;
       StringTokenizer token=null;
       while((str=br.readLine())!=null){
           token=new StringTokenizer(str);
           if(!token.hasMoreTokens())
               continue;
           str=token.nextToken();
           if(!token.hasMoreTokens())
           continue;
           if(str.equalsIgnoreCase("MemTotal:"))
               result[0]=Integer.parseInt(token.nextToken());
           else if(str.equalsIgnoreCase("MemFree:"))
               result[1]=Integer.parseInt(token.nextToken());
           else if (str.equalsIgnoreCase("SwapTotal:"))
               result[2]=Integer.parseInt(token.nextToken());
           else if(str.equalsIgnoreCase("SwapFree:"))
               result[3]=Integer.parseInt(token.nextToken());
       }
       }catch (Exception e){
           e.printStackTrace();
       }
       memInfo="{memInfo:{MemTotal:"+result[0]+",MemFree:"+result[1]+",SwapTotal:"+result[2]+",SwapFree:"+result[3]+"}}";
       return memInfo;
   }
    public  float getCpuInfo()
    {
        File file=null;
        BufferedReader br=null;
        StringTokenizer token=null;
        int user1=0;
        int nice1=0;
        int sys1=0;
        int idle1=0;
        try{
        file=new File(statinfoPath);
        br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        token=new StringTokenizer(br.readLine());
        token.nextToken();
        user1=Integer.parseInt(token.nextToken());
        nice1=Integer.parseInt(token.nextToken());
        sys1=Integer.parseInt(token.nextToken());
        idle1=Integer.parseInt(token.nextToken());
        Thread.sleep(1000);
        br=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        token=new StringTokenizer(br.readLine());
        token.nextToken();
        }catch (Exception e){
         e.printStackTrace();
        }
        int user2=Integer.parseInt(token.nextToken());
        int nice2=Integer.parseInt(token.nextToken());
        int sys2=Integer.parseInt(token.nextToken());
        int idle2=Integer.parseInt(token.nextToken());
        return (float)((user2+sys2+nice2)-(user1+sys1+nice1))/(float)((user2+nice2+sys2+idle2)-(user1+nice1+sys1+idle1));
    }
	public static void main(String[] args) throws Exception{
    }
}
