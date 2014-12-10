windows中 将sigar-x86-winnt.dll放到java/bin中
Linux中  将libsigar-amd64-linux.so放到java/bin中
sigar-x86-winnt.dll和libsigar-amd64-linux.so放在jar包中


项目说明:定期的(间隔为1秒)从服务器上采集系统信息,通过client(client中有个定时器)发送到server上