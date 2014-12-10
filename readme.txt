windows中 将sigar-x86-winnt.dll放到java/bin中
Linux中  将libsigar-amd64-linux.so放到java/bin中
sigar-x86-winnt.dll和libsigar-amd64-linux.so放在jar包中


项目说明:定期的(间隔为1秒)从服务器上采集系统信息,通过client(client中有个定时器)发送到server上

系统信息的计算依据:
http://blog.sina.com.cn/s/blog_439628be01018d1v.html
采用cpu利用率,内存利用率,网络利用率,线程数

获取系统信息为sigar来获取

通信部分采用mina来实现