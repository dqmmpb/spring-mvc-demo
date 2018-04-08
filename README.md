# 工程名：platform

## platform server端主工程

### 规范介绍

能用注解注入的尽量用注解，少写点xml，约定胜于配置；

@Component 用于Controller层实现类注入，@Service 用于service层实现类注入，@Repository 用于持久化层实现类注入；

每个数据表，设计到状态的，一定要把状态机的迁转设计清楚，尽量使用英文枚举，可读性强；

### 目录结构介绍

- controller层： 仅仅用来处理url相关内容，处理param参数，以及路由控制
- service层： 基础服务层，存放单个领域模型的业务逻辑的代码，用来存放简单的业务逻辑
- facade层： 门面facade，用来存放复杂的业务逻辑，对外提供的Service接口通过facade统一封装，放在facacde包下；
- dao层： 使用通用Mapper自动生成，需要根据本地配置修改`generatorConfig.xml`文件，在需要生成model的工程中会存在该配置文件；
- 异常/Trace Log: 由`AOP`处理，使用`logback`记录；

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

后续的更改，请按照规范`1.0.1`新建子文件夹


注：
- mybatis generator在生成的*Mapper.xml会采用merge的方式，因此多次执行mybatis generator会在xml中存在重复代码，需要手动去除。
- 一种比较好的策略是在使用通用Mapper的时候不使用自动生成的xml。
- 通用Mapper没有提供继承方式，因此所有的Mapper都要继承自`tk.mybatis.mapper.common.Mapper`。
- 当接口有1个参数时，Mapper会根据参数名映射到xml中，但当有多个参数时，需要使用@Param方式绑定参数，便于在xml中获取。


### 国际化方案

国际化方案参看doc/i18n.md中的介绍。
