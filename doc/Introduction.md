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
  3. 每个数据表，设计到状态的，一定要把状态机的迁转设计清楚，建议使用英文枚举，可读性强；
- 日志： 日志使用logback，对日志进行划分，分为error/sql/trace，以及业务层biz日志和access访问日志，日志必须做分割（按照日期分割YYYY-MM-DD）
- 密码生成规则： 密码采用密文存储，密码必须经过加盐处理，密码生成的工具类请参看EncryptUtil，提供了对密钥生成算法的`Algorithm`接口，可根据自身需实现`Algorithm`接口。
- redis方案（可选）： redis的使用需要谨慎。redis主要应该应用在保存用户Session等信息上，redis的信息必须做好持久化，即，即便没有Redis，系统仍可以正常运行，对用户无感知
- 系统参数的动态配置： 本方案暂时不考虑系统级参数的的动态配置，如果考虑，建议结合redis和数据库实现。`SysConfigController`提供了获取系统配置的接口，客户端可使用该接口获取相关信息。
- 登录/鉴权： 权限验证设计在Controller层，以`RequestsPermissions`注解方式实现，鉴权采用`aop`方式。权限的设计可参看`ConstTest`中对权限的设定
- 事物处理： //TODO 待完善

### 工程介绍

- platform-core: 系统核心工程，提供核心功能以及工具类，包括`annotation`、`exception`、`i18n`、`log`等规约
- platform-core-web: 系统Web核心工程，提供web相关的服务和工具，包括`exception`、`spring`、web层的`util`等规约
- platform-base: 系统基类工程，依赖`platform-core-web`，提供业务的`dao`、`domain`、`mapper`等
- platform-web-base: Web基类工程，包括`service`和`facade`，用于提供基服务。如用户权限、登录的核心操作，在扩展工程时，请在自己的工程中进行扩展
- platform-web: Web业务工程，包括`controller`，主要对外提供web接口。依赖`platform-web-base`。 


### 数据库脚本

数据库脚本放置在doc/sql目录下，请自行查阅

- init_sql.sql: 数据库初始化脚本（仅表结构）
- init_local_test.sql: 测试数据的初始化脚本（结构和数据）

### 框架

#### 基本原则

platform-web提供最基本的功能，包括5张表（PRIV、ROLE、USER、ROLE_PRIV、USER_ROLE），以及对这5张表的基本操作（具体字段参看表结构的sql语句）

- PRIV： 权限表，权限暂时分为3类。DIR-目录（菜单的目录结构，可以实现多级目录），MENU-菜单（菜单链接，具体到url），DATA-数据（数据操作权限，以"`系统`:`模块`:`操作`"作为code值，例如"sys:priv:add"，代表系统层权限管理的添加权限）
- ROLE： 角色表。角色固定一个SuperAdmin，作为超级管理员，其余角色可实现自定义
- USER： 用户表，用户基本信息。包括用户的可用状态、锁定状态、启用时间、过期时间、登录失败次数等
- ROLE_PRIV: 角色权限关联表。可支持权限的按需分配。
- USER_ROLE: 用户角色关联表。可支持用户多角色处理。

