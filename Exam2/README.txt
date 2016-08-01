1.编译
mvn clean compile
2.开启服务器
mvn exec:java -Dexec.mainClass="com.hand.MyServer" -Dexec.args="arg0 arg1 arg2"
3.开启客户端  文件放在工程目录下
mvn exec:java -Dexec.mainClass="com.hand.MyClient" -Dexec.args="arg0 arg1 arg2"