package de.sentryc.service;

import de.sentryc.SentrycApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@SpringBootTest
@AutoConfigureTestDatabase
@EnableAutoConfiguration
        (exclude = {com.graphql.spring.boot.test.GraphQLTestAutoConfiguration.class,
                graphql.kickstart.spring.web.boot.GraphQLWebsocketAutoConfiguration.class})
@ActiveProfiles("test")
@ContextConfiguration(classes = SentrycApplication.class)
public @interface NonTransactionalIntegrationTest {

}