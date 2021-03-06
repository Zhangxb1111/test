package com.test.configration;

import com.test.influxdb.InfluxDBConnectionFactory;
import com.test.influxdb.converter.PointConverter;
import com.test.influxdb.InfluxDBTemplate;
import com.test.properties.InfluxDBProperties;
import org.influxdb.dto.Point;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({InfluxDBProperties.class})
public class InfluxDBConfiguration {

	// PropertySourcesPlaceholderConfigurer这个bean，
	// 这个bean主要用于解决@value中使用的${…}占位符。
	// 假如你不使用${…}占位符的话，可以不使用这个bean。
	/*@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}*/
	@Bean
	public InfluxDBConnectionFactory influxDBConnectionFactory(final InfluxDBProperties influxDBProperties) {
	    return new InfluxDBConnectionFactory(influxDBProperties);
	}

	@Bean
	public InfluxDBTemplate<Point> influxDBTemplate(final InfluxDBConnectionFactory influxDBConnectionFactory) {
	    /*
	     * You can use your own 'PointCollectionConverter' implementation, e.g. in case
	     * you want to use your own custom measurement object.
	     */
	    return new InfluxDBTemplate<>(influxDBConnectionFactory, new PointConverter());
	}
	  
}
