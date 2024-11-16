package fun.pingflow.sparrow.generate.test;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.ibatis.annotations.Mapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 代码生成器
 *
 * @author eang-zh at 2024-11-07 21:01
 * @version 1.0.0
 */
@SpringBootTest
public class CodeGeneratorTest {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    /**
     * 代码生成，用 web-start 在单测里写，是因为配置文件数据库信息加密<p>
     * 单测类要自动解密，最方便的就是把密码写在配置文件里<p>
     * 依赖框架解密，就要用 @SpringBootTest
     */
    @Test
    public void codeGenerator() {

        FastAutoGenerator.create(url, username, password)
                // 全局配置
                .globalConfig(builder -> {
                    builder.author("eang-zh") // 设置作者
                            .disableOpenDir() // 禁止自动打开输出目录
                            .commentDate("yyyy-MM-dd HH:mm") // 注释的日期格式
                            .dateType(DateType.TIME_PACK) // 设置时间类型 (默认值 Java LocalDate）
                            .outputDir("./CodeGenerateOutput"); // 指定输出目录
                })
                // 包配置
                .packageConfig(builder -> builder
                        // TODO
                        .parent("fun.pingflow.sparrow") // 父包名
                        // TODO
                        .moduleName("module") // 父包模块名
                        .controller("controller") // Controller 包名
                        .service("service") // Service 包名
                        .serviceImpl("service.impl") // Service Impl 包名
                        .mapper("mapper") // Mapper 包名
                        .xml("mapper") // Mapper XML 包名
                        .entity("entity") // Entity 包名
                )
                // 策略配置(包括模板配置)
                .strategyConfig(builder -> builder
                        // 设置需要生成的表名
                        .addInclude("sparrow_user")
                        // 类名移除表前缀
                        .addTablePrefix("sparrow")

                        // Entity 配置
                        .entityBuilder()
                        // .superClass(BaseEntity.class)
                        .enableLombok()
                        .enableChainModel()
                        .versionColumnName("version") // 乐观锁字段名(数据库字段)
                        .logicDeleteColumnName("deleted_flag")
                        // .addSuperEntityColumns(Arrays.asList("id", "created_at", "create_by", "updated_at", "update_by", "deleted_flag", "version", "extend1", "extend2", "extend3"))
                        .formatFileName("%sEntity")
                        .enableFileOverride()

                        // Controller 配置
                        .controllerBuilder()
                        .enableRestStyle() // 生成 @RestController
                        .formatFileName("%sController")
                        .enableFileOverride()

                        // Service 配置
                        .serviceBuilder()
                        .formatServiceFileName("I%sService")
                        .formatServiceImplFileName("%sServiceImp")
                        .enableFileOverride()

                        // Mapper 配置
                        .mapperBuilder()
                        .mapperAnnotation(Mapper.class)
                        .formatMapperFileName("%sMapper")
                        .formatXmlFileName("%sMapper")
                        .enableFileOverride()
                )

                // 模板引擎配置
                // 使用 Freemarker 引擎模板，默认的是 Velocity 引擎模板
                .templateEngine(new FreemarkerTemplateEngine())

                // 发起执行
                .execute();
    }
}
