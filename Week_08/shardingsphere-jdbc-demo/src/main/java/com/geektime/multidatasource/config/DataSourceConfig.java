package com.geektime.multidatasource.config;

import com.google.common.collect.ImmutableSet;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.keygen.KeyGenerateStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.HintShardingStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.StandardShardingStrategyConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.ds0")
    public DataSource ds1DataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.ds1")
    public DataSource ds2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.props")
    public Properties props(){
        return new Properties();
    }

    @Bean
    @Primary
    public DataSource ShardingSphereDataSource(DataSource ds1DataSource,DataSource ds2DataSource,Properties props) throws SQLException, IOException {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds0",ds1DataSource);
        dataSourceMap.put("ds1",ds2DataSource);

        // 配置分库规则
        ShardingTableRuleConfiguration orderTableRuleConfig = new ShardingTableRuleConfiguration("tb_user", "ds${0..1}.tb_user");

        // 配置分库策略
        orderTableRuleConfig.setDatabaseShardingStrategy(new StandardShardingStrategyConfiguration("card_id","dbShardingAlgorithm"));

//        // 配置分表策略
//        orderTableRuleConfig.setTableShardingStrategy(new StandardShardingStrategyConfiguration("id", "tableShardingAlgorithm"));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTables().add(orderTableRuleConfig);

        // 配置分库算法
        Properties dbShardingAlgorithmrProps = new Properties();
        dbShardingAlgorithmrProps.setProperty("algorithm-expression", "ds${card_id % 2}");
        shardingRuleConfig.getShardingAlgorithms().put("dbShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", dbShardingAlgorithmrProps));
        // 配置分表算法
//        Properties tableShardingAlgorithmrProps = new Properties();
//        tableShardingAlgorithmrProps.setProperty("algorithm-expression", "tb_user${id % 2}");
//        shardingRuleConfig.getShardingAlgorithms().put("tableShardingAlgorithm", new ShardingSphereAlgorithmConfiguration("INLINE", tableShardingAlgorithmrProps));

        DataSource dataSource;
        dataSource = ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, ImmutableSet.of(shardingRuleConfig), props);
        return dataSource;
    }
}
