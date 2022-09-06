package edu.tongji.cc.digitalworld.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Server extends Thread{
    @Override
    public  void run() {
        DatagramSocket ds = null; // 监听指定端口
        try {
            ds = new DatagramSocket(8888);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        System.out.println("server is running...");
        for (;;) {
            // 接收:
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
                ds.receive(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String cmd = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
            // 发送:
            String resp = "bad command";
            switch (cmd) {
                case "date":
                    resp = LocalDate.now().toString();
                    break;
                case "time":
                    resp = LocalTime.now().withNano(0).toString();
                    break;
                case "datetime":
                    resp = LocalDateTime.now().withNano(0).toString();
                    break;
                case "weather":
                    resp = "sunny, 10~15 C.";
                    break;
            }
            System.out.println(cmd + " >>> " + resp);
            packet.setData(resp.getBytes(StandardCharsets.UTF_8));
            try {
                ds.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
