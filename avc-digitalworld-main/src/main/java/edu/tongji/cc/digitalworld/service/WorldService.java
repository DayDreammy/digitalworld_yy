package edu.tongji.cc.digitalworld.service;

import edu.tongji.cc.digitalworld.common.Location;
import edu.tongji.cc.digitalworld.entity.Agent;
import edu.tongji.cc.digitalworld.entity.Map;
import edu.tongji.cc.digitalworld.entity.RoboTaxi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * World service, which is essentially the simulation engine.
 * @author ZhangWei(Dept. of Control, TongJi University)
 * - First version.
 */
public class WorldService {

    private MapService _map;
    private AgentService _agents;

    static private final int  _AgentNumber = 5;

    public WorldService()
    {
        _agents = new AgentService();
        _map = new MapService(_agents);
        _agents.randomize(_AgentNumber);
    }

    public AgentService getAgentService()
    {
        return _agents;
    }

    public MapService getMapService()
    {
        return _map;
    }

    public static int GetagentNumber(){
        return _AgentNumber;
    }

}
