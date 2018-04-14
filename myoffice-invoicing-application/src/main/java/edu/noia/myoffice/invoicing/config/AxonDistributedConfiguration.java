package edu.noia.myoffice.invoicing.config;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.distributed.*;
import org.axonframework.serialization.Serializer;
import org.axonframework.springcloud.commandhandling.SpringCloudCommandRouter;
import org.axonframework.springcloud.commandhandling.SpringHttpCommandBusConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

@Profile("axon_distributed")
@Configuration
public class AxonDistributedConfiguration {

    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }

    @Bean
    public CommandRouter springCloudCommandRouter(DiscoveryClient discoveryClient, ServiceInstance serviceInstance) {
        return new SpringCloudCommandRouter(
                discoveryClient,
                makeRegistration(serviceInstance),
                new MetaDataRoutingStrategy("routingKey", UnresolvedRoutingKeyPolicy.STATIC_KEY));
    }

    @Bean
    public CommandBusConnector springHttpCommandBusConnector(@Qualifier("localSegment") CommandBus localCommandBus,
                                                             RestOperations restOperations,
                                                             Serializer serializer) {
        return new SpringHttpCommandBusConnector(localCommandBus, restOperations, serializer);
    }

    @Primary
    @Bean
    public DistributedCommandBus springCloudDistributedCommandBus(CommandRouter commandRouter,
                                                                  CommandBusConnector commandBusConnector) {
        return new DistributedCommandBus(commandRouter, commandBusConnector);
    }

    private Registration makeRegistration(ServiceInstance serviceInstance) {
        return new Registration() {
            @Override
            public String getServiceId() {
                return serviceInstance.getServiceId();
            }

            @Override
            public String getHost() {
                return serviceInstance.getHost();
            }

            @Override
            public int getPort() {
                return serviceInstance.getPort();
            }

            @Override
            public boolean isSecure() {
                return serviceInstance.isSecure();
            }

            @Override
            public URI getUri() {
                return serviceInstance.getUri();
            }

            @Override
            public Map<String, String> getMetadata() {
                return serviceInstance.getMetadata();
            }
        };
    }
}
