package edu.tongji.cc.digitalworld.service;

import edu.tongji.cc.digitalworld.common.Location;
import edu.tongji.cc.digitalworld.entity.Agent;
import edu.tongji.cc.digitalworld.entity.Map;

import java.util.List;

/**
 * Map management.
 *
 * @author ZhangWei(Dept. of Control, TongJi University)
 * - First version.
 */
public class MapService {
    public static final int MAP_WIDTH = 50;
    public static final int MAP_HEIGHT = 50;

    private short[][] cell = new short[MAP_WIDTH][MAP_HEIGHT];
    AgentService _agents;

    public MapService(AgentService agents)
    {
        this._agents = agents;
    }

    public Map subMap(Location loc1, Location loc2)
    {
        int width = Math.abs(loc1.getX() - loc2.getX());
        int height = Math.abs(loc1.getY() - loc2.getY());
        Map submap = new Map(width, height);
        // TODO
        return submap;
    }

    public void randomize()
    {

    }
}
