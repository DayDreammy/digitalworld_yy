package edu.tongji.cc.digitalworld.entity;

import edu.tongji.cc.digitalworld.common.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * World = Map + Agents
 *
 * @author ZhangWei(Dept. of Control, TongJi University)
 * - First version.
 */
public class World {

    private Map _map;
    private List<Agent> _agentlist;

    public World()
    {
        _agentlist = new ArrayList<Agent>();
        _map = new Map(5,5);
    }

}
