# 安卓飞机大战

HITSZ 2022春面向对象课程项目，Collaborator: yym

### 主要功能

本项目主要分为用户验证，游戏，道具商城三大部分。

![function](https://github.com/Mimasss2/Aircraft-War-Game-for-Android/tree/main/img/function.png)

### 流程图

![pipeline](https://github.com/Mimasss2/Aircraft-War-Game-for-Android/tree/main/img/pipeline.png)

用户登录后，执行的操作顺序如上图所示。

### 系统设计方案

#### 网络通信方案

在联机模式下，两对战用户的信息（得分）需要互通。我们采用socket实现两个玩家之间的信息传输。

![socket](https://github.com/Mimasss2/Aircraft-War-Game-for-Android/tree/main/img/socket.png)

#### 数据存储方案

- MySQL：存储用户信息，道具信息，联网模式游戏记录信息
- SharedPreferences：存储单机模式游戏记录信息，用户默认用户名，密码等设置

**用户信息：**

Mysql数据库**user**表，存储用户id（主键），用户名，用户密码，用户拥有的积分。

**游戏记录：**

（联网模式）Mysql数据库**game_record**表，存储记录id(主键),用户id，游戏分数，用户名，创建时间。

（本地模式）SharedPreferences本地存储，同种模式下的所有单机记录（用户id，用户名，游戏分数，创建时间）的List转化为json字符串后存储到本地设备的文件中。

**道具：**

Mysql数据库**props**表，存储道具id（主键），道具名，道具描述，道具图对应的key

Mysql数据库**prop_instance**表存储所有用户所持的道具实例，每条记录包含用户id，道具id，道具记录id（主键）

### 界面展示

游戏界面：

![login](https://github.com/Mimasss2/Aircraft-War-Game-for-Android/tree/main/img/login.png)

登录界面：

![game](https://github.com/Mimasss2/Aircraft-War-Game-for-Android/tree/main/img/game.png)