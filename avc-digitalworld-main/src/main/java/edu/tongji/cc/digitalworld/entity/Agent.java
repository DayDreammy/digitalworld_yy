package edu.tongji.cc.digitalworld.entity;

import edu.tongji.cc.digitalworld.Astar.AStar;
import edu.tongji.cc.digitalworld.Astar.Node;
import edu.tongji.cc.digitalworld.common.Location;
import edu.tongji.cc.digitalworld.service.MapService;
import edu.tongji.cc.digitalworld.service.WorldService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static edu.tongji.cc.digitalworld.service.MapService.*;

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
    private static Location _location = new Location();
    private static Location _destination = new Location();// taxi's target

    private static List<Node> _path= new ArrayList<Node>();//taxi's path

    public Agent() {

    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public void setLocation(int type) {
        Random rnd = new Random();
        Location loc = new Location();
        while (true) {
            loc = new Location(rnd.nextInt(MAP_WIDTH), rnd.nextInt(MAP_HEIGHT));
            if (MapService.judgeCellEmpty(loc.getX(), loc.getY())) //cell位置要为空，才能落座
                break;
        }
        if( type ==2){
            this._location.assignfrom(loc);//给agent赋位置
            setCellValue(loc.getX(),loc.getY(), (short) 2);//起点
        }
        else if(type == 3){
            this._destination.assignfrom(loc);
            //MapService.setCellValue(loc.getX(),loc.getY(), (short) 3);//终点
        }

    }

    public void setDestination() {
        Random rnd = new Random();
        Location dst = new Location();
        while (true) {
            dst = new Location(rnd.nextInt(MAP_WIDTH), rnd.nextInt(MAP_HEIGHT));
            if (MapService.judgeCellEmpty(dst.getX(), dst.getY())) //cell位置要为空，才能设为起点/终点
                break;
        }
        this._destination.assignfrom(dst);
        setCellValue(dst.getX(),dst.getY(), (short) 3);//终点
    }

    public static void Astar() {
        Node initialNode = new Node(getX(), getY());
        Node finalNode = new Node(getX_dst(), getY_dst());
        int rows = MAP_HEIGHT;
        int cols = MAP_WIDTH;
        AStar aStar = new AStar(rows, cols, initialNode, finalNode);
//        int[][] blocksArray = new int[][]{{1, 3}, {2, 3}, {3, 3}};
        List<Location> blocksArray = new ArrayList<Location>();
        for(int i=0;i<MAP_HEIGHT;i++){
            for(int j=0;j<MAP_WIDTH;j++){
                //如果cell位置不为空，那么就标记为障碍物
                if (!MapService.judgeCellEmpty(i,j)){
                    blocksArray.add(new Location(i,j));
                }
            }
        }
        aStar.setBlocks(blocksArray);
//        List<Node> path = aStar.findPath();
        _path=aStar.findPath();
        for (Node node : _path) {
            System.out.println(node);
            setCellValue(node.getRow(),node.getCol(),(short)4);
            setCellValue(initialNode.getRow(),initialNode.getCol(),(short)2);
            setCellValue(finalNode.getRow(),finalNode.getCol(),(short)3);
        }

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

    public static int getX() {
        return _location.getX();
    }

    public static int getY() {
        return _location.getY();
    }

    public static int getX_dst() {
        return _destination.getX();
    }

    public static int getY_dst() {
        return _destination.getY();
    }


}
