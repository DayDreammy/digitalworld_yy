package edu.tongji.cc.digitalworld.service;

import edu.tongji.cc.digitalworld.udp.Server;

/**
 * World service, which is essentially the simulation engine.
 * @author ZhangWei(Dept. of Control, TongJi University)
 * - First version.
 */
public class WorldService {

    private MapService _map;
    private AgentService _agents;

    static private final int  _AgentNumber = 3;
    static private final int  _ObstacleNumber = 10;

    public WorldService()
    {
        _agents = new AgentService();
        _map = new MapService(_agents);
        _map.randomize(_ObstacleNumber);//初始化地图，随机生成障碍物
        _agents.randomize(_AgentNumber);//初始化Agent,生成taxi的初始位置和目标位置
        MapService.drawMap();//画出地图，包括障碍物，起点、终点和路径
        Thread udp=new Server();
        udp.start();//启动udp监听
    }

    public AgentService getAgentService()
    {
        return _agents;
    }

    public MapService getMapService()
    {
        return _map;
    }

    public static int getAgentNumber(){
        return _AgentNumber;
    }

    public static int getObstacleNumber(){
        return _ObstacleNumber;
    }



}
