package edu.tongji.cc.digitalworld.entity;

import edu.tongji.cc.digitalworld.common.Location;

class ObstacleInfo {
    int _length;//长度
    int _width;//宽度
    double _angle;//倾斜角度
}
//障碍物类
public class Obstacle extends Location{

    private ObstacleInfo _Obstacle_info=new ObstacleInfo();

    public Obstacle() {

    }

    public Obstacle(Location center, int len, int wid, double angle) {
        setObstacle(center, len, wid, angle);
    }

    public void setObstacle(Location center, int len, int wid, double angle) {
        setX(center.getX());
        setY(center.getY());
        setZ(center.getZ());
        _Obstacle_info._length = len;
        _Obstacle_info._width = wid;
        _Obstacle_info._angle = angle;
    }

    public int getLength() {
        return _Obstacle_info._length ;
    }

    public int getWidth() {
        return _Obstacle_info._width ;
    }

    public double getAngle() {
        return _Obstacle_info._angle ;
    }


    public ObstacleInfo get_Obstacle_info() {
        return _Obstacle_info;
    }

    @Override
    public String toString() {
        return String.format(
                "Obstacle [center=(%d , %d) , Width= %d ,Length= %d ]",
                getX(), getY(),getWidth(),getLength());
    }



}