如下为基本设定
```java
package com.alphabeta.platform.web.common;

public class ConstTest {

    public final static String[][] PRIVS = {
        // 以下为系统权限
        // 权限管理
        {"DIR", "sys:priv:dir:manage", "权限管理", "权限管理", "/priv", "/priv"},
        {"MENU", "sys:priv:url:list", "权限列表", "权限列表", "/priv/list", "/priv/list", "sys:priv:dir:manage"},
        {"MENU", "sys:priv:url:add", "新建权限", "新建权限", "/priv/add", "/priv/add", "sys:priv:dir:manage"},
        {"MENU", "sys:priv:url:edit", "编辑权限", "编辑权限", "/priv/edit", "/priv/edit", "sys:priv:dir:manage"},
        {"MENU", "sys:priv:url:view", "查看权限", "查看权限", "/priv/view", "/priv/view", "sys:priv:dir:manage"},
        {"DATA", "sys:priv:add", "新建权限", "新建权限", "", "", "sys:priv:dir:manage"},
        {"DATA", "sys:priv:delete", "删除权限", "删除权限", "", "", "sys:priv:dir:manage"},
        {"DATA", "sys:priv:edit", "编辑权限", "编辑权限", "", "", "sys:priv:dir:manage"},
        {"DATA", "sys:priv:view", "查看权限", "查看权限", "", "", "sys:priv:dir:manage"},
        // 角色管理
        {"DIR", "sys:role:dir:manage", "角色管理", "角色管理", "/role", "/role"},
        {"MENU", "sys:role:url:list", "角色列表", "角色列表", "/role/list", "/role/list", "sys:role:dir:manage"},
        {"MENU", "sys:role:url:add", "新建角色", "新建角色", "/role/add", "/role/add", "sys:role:dir:manage"},
        {"MENU", "sys:role:url:edit", "编辑角色", "编辑角色", "/role/edit", "/role/edit", "sys:role:dir:manage"},
        {"MENU", "sys:role:url:permission", "分配权限", "分配权限", "/role/permission", "/role/permission", "sys:role:dir:manage"},
        {"MENU", "sys:role:url:view", "查看角色", "查看角色", "/role/view", "/role/view", "sys:role:dir:manage"},
        {"DATA", "sys:role:add", "新建角色", "新建角色", "", "", "sys:role:dir:manage"},
        {"DATA", "sys:role:delete", "删除角色", "删除角色", "", "", "sys:role:dir:manage"},
        {"DATA", "sys:role:edit", "编辑角色", "编辑角色", "", "", "sys:role:dir:manage"},
        {"DATA", "sys:role:view", "查看角色", "查看角色", "", "", "sys:role:dir:manage"},
        {"DATA", "sys:role:allocate", "分配权限", "分配权限", "", "", "sys:role:dir:manage"},
        // 管理员管理
        {"DIR", "sys:user:dir:manage", "管理员管理", "管理员管理", "/user", "/user"},
        {"MENU", "sys:user:url:list", "管理员列表", "管理员列表", "/user/list", "/user/list", "sys:user:dir:manage"},
        {"MENU", "sys:user:url:add", "新建管理员", "新建管理员", "/user/add", "/user/add", "sys:user:dir:manage"},
        {"MENU", "sys:user:url:edit", "编辑管理员", "编辑管理员", "/user/edit", "/user/edit", "sys:user:dir:manage"},
        {"MENU", "sys:user:url:permission", "分配角色", "分配角色", "/user/permission", "/user/permission", "sys:user:dir:manage"},
        {"MENU", "sys:user:url:view", "查看管理员", "查看管理员", "/user/view", "/user/view", "sys:user:dir:manage"},
        {"DATA", "sys:user:add", "新建管理员", "新建管理员", "", "", "sys:user:dir:manage"},
        {"DATA", "sys:user:delete", "删除管理员", "删除管理员", "", "", "sys:user:dir:manage"},
        {"DATA", "sys:user:edit", "编辑管理员", "编辑管理员", "", "", "sys:user:dir:manage"},
        {"DATA", "sys:user:view", "查看管理员", "查看管理员", "", "", "sys:user:dir:manage"},
        {"DATA", "sys:user:allocate", "分配角色", "分配角色", "", "", "sys:user:dir:manage"},
        {"DATA", "sys:user:lock", "禁用管理员", "禁用管理员", "", "", "sys:user:dir:manage"},
        {"DATA", "sys:user:unlock", "启用管理员", "启用管理员", "", "", "sys:user:dir:manage"},
        {"DATA", "sys:user:resetPwd", "重置密码", "重置密码", "", "", "sys:user:dir:manage"},
        // 个人信息
        {"MENU", "sys:profile:manage", "个人信息", "个人信息", "/profile/view", "/profile/view"},
        {"DATA", "sys:profile:changePwd", "修改密码", "修改密码", "", "", "sys:profile:manage"},

    };

    public final static String[][] ROLES = {
        {"SuperAdmin", "超级管理员", "超级管理员"},
        {"Admin", "管理员", "管理员"},
        {"User", "普通用户", "普通用户"},
    };

    public final static String[][] USERS = {
        {"13819493700", "123456"},
        {"13819493701", "123456"},
        {"13819493702", "123456"},
    };

}
```

#### 基本功能

- 权限管理： 权限表的crud操作；获取角色所属的权限；获取用户所属的权限（如果是多角色，采用并集的方式）
- 角色管理： 角色表的crud操作；针对某个角色，分配其所属的权限；
- 用户管理： 用户表的crud操作；针对某个用户，分配其所属的角色；

### 使用

- 原则上，业务系统只需要引入`platform-core`和`platform-core-web`的依赖，在此基础上，实现业务系统的业务功能。
- `platform-base`、`platform-web-base`、`platform-web`是实现权限业务的工程。可作为实现其他业务的模板参考。
- 如果自定义的业务系统，对用户角色权限的要求不高，可集成`platform-web`工程作为自身业务的一部分。

### 数据过滤

Controller层采用@FastJsonView方式对Json数据进行动态过滤，例如
> ```java
> @FastJsonView(
>     include = {@FastJsonFilter(clazz = XmManager.class, props = {"phone", "name"})},
>     exclude = {@FastJsonFilter(clazz = XmPriv.class, props = {"createtime", "updatetime"})})
> ```
