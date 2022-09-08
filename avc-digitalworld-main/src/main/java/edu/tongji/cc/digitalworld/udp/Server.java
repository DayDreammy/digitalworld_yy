package edu.tongji.cc.digitalworld.udp;

import edu.tongji.cc.digitalworld.common.Location;
import edu.tongji.cc.digitalworld.entity.Agent;
import edu.tongji.cc.digitalworld.service.AgentService;
import edu.tongji.cc.digitalworld.service.AppService;
import edu.tongji.cc.digitalworld.service.MapService;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

import static edu.tongji.cc.digitalworld.service.MapService.getMapWidth;

public class Server extends Thread{

    private AgentService _agents;

    public Server(AgentService agent) {
        this._agents = agent;
    }
    @Override
    public  void run() {
        DatagramSocket ds = null; // 监听指定端口
        try {
            ds = new DatagramSocket(8888);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        System.out.println("server is running...");

        boolean wait_car=true;
        Agent app_car = new Agent();//备用app车
        int app_car_id=0;
        for (;;) {
            // 接收:
//            byte[] buffer = new byte[1024];
            byte[] buffer = new byte[32];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try {
                ds.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String cmd = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);

            //以c为分割字符
            String[] splitstr=cmd.split(" ");
//            for(String res : splitstr){
//                System.out.println(res);
//            }

            System.out.println("*****************************");
            final String head = "INS";


            if(wait_car){
                //报头验证，创建新车
                if(splitstr[0].equals(head)){
                    int x = Double.valueOf(splitstr[1]).intValue();
                    int y = Double.valueOf(splitstr[2]).intValue();
                    int dir = Integer.valueOf(splitstr[5]);
                    //处理异常值
                    final int max_x=MapService.getMapHeight()-1;
                    final int max_y=MapService.getMapWidth()-1;
                    x=Math.max(x,0);
                    x=Math.min(x,max_x);
                    y=Math.max(y,0);
                    y=Math.min(y,max_y);

                    app_car_id=_agents.find().size();
                    app_car.setId(app_car_id);//id就是当前list大小，相当于加到最后
                    app_car.setLocation(new Location(x,y));//设置位置
                    app_car.setDirection(dir);//设置朝向
                    app_car.setType(1);//设置类型
                    _agents.add(app_car);//添加到列表
                    wait_car=false;//车已经添加进来了，状态调整
                }
            }else{
                //app car 已经创建
                //报头验证，更新car的位置信息
                if(splitstr[0].equals(head)){
                    int x = Double.valueOf(splitstr[1]).intValue();
                    int y = Double.valueOf(splitstr[2]).intValue();
                    int dir = Integer.valueOf(splitstr[5]);
                    //处理异常值
                    x=Math.max(x,0);
                    y=Math.max(y,0);

                    app_car=_agents.get(app_car_id);
                    app_car.setLocation(new Location(x,y));//设置位置
                    app_car.setDirection(dir);//设置朝向
                }
            }
            // 发送:
            String resp = "core received ";
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            switch (cmd) {
//                case "date":
//                    resp = LocalDate.now().toString();
//                    break;
//                case "time":
//                    resp = LocalTime.now().withNano(0).toString();
//                    break;
//                case "datetime":
//                    resp = LocalDateTime.now().withNano(0).toString();
//                    break;
//                case "weather":
//                    resp = "sunny, 10~15 C.";
//                    break;
//            }
            System.out.println( " >>> " +resp+cmd  );
            packet.setData(resp.getBytes(StandardCharsets.UTF_8));
            try {
                ds.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
