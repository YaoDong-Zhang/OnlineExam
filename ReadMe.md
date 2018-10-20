#项目简介
>##项目概述
* 该项目主要是针对高中在线考试而开发，是一个在校项目。项目是作者第一次使用 SSM框架开发。<br/>
* 前台学生登录考试,后台分为管理员（类似年级主任那种）和普通教师
>###模块划分
* #####后台界面
  1. **基本信息**
     * 年级管理<br/>
     * 科目管理<br/>
  2. **班级管理**
     * 班级管理<br/>
     * 各班级总人数<br/>
  3. **教师管理**
     * 所有教师<br/>
  4. **学生管理**
     * 所有学生<br/>
     * 学生考试信息<br/>
  5. **试卷管理**
      * 所有试卷<br/>
  6. **试题管理**
      * 所有试题<br/>
      * 导入试题<br/>
  7. **考试安排管理**
      * 待考信息<br/>
  8. **以往考试信息**
        * 所有记录<br/>
>###设计思想
* #####MVC设计思想
    1. 表现层: jsp + css + jquery + ajax
    2. 控制层: springmvc
    3. 业务层: service组件
    4. 持久层: Dao组件
>###技术架构
* #####开发环境: windowdsXP + Tomcat + mysql
* #####采用技术: java + jQuery + aiax + springmvc + IOC + AOP + mybatis
   * java：开发核心技术
   * jQuery：简化前段JavaScript($对象和API)
   * aiax：局部处理页面,提升用户体验度
   * spring(IOC/AOP)：管理相关组件
       * IOC: 负责管理Controller/Service/Dao,维护他们之间的关系
       * AOP：面向切面编程,不修改原有代码,给系统添加新功能
   * mybatis: 对数据库进行操作
>###整体规范
>###数据库常用命令
show databases; //查看有哪些数据库<br/>
create database cloud_note; //创建数据库<br/>
drop database cloud_note; //删除数据库<br/>
use 数据库名; //连接数据库<br/>
show tables; //查看有哪些表<br/>

set names utf8;<br/>
sourcel 路径...;//导入SQL文件<br/>

    

