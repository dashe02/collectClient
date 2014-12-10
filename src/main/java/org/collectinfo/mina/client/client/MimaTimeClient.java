package org.collectinfo.mina.client.client;

/**
 * Created with IntelliJ IDEA.
 * User: hadoop
 * Date: 14-12-8
 * Time: 下午2:02
 * To change this template use File | Settings | File Templates.
 */
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.prefixedstring.PrefixedStringCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.collectinfo.mina.client.collectinfo.CollectSytemInfo;
import org.collectinfo.mina.client.config.ConfigParser;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
public class MimaTimeClient {
    private ConfigParser configParser=new ConfigParser();
    private final String ip=configParser.getPros().get("ip").toString();
    private final int port=Integer.parseInt(configParser.getPros().get("port").toString());
    private final int HOUR=Integer.parseInt(configParser.getPros().get("HOUR").toString());
    private final int MINUTE=Integer.parseInt(configParser.getPros().get("MINUTE").toString());
    private final int SECOND=Integer.parseInt(configParser.getPros().get("SECOND").toString());
    private final int delay=Integer.parseInt(configParser.getPros().get("delay").toString());
    public  void connect(){
        IoConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("logger",new LoggingFilter());
        connector.getFilterChain().addLast("codec",new ProtocolCodecFilter( new PrefixedStringCodecFactory(Charset.forName("UTF-8"))));
        connector.setHandler(new TimeClientHander());
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress(ip,port));
        //等待建立连接
        connectFuture.awaitUninterruptibly();
        System.out.println("连接成功");
        final IoSession session = connectFuture.getSession();
        boolean quit = false;
        Calendar cal=Calendar.getInstance();
        //每天定点执行
        cal.set(Calendar.HOUR_OF_DAY,HOUR);     //控制时
        cal.set(Calendar.MINUTE,MINUTE);           //控制分
        cal.set(Calendar.SECOND,SECOND);           //控制秒
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String jsonString=new CollectSytemInfo().getSystemInfo();
                session.write(jsonString);
            }                           //cal.getTime()为得出执行任务的时间,为今天的12:00:00
        },cal.getTime(),delay);        //设定将延时每天固定执行
        if(session!=null){
            if(session.isConnected()){
                session.getCloseFuture().awaitUninterruptibly();
            }
            connector.dispose(true);
        }
    }
    public static void main(String[] args) {
        new MimaTimeClient().connect();
    }
}