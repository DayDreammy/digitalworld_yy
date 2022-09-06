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
    private int _direction;//车的朝向
    private int _velocity = 1;//车的速度
    private int _acceleration = 0;//车的加速度
    //    - 0  北 （朝上）
//    - 1  南 （朝下）
//    - 2  西 （朝左）
//    - 3  东 （朝右）
    private Location _location = new Location();
    private Location _destination = new Location();// taxi's target

    private List<Node> _path = new ArrayList<Node>();//taxi's path

    public Agent() {

    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public int getDirection() {
        return _direction;
    }

    public void setDirection(int direction) {
        this._direction = direction;
    }

    public int getVelocity() {
        return _velocity;
    }

    public void setVelocity(int velocity) {
        this._velocity = velocity;
    }

    public int getAcceleration() {
        return _acceleration;
    }

    public void setAcceleration(int acceleration) {
        this._acceleration = acceleration;
    }

    public void setLocation(int type) {
        Random rnd = new Random();
        Location loc;
        while (true) {
            loc = new Location(rnd.nextInt(MAP_WIDTH), rnd.nextInt(MAP_HEIGHT));
            if (MapService.judgeCellEmpty(loc.getX(), loc.getY())) //cell位置要为空，才能落座
                break;
        }
        if (type == 2) {
            _location.assignfrom(loc);//给agent赋位置，起点
            setCellValue(loc.getX(), loc.getY(), (short) 2);//起点
        } else if (type == 3) {
            _destination.assignfrom(loc);//终点
            //MapService.setCellValue(loc.getX(),loc.getY(), (short) 3);//终点
        }

    }


    public void turnLeft() {
        if (_direction == 0) {       // - 0  北 （朝上）
            setDirection(2);
        } else if (_direction == 1) { // - 1  南 （朝下）
            setDirection(3);
        } else if (_direction == 2) { // - 2  西 （朝左）
            setDirection(1);
        } else if (_direction == 3) { // - 3  东 （朝右）
            setDirection(0);
        }
    }

    public void turnRight() {
        if (_direction == 0) {       // - 0  北 （朝上）
            setDirection(3);
        } else if (_direction == 1) { // - 1  南 （朝下）
            setDirection(2);
        } else if (_direction == 2) { // - 2  西 （朝左）
            setDirection(0);
        } else if (_direction == 3) { // - 3  东 （朝右）
            setDirection(1);
        }
    }

    //向前演化一步 观察+动作
    public void invovle() {
        //观察，下一步路怎么走
        int next_direction=getNextDirection();
        //行动，一脚油门+方向盘
        goNextNode(next_direction);

    }

    public int getNextDirection(){
        //删去path的第一个节点
        if (_path.size() > 1) {//还没到终点
            _path.remove(0);
        } else {//已经到终点
            System.out.println("taxi [id : " + getId() + " ] 已到达目的地");
        }


        //判断path和自己位置的关系
        int loc_x = _location.getX();
        int loc_y = _location.getY();

        int next_x = _path.get(0).getRow();
        int next_y = _path.get(0).getCol();

        int next_direction = 0; //下一个点的方向

        //观察下一步该怎么走
        if (next_y == loc_y) {//上下移动
            if (next_x > loc_x) {//next path的位置，在南面
                //    - 0  北 （朝上）
                //    - 1  南 （朝下）
                //    - 2  西 （朝左）
                //    - 3  东 （朝右）
                switch (_direction) {
                    case 0:
                        next_direction = 0;//向后转 前进
                        break;
                    case 1:
                        next_direction = 1; //前进
                        break;
                    case 2:
                        next_direction = 2; // 向左转 前进
                        break;
                    case 3:
                        next_direction = 3; //向右转 前进
                        break;
                }
            } else if (next_x < loc_x) {//next path的位置，在南面
                //    - 0  北 （朝上）
                //    - 1  南 （朝下）
                //    - 2  西 （朝左）
                //    - 3  东 （朝右）
                switch (_direction) {
                    case 0:
                        next_direction = 1; //前进
                        break;
                    case 1:
                        next_direction = 0;//向后转 前进
                        break;
                    case 2:
                        next_direction = 3; //向右转 前进
                        break;
                    case 3:
                        next_direction = 2; // 向左转 前进
                        break;
                }
            }
        } else if (next_x == loc_x) {//左右移动
            //    - 0  北 （朝上）
            //    - 1  南 （朝下）
            //    - 2  西 （朝左）
            //    - 3  东 （朝右）
            if (next_y > loc_y) {
                switch (_direction) {
                    case 0:
                        next_direction = 3; //向右转 前进
                        break;
                    case 1:
                        next_direction = 2; // 向左转 前进

                        break;
                    case 2:
                        next_direction = 0;//向后转 前进
                        break;
                    case 3:
                        next_direction = 1; //前进
                        break;
                }
            } else if (next_y < loc_y) {
                switch (_direction) {
                    case 0:
                        next_direction = 2; // 向左转 前进
                        break;
                    case 1:
                        next_direction = 3; //向右转 前进
                        break;
                    case 2:
                        next_direction = 1; //前进
                        break;
                    case 3:
                        next_direction = 0;//向后转 前进
                        break;
                }
            }
        }
        return next_direction;
    }
    public void goNextNode(int next_direction){
        //根据观察结果做出动作
        switch (next_direction) {
            case 0:
                turnLeft();
                turnLeft();
                Forward();
                break;
            case 1:
                Forward();
                break;
            case 2:
                turnLeft();
                Forward();
                break;
            case 3:
                turnRight();
                Forward();
                break;
        }
    }

    public void Forward() {//地图坐标原点在左上角
        // 单位时间位移 = 速度
        // 下一个单位时间位置 = 当前位置 + 单位时间位移
        if (_direction == 0) {       // - 0  北 （朝上）
            _location.setX(getX() - _velocity);
        } else if (_direction == 1) { // - 1  南 （朝下）
            _location.setX(getX() + _velocity);
        } else if (_direction == 2) { // - 2  西 （朝左）
            _location.setY(getY() - _velocity);
        } else if (_direction == 3) { // - 3  东 （朝右）
            _location.setY(getY() + _velocity);
        }
    }

    public void Astar() {
        Node initialNode = new Node(getX(), getY());
        Node finalNode = new Node(getX_dst(), getY_dst());
        int rows = MAP_HEIGHT;
        int cols = MAP_WIDTH;
        AStar aStar = new AStar(rows, cols, initialNode, finalNode,0,2);
//        int[][] blocksArray = new int[][]{{1, 3}, {2, 3}, {3, 3}};
        List<Location> blocksArray = new ArrayList<Location>();
        for (int i = 0; i < MAP_HEIGHT; i++) {
            for (int j = 0; j < MAP_WIDTH; j++) {
                //如果cell位置不为空，那么就标记为障碍物
                if (!MapService.judgeCellEmpty(i, j)) {
                    blocksArray.add(new Location(i, j));
                }
            }
        }
        aStar.setBlocks(blocksArray);
//        List<Node> path = aStar.findPath();
        _path = aStar.findPath();

        for (Node node : _path) {
            System.out.println(node);
            setCellValue(node.getRow(), node.getCol(), (short) 4);
            setCellValue(initialNode.getRow(), initialNode.getCol(), (short) 2);
            setCellValue(finalNode.getRow(), finalNode.getCol(), (short) 3);
        }

    }



    public Location getLocation() {
        return this._location;
    }

    @Override
    public String toString() {
        return String.format(
                "Agent [id=%d, location=(%d , %d) ， destination=(%d , %d)]",
                getId(), getX(), getY(), getX_dst(), getY_dst());
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
