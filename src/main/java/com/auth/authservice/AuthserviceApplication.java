package com.auth.authservice;

//import com.auth.authservice.connection.DataStaxAstraProperties;
//import com.auth.authservice.connection.DataStaxAstraProperties;
//import com.auth.authservice.connection.DataStaxAstraProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.cassandra.autoconfigure.CassandraAutoConfiguration;
import org.springframework.boot.cassandra.autoconfigure.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import java.nio.file.Path;

@SpringBootApplication
//@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class AuthserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthserviceApplication.class, args);
	}

//	@Bean
//	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
//
//		Path bundle = astraProperties
//				.getSecureConnectBundle()
//				.toPath();
//
//		return builder -> builder.withCloudSecureConnectBundle(bundle);
//	}
}
