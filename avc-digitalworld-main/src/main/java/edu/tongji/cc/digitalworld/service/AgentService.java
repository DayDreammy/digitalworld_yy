package edu.tongji.cc.digitalworld.service;

import edu.tongji.cc.digitalworld.common.Location;
import edu.tongji.cc.digitalworld.entity.Agent;
import edu.tongji.cc.digitalworld.entity.Map;
import edu.tongji.cc.digitalworld.entity.RoboTaxi;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * AgentService manages all the agents in the application, including CRUD operations
 * and other helpful methods.
 *
 * @author ZhangWei(Dept. of Control, TongJi University)
 * - First version.
 */
public class AgentService {

    List<Agent> _agentlist = new ArrayList<Agent>();

    public void randomize(int count)
    {
        Random rnd = new Random();
        for (int i=0; i<count; i++)
        {
            Agent agent = new RoboTaxi();
            agent.setId(i);
            agent.setLocation(new Location(rnd.nextInt(MapService.MAP_WIDTH),rnd.nextInt(MapService.MAP_HEIGHT)));
            _agentlist.add(agent);
        }
    }

    public List<Agent> find()
    {
        return _agentlist;
    }

    public Agent get(int id)
    {
        return _agentlist.get(id);
    }

}
