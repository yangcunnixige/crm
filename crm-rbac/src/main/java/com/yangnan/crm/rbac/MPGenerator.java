package com.yangnan.crm.rbac;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.IFill;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;


public class MPGenerator {
    //需要配置
    private static final String dbTables = "login_log";             //需要生成的表名           //需要生成的表名
    private static final Boolean enableSwagger = false;                 //是否开启Swagger

    public static void main(String[] args) {
        // 1.数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder("jdbc:mysql://localhost:3306/crm","root","123456789").build();

        // 2.全局配置
        GlobalConfig.Builder globalConfigBuilder = new GlobalConfig.Builder();
        // 代码生成目录
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);// D:\MyIdeaProject\mybatis-plus
        globalConfigBuilder.outputDir(projectPath + "/crm-rbac" + "/src/main/java");
        // 作者
        globalConfigBuilder.author("yangnan");
        // 结束时是否打开文件夹
        globalConfigBuilder.openDir(false);
        // 实体属性Swagger2注解
        if (enableSwagger){
            globalConfigBuilder.enableSwagger();
        }
        globalConfigBuilder.dateType(DateType.ONLY_DATE);//设置时间类型Date  LocalDateTime

        // 3.包配置
        PackageConfig.Builder packageConfigBuilder = new PackageConfig.Builder();
        packageConfigBuilder.parent("com.yangnan.crm.rbac");           //项目包名

        //都有默认值   配置实体、mapper、service、controller的包名
        packageConfigBuilder.entity("pojo");

        // 4.策略配置
        StrategyConfig.Builder strategyConfigBuilder = new StrategyConfig.Builder();
        // 设置需要映射的表名  用逗号分割
        strategyConfigBuilder.addInclude(dbTables.split(","));
        strategyConfigBuilder.entityBuilder().idType(IdType.NONE);
        //主键使用雪花算法
        strategyConfigBuilder.entityBuilder().idType(IdType.ASSIGN_ID);
        // 下划线转驼峰
        strategyConfigBuilder.entityBuilder().naming(NamingStrategy.underline_to_camel);
        strategyConfigBuilder.entityBuilder().columnNaming(NamingStrategy.underline_to_camel);
        // entity的Lombok
        strategyConfigBuilder.entityBuilder().enableLombok();
        // 逻辑删除
        strategyConfigBuilder.entityBuilder().logicDeleteColumnName("deleted");
        strategyConfigBuilder.entityBuilder().logicDeletePropertyName("deleted");

        // 自动填充
        // 创建时间
        IFill gmtCreate = new Column("gmt_create", FieldFill.INSERT);
        // 更新时间
        IFill gmtModified = new Column("gmt_modified", FieldFill.INSERT_UPDATE);
        strategyConfigBuilder.entityBuilder().addTableFills(gmtCreate, gmtModified);
        // 乐观锁
        //strategyConfigBuilder.entityBuilder().versionColumnName("version");
        //strategyConfigBuilder.entityBuilder().versionPropertyName("version");
        // 使用RestController3
        strategyConfigBuilder.controllerBuilder().enableRestStyle();
        // 将请求地址转换为驼峰命名，如 http://localhost:8080/hello_id_2
        strategyConfigBuilder.controllerBuilder().enableHyphenStyle();

        // 创建代码生成器对象，加载配置   对应1.2.3.4步
        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfig);
        autoGenerator.global(globalConfigBuilder.build());
        autoGenerator.packageInfo(packageConfigBuilder.build());
        autoGenerator.strategy(strategyConfigBuilder.build());

        // 执行
        autoGenerator.execute();
    }
}
