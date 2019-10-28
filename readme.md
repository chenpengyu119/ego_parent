视图技术使用jsp
springboot 2.1.8
spring-boot-starter-data-solr : 1.4.3.RELEASE

项目部署
     修改数据库地址为mycat的（端口8066），然后修改mycat的schema.xml中查询条数限制，因为测试数据4000，
  导入solr时需要查询，所以修改为4000。
     先清空solr（先去web项目删除全部，然后执行初始化），
  然后清空redis(flushall)，因为mycat中的数据是新导入的，没有测试数据。
  1.serviceImpl
     新建一个虚拟机，命名为provider。
     provider的配置文件中必须配置provider的host为provider虚拟机的ip(开发时不能写这个)
     先clean一下parent项目。
       因为是springBoot项目，所以需要使用springBoot打包插件，否则打包会不包含依赖。而且该打包插件只能用于包括main方法的项目，所以不能在
     parent中直接引入，需要单独挨个放到包含main函数的项目中。
     修改web项目的packaging为war，因为这些项目包含webapp文件夹，jar无法包含该文件夹
     导入之后，去parent中install进行打包。打包结束，取serviceimpl中的jar。
     provider只需要jdk环境。
     运行provider之前先启动zookeeper主机
     
  
  2. portal
     考虑该项目有较大并发，需要搭建集群，并且使用Nginx的反向代理功能实现负载均衡。
     修改packaging，
     编写打印日志的语句
     确认有打包插件，
     clean portal
     install
     该项目启动需要zookeeper和redis，mysql1，mysql2，mycat先启动