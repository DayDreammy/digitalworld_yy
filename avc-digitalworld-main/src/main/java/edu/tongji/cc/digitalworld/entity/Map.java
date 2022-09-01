package edu.tongji.cc.digitalworld.entity;

import java.util.List;

/**
 * Geographical Map, which is a 2D/3D space containing some MapObjects/Agents.
 *
 * @author ZhangWei(Dept. of Control, TongJi University)
 * - First version.
 */
public class Map {
    private short[][] cell;

    public Map(int width, int height)
    {
        cell = new short[width][height];
    }

    public short[][] cell()
    {
        return cell;
    }

}
