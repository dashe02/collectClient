package org.collectinfo.mina.client;

import org.collectinfo.mina.client.client.MimaTimeClient;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 14-12-10
 * Time: 下午5:15
 * To change this template use File | Settings | File Templates.
 */
public class StartClient {
    public static void main(String[] args){
        new MimaTimeClient().connect();
    }
}
