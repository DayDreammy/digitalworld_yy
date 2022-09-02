package edu.tongji.cc.digitalworld.entity;

import edu.tongji.cc.digitalworld.common.Location;
import edu.tongji.cc.digitalworld.service.MapService;
import edu.tongji.cc.digitalworld.service.WorldService;

import java.util.Random;

/**
 * The base class for all kinds of Agent.
 * <p>
 * TODO: In the future, we'll introduce interface IiAgent.
 *
 * @author ZhangWei(Dept. of Control, TongJi University)
 * - First version.
 */
public class Agent {

    private int _id;
    private Location _location = new Location();
    private Location _destination = new Location();// taxi's target

    public Agent() {

    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public void setLocation() {
        Random rnd = new Random();
        Location loc = new Location();
        while (true) {
            loc = new Location(rnd.nextInt(MapService.MAP_WIDTH), rnd.nextInt(MapService.MAP_HEIGHT));
            if (MapService.judgeCellEmpty(loc.getX(), loc.getY())) //cell位置要为空，才能落座
                break;
        }
        this._location.assignfrom(loc);//给agent赋位置
        MapService.setCellValue(loc.getX(),loc.getY(), (short) 2);//起点
    }

    public void setDestination() {
        Random rnd = new Random();
        Location dst = new Location();
        while (true) {
            dst = new Location(rnd.nextInt(MapService.MAP_WIDTH), rnd.nextInt(MapService.MAP_HEIGHT));
            if (MapService.judgeCellEmpty(dst.getX(), dst.getY())) //cell位置要为空，才能落座
                break;
        }
        this._destination.assignfrom(dst);
        MapService.setCellValue(dst.getX(),dst.getY(), (short) 3);//终点
    }

    public Location getLocation() {
        return this._location;
    }

    @Override
    public String toString() {
        return String.format(
                "Agent [id=%d, location=(%d , %d) ， destination=(%d , %d)]",
                getId(), getX(), getY(),getX_dst(),getY_dst());
    }

    public int getX() {
        return _location.getX();
    }

    public int getY() {
        return _location.getY();
    }

    public int getX_dst() {
        return _destination.getX();
    }

    public int getY_dst() {
        return _destination.getY();
    }
}
