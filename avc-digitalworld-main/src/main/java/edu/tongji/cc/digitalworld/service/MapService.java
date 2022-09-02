package edu.tongji.cc.digitalworld.service;

import edu.tongji.cc.digitalworld.common.Location;
import edu.tongji.cc.digitalworld.entity.Map;
import edu.tongji.cc.digitalworld.entity.Obstacle;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Map management.
 *
 * @author ZhangWei(Dept. of Control, TongJi University)
 * - First version.
 */

public class MapService {
    public static final int MAP_WIDTH = 50;
    public static final int MAP_HEIGHT = 50;

    private static short[][] cell = new short[MAP_WIDTH][MAP_HEIGHT];
    //cell 数值说明
    //0 空
    //1 障碍物
    //2 起点
    //3 终点
    AgentService _agents;
    List<Obstacle> _obslist = new ArrayList<Obstacle>();

    public MapService(AgentService agents) {
        this._agents = agents;
    }

    public Map subMap(Location loc1, Location loc2) {
        int width = Math.abs(loc1.getX() - loc2.getX());
        int height = Math.abs(loc1.getY() - loc2.getY());
        Map submap = new Map(width, height);
        // TODO
        return submap;
    }


    //随机生成障碍物
    public void randomize(int count) {
        Random rnd = new Random();
        for (int i = 0; i < count; i++) {
            Obstacle obs = new Obstacle();
            obs = genObstacle(_obslist, rnd.nextInt(5), rnd.nextInt(5), 0, 0, rnd.nextInt(3));
            _obslist.add(obs);
        }

        for (int i = 0; i < WorldService.getAgentNumber(); i++) {
            System.out.println(_obslist.get(i).toString());
        }

        //栅格障碍物置1
        setObsGride();


    }

    //生成障碍物object
    public Obstacle genObstacle(List<Obstacle> obs_list, int row_id, int col_id, int hei_id, double angle, int obs_type) {
        Obstacle obs = new Obstacle();
        if (obs_type == 0) {
            Location center = genObsCenter(row_id, col_id, hei_id, angle, obs_type);
            obs.setObstacle(center, 9, 9, 0);
        } else if (obs_type == 1) {
            Location center = genObsCenter(row_id, col_id, hei_id, angle, obs_type);
            obs.setObstacle(center, 5, 7, 0);
        } else if (obs_type == 2) {
            Location center = genObsCenter(row_id, col_id, hei_id, angle, obs_type);
            obs.setObstacle(center, 7, 5, 0);
        }
        return obs;
    }

    //生成障碍物中心点坐标
    public Location genObsCenter(int row_id, int col_id, int hei_id, double angle, int obs_type) {
        int x = 0, y = 0;
        if (row_id > 4 || col_id > 4 || row_id < 0 || col_id < 0)
            return new Location(x, y);
        //地图被划分为5行5列，每块大小为10x10个栅格.id范围：0-4
        //确定中心点栅格坐标，方向为横向

        if (obs_type == 0) {//7x7
            x = row_id * 10 + 5;
            y = col_id * 10 + 5;
        } else if (obs_type == 1) {//5x3
            x = row_id * 10 + 5;
            y = col_id * 10 + 5;
        } else if (obs_type == 2) {//3x5
            x = row_id * 10 + 5;
            y = col_id * 10 + 5;
        }
        return new Location(x, y);
    }

    //将障碍物覆盖点置1
    public void setObsGride() {
        int x_start, y_start, x_end, y_end;
        Location loc = new Location();
        for (int i = 0; i < WorldService.getObstacleNumber(); i++) {
            Obstacle obs = _obslist.get(i);
            //计算边界点
            x_start = obs.getX() - obs.getWidth() / 2;
            x_end = obs.getX() + obs.getWidth() / 2;
            y_start = obs.getY() - obs.getLength() / 2;
            y_end = obs.getY() + obs.getLength() / 2;

            //将障碍物覆盖点置1
            for (int j = x_start; j <= x_end; j++) {
                for (int k = y_start; k <= y_end; k++) {
                    cell[j][k] = 1;
                }
            }
        }

    }

    public static boolean judgeCellEmpty(int x, int y) {
        return (cell[x][y] == 0);
    }

    public static void setCellValue(int x, int y, short val) {
        cell[x][y] = val;
    }

    public static void drawMap() {
        // 调试——将栅格打印出来看看效果
        System.out.println(Arrays.deepToString(cell));
        //保存为map.txt
        try {
            File f = new File("map.txt");
            FileOutputStream fop = new FileOutputStream(f);
            // 构建FileOutputStream对象,文件不存在会自动新建

            OutputStreamWriter os = new OutputStreamWriter(fop, "UTF-8");

            for (int x = 0; x < MAP_WIDTH; x++) {
                os.append("\r\n");
                for (int y = 0; y < MAP_HEIGHT; y++) {
                    if (cell[x][y] != 0)
                        os.append((char) (cell[x][y] + 48));
                    else{
                        os.append(' ');
                    }
                }
            }
            os.close();
            fop.close();
        } catch (IOException e) {
            System.out.print("Exception");
        }
    }
}
