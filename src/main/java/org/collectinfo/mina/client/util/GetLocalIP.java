package org.collectinfo.mina.client.util;

import java.net.InetAddress;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 14-12-10
 * Time: 下午6:39
 * To change this template use File | Settings | File Templates.
 */
public class GetLocalIP {
    public InetAddress getLocalIP(){
        InetAddress localIP=null;
       try{
           localIP=InetAddress.getLocalHost();
       }catch(Exception e){
           e.printStackTrace();
       }
        return localIP;
    }
    public static void main(String[] args){
       System.out.println(new GetLocalIP().getLocalIP().getHostAddress());
    }
}
