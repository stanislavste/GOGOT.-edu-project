package com.example.gogot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        String commit = "commit1";
        //
        System.out.println("asd");
    }
}

public class ApplicationStartListener implements ServletContextListener {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationStartListener.class);
    private ITokenClient iTokenClient;
    private static final ClassLoader OTT_CLASS_LOADER = OttClientConfiguration.class.getClassLoader();
    private static final String OTT_SERVICE_CLIENT = "ott-service-client";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        this.iTokenClient = TokenClientSpi.getInstance();
        LOG.info("OTT client started.");
        try {
            PlatformCommonsFactory platformCommonsFactory = PlatformCommonsFactoryBinder.getInstance(() -> OTT_CLASS_LOADER);
            platformCommonsFactory.createPlatformPropertyLoader(OTT_SERVICE_CLIENT, OTT_CLASS_LOADER);
        } catch (Exception e) {
            LOG.error("Error create loader", e);
            throw new OttClientException(OttCode.OTT_CLIENT_CFG_ERROR, "Error while checking the possibility of creating a configuration.", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        if (iTokenClient != null) {
            iTokenClient.close();
            iTokenClient = null;
            LOG.info("OTT client destroyed.");
        }
        LOG.info("OTT client stopped.");
    }
}