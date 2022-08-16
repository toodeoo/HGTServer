package com.virgil.hgtserver;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.virgil.hgtserver.mappers")
public class HgtServerApplication {

    public static void main( String[] args ) {
        SpringApplication.run(HgtServerApplication.class ,args);
    }

/*    @Value("${http.port:80}")
    Integer httpPort;

    @Value("${server.port:443}")
    Integer httpsPort;

    @Bean
    @ConditionalOnProperty(name = "condition.http2https", havingValue = "true", matchIfMissing = false)
    public TomcatServletWebServerFactory servletContainer(){
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory(){
            @Override
            protected void postProcessContext( Context context ){
                SecurityConstraint constraint = new SecurityConstraint();
                constraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }

    @Bean
    @ConditionalOnProperty(name = "condition.http2https", havingValue = "true", matchIfMissing = false)
    public Connector httpConnector(){
        Connector connector = new Connector ( "org.apache.coyote.http11.Http11NioProtocol" );
        connector.setScheme ( "http" );
        connector.setPort(httpPort);
        connector.setSecure(false);
        connector.setRedirectPort(httpsPort);
        return connector;
    }*/

}
