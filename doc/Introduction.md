# Introduction

## 技术方案/规约

- spring MVC： Ioc/Aop
- mybatis: mybatis-generator（Do生成方案）, mybatis-pagehelper（分页方案）, 通用Mapper（Dao层查询方案）
- junit：junit（单元测试），platform（数据库单元测试，独立测试环境）
- i18n: ResourceBundle（国际化方案）
- 类型或常量： 优先考虑使用Enum类型，其次考虑静态变量Const方式
- 目录/包结构：
  1. controller层，仅仅用来处理url相关内容，以及路由控制
  2. service层是基础服务层，存放单个领域模型的业务逻辑的代码，用来存放简单的业务逻辑
  3. facade层是门面facade，用来存放复杂的业务逻辑，对外提供的Service接口通过facade统一封装，放在facacde包下；
  4. dao是使用通用Mapper自动生成的，需要根据本地配置修改 generatorConfig.xml 这个文件，并且不要提交这个文件；
- 规范：
  1. 优先考虑注解，能用注解注入的尽量用注解，少写点xml，约定胜于配置；
  2. @Controller 用于Controller层实现类注入；@Service 用于service层实现类注入；@Repository 用于持久化层实现类注入；
  3. 每个数据表，设计到状态的，一定要把状态机的迁转设计清楚，并且不用数字枚举，要用英文枚举，包括数据库存储也用英文，可读性强；
- 日志： 日志使用logback，对日志进行划分，分为error/sql/trace，以及业务层biz日志和access访问日志，日志必须做分割（按照日期分割YYYY-MM-DD）
- 密码生成规则： 密码采用密文存储，密码必须经过加盐处理，密码生成的工具类请参看EncryptPwdUtil。
- redis方案： redis的使用需要谨慎。redis主要应该应用在保存用户Session等信息上，redis的信息必须做好持久化，即，即便没有Redis，系统仍可以正常运行，对用户无感知
- 系统参数的动态配置： 本方案暂时不考虑系统级参数的的动态配置，如果考虑，建议结合redis和数据库实现。
- 登录/鉴权： 权限验证设计在Controller层，以RequestsPermissions注解方式实现， 鉴权采用aop方式
- 事物处理： //TODO 待完善

### 工程介绍

- platform-base: 基类工程，包括用户权限、登录的核心操作，在扩展工程时，请在自己的工程中进行扩展，不要修改platform-base的代码
- platform-base: 业务核心工程，提供业务相关的服务，具体项目的业务层请参看本工程
- 

### 数据库脚本

数据库脚本放置在doc/sql目录下，请自行查阅

- init_sql.sql: 数据库初始化脚本（仅表结构）
- init_local_test.sql: 测试数据的初始化脚本（结构和数据）

### 框架

#### 基本原则

xiaoma-platform提供最基本的功能，包括5张表（PRIV、ROLE、USER、ROLE_PRIV、USER_ROLE），以及对这5张表的基本操作（具体字段参看表结构的sql语句）

- PRIV： 权限表，权限暂时分为3类。DIR-目录（菜单的目录结构，可以实现多级目录），MENU-菜单（菜单链接，具体到url），DATA-数据（数据操作权限，以"`系统`:`模块`:`操作`"作为code值，例如"sys:priv:add"，代表系统层权限管理的添加权限）
- ROLE： 角色表。角色固定一个SuperAdmin，作为超级管理员，其余角色可实现自定义
- USER： 用户表，用户基本信息。包括用户的可用状态、锁定状态、启用时间、过期时间、登录失败次数等
- ROLE_PRIV: 角色权限关联表。可支持权限的按需分配。
- USER_ROLE: 用户角色关联表。可支持用户多角色处理。

#### 基本功能

- 权限管理： 权限表的crud操作；获取角色所属的权限；获取用户所属的权限（如果是多角色，采用并集的方式）
- 角色管理： 角色表的crud操作；针对某个角色，分配其所属的权限；
- 用户管理： 用户表的crud操作；针对某个用户，分配其所属的角色；

### 使用

业务系统需要引入platform-base和platform-web工程依赖，在此基础上，实现业务系统的业务功能。platform只提供最核心的实现（可以作为业务系统的模板使用）

### 数据过滤

Controller层采用@FastJsonView方式对Json数据进行动态过滤，例如
> ```java
> @FastJsonView(
>     include = {@FastJsonFilter(clazz = XmManager.class, props = {"phone", "name"})},
>     exclude = {@FastJsonFilter(clazz = XmPriv.class, props = {"createtime", "updatetime"})})
> ```
