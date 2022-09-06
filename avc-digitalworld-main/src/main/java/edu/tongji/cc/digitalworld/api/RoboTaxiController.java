package edu.tongji.cc.digitalworld.api;

import edu.tongji.cc.digitalworld.entity.Agent;
import edu.tongji.cc.digitalworld.service.AppService;
import edu.tongji.cc.digitalworld.service.MapService;
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
@RequestMapping(value="/api/world")
public class RoboTaxiController {

    @Resource(name="avc")
    final AppService avc;

    public RoboTaxiController(AppService avc)
    {
        this.avc = avc;
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

    @GetMapping("/map")
    public short[][] getMap(@RequestParam(value = "criteria", defaultValue = "") String criteria)
    {
        return MapService.getMap();
    }
}
