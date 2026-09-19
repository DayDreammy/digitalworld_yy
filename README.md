# 模拟数字交通世界（Java / Spring Boot）

同济大学计算机类课程项目（2022 秋）。在课程提供的 Spring Boot 脚手架
`avc-digitalworld-main`（包名 `edu.tongji.cc.digitalworld`）上，把一个「数字交通世界」跑起来：
世界里地图、障碍物、多个智能体（车）各自用 A* 规划路径并行驶到目标点；外部 App 可以通过
UDP 与 REST API 读取世界状态、下发指令。

## 我实现的部分

新增的文件：

| 文件 | 作用 |
| --- | --- |
| `Astar/AStar.java`、`Astar/Node.java` | A* 路径规划；后续做了直角化与平滑优化 |
| `entity/Obstacle.java` | 障碍物：初始化世界时随机生成 |
| `service/UdpServer.java` | UDP 服务端线程：接收 App 发来的信息 |
| `service/ScheduledTasks.java` | 定时任务：驱动世界推进（智能体移动、状态更新） |

另外按提交记录扩展了框架里的既有类：`MapService`（对外提供地图）、`Agent`（保存当前位置等状态）、
`World` / `RoboTaxi`（新增 sim 车），以及一批 REST API。

`UML/` 是本项目的类图与流程图（drawio：Agent / Api / Astar / Common / Entity / Obstacle / Service / UDP
，以及服务端流程图）。

## 技术栈

- Java + Spring Boot（`pom.xml` 与 `build.gradle` 两套构建都在）
- A* 寻路、UDP 通信、REST API、定时任务
- 运行日志在 `log/`，地图文件 `map.txt` / `map - Copy.txt`

## 怎么跑

- Windows 下双击 `avc-digitalworld-main/run.bat`，或直接运行 `MainApplication`
- 依赖的是 2022 年的 Spring Boot 版本，现在直接跑可能要调整 JDK 版本与依赖

## 仓库说明

- `avc-digitalworld-main/` 里的 Spring Boot 脚手架（`Jenkinsfile`、`pom.xml` / `build.gradle`、
  最初的 `MainApplication`、`GreetingController` 等）在第一次提交时就已经存在，属于**课程提供的框架代码**，
  不是本人所写。
- `log/` 是当年跑出来的运行日志，未清理。

## 待补充

- 课程全名与学期、项目要求
- 当年写的报告 / 设计文档（可补成 `doc/`）
