package com.live;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.live.user.mapper")
public class NewBirdApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewBirdApplication.class, args);
    }
//  尝试更改mybatis扫描pojo包的方式，适配正则匹配扫描
//    @Bean
//    @ConditionalOnMissingBean
//    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
//        factory.setDataSource(dataSource);
//        factory.setVfs(SpringBootVFS.class);
//        return factory;
//    }
}
