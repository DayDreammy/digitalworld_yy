package edu.tongji.cc.digitalworld.service;

import edu.tongji.cc.digitalworld.common.Location;
import edu.tongji.cc.digitalworld.entity.Agent;
import edu.tongji.cc.digitalworld.entity.Map;
import org.springframework.stereotype.Service;

/**
 * Main application service.
 * @author ZhangWei(Dept. of Control, TongJi University)
 * - First version.
 */
@Service("avc")
public class AppService {

    private WorldService world = new WorldService();
    private LogService logservice = new LogService();

    private int _status=0;//仿真引擎的状态
    private long count=0;//仿真引擎运行的时间
    // - 0 未启动
    // - 1 运行中
    // - 2 暂停

//    private WorldService worldservice = new WorldService();

    public AppService()
    {
        setStatus(1);
    }

//    public WorldService world()
//    {
//        return worldservice;
//    }

    public AgentService agents()
    {
        return world.getAgentService();
    }

    public MapService map()
    {
        return world.getMapService();
    }

    public Map submap(int id)
    {
        Location loc =  agents().get(id).getLocation();
        Location loc1 = new Location(loc.getX()-5, loc.getY()-2);
        Location loc2 = new Location(loc.getX(), loc.getY()+2);

        return world.getMapService().subMap(loc1,loc2);
    }

    public WorldService world(){
        return this.world;
    }

    public void evolve(){
        System.out.println("evolve ...");
        int agent_num=agents().find().size();
//        log.info("time : {}", dateFormat.format(new Date()));

        for (int i = 0; i < agent_num; i++) {
            Agent agent=agents().get(i);
            if(agent.getType() == 0){//digital car 需要involve
                agent.involve();
            }else if(agent.getType() == 1){//app car 需要update
                agent.appupdate();
            }
        }
    }

    public int Status(){
        return _status;
    }

    public String getStatus(String cmd){

        if(cmd.isEmpty()){
            return String.format(String.valueOf(_status));
        }

        else if(cmd.equals("evolve")){
            System.out.println("evolve");
            evolve();
            return String.format("evolve");
        }else if(cmd.equals("pause")){
            System.out.println("pause");
            setStatus(2);
            return String.format("pause");
        }else if(cmd.equals("resume")){
            System.out.println("resume");
            setStatus(1);
            return String.format("resume");
        }
        else {
            return cmd;
        }


    }

    public void setStatus(int status){
        this._status=status;
    }

    public void Post(String cmd){

    }

    public void tictoc(){
        count++;
    }

}
