package edu.tongji.cc.digitalworld.api;

import edu.tongji.cc.digitalworld.entity.Agent;
import edu.tongji.cc.digitalworld.entity.Map;
import edu.tongji.cc.digitalworld.service.AppService;
import edu.tongji.cc.digitalworld.service.MapService;
import edu.tongji.cc.digitalworld.service.WorldService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Restful interface for RoboTaxi service。
 *
 * @author ZhangWei(Dept. of Control, TongJi University)
 * - First version.
 */
@RestController
@CrossOrigin
@RequestMapping(value="/api/world")
//http://localhost:8080/api/world/
public class RoboTaxiController {

    @Resource(name="avc")
    final AppService avc;

    public RoboTaxiController(AppService avc)
    {
        this.avc = avc;
    }

    @GetMapping("")
    public WorldService get(@RequestParam(value = "criteria", defaultValue = "") String criteria)
    {
        return avc.world();
    }

    @GetMapping("/agent")
    public List<Agent> find(@RequestParam(value = "criteria", defaultValue = "") String criteria)
    {
        return avc.agents().find();
    }

    @GetMapping("/agent/{id}")
    public Agent get(@PathVariable(value = "id") Integer id)
    {
        return avc.agents().get(id);
    }

    @GetMapping("/agent/{id}/sensor")
    public short[][] sensor(@PathVariable(value = "id") Integer id)
    {
        return avc.submap(id).cell();
    }

    @GetMapping("/agent/{id}/actuator")
    public String getActuator(@RequestParam(value = "cmd", defaultValue = "")  String cmd, @PathVariable Integer id)
    {
        if(cmd.isEmpty()){
            return avc.agents().get(id).toString();
        }else if(cmd.equals("forward")){
            avc.agents().get(id).Forward();
            return String.format("forward");
        }
        else if(cmd.equals("turnleft")){
            avc.agents().get(id).turnLeft();
            return String.format("turnleft");
        }
        else if(cmd.equals("turnright")){
            avc.agents().get(id).turnRight();
            return String.format("turnright");
        }else {
            return avc.agents().get(id).toString();
        }
    }

    @GetMapping("/map")
    public short[][] getMap(@RequestParam(value = "criteria", defaultValue = "") String criteria)
    {
        return MapService.getMap();
    }

    @GetMapping("/sim")
    public String getStatus(@RequestParam(value = "cmd", defaultValue = "") String cmd)
    {
        return avc.getStatus(cmd);
    }


}
