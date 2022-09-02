package edu.tongji.cc.digitalworld.service;

/**
 * World service, which is essentially the simulation engine.
 * @author ZhangWei(Dept. of Control, TongJi University)
 * - First version.
 */
public class WorldService {

    private MapService _map;
    private AgentService _agents;

    static private final int  _AgentNumber = 5;
    static private final int  _ObstacleNumber = 10;

    public WorldService()
    {
        _agents = new AgentService();
        _map = new MapService(_agents);
        _agents.randomize(_AgentNumber);
        _map.randomize(_ObstacleNumber);
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
