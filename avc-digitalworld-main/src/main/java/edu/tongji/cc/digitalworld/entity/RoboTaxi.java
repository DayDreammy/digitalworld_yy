package edu.tongji.cc.digitalworld.entity;

import edu.tongji.cc.digitalworld.common.Destination;
import edu.tongji.cc.digitalworld.common.Location;

/**
 * RoboTaxi is a special kind of autonomous agent。
 * Replace the Vehicle class in the former UML design
 *
 * @author ZhangWei(Dept. of Control, TongJi University)
 * - First version.
 */
public class RoboTaxi extends Agent{

    private Destination _destination = new Destination();
    public RoboTaxi()
    {

    }
}
