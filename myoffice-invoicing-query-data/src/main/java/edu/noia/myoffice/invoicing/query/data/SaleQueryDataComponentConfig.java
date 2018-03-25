package edu.noia.myoffice.invoicing.query.data;

import edu.noia.myoffice.sale.domain.vo.CartId;
import edu.noia.myoffice.sale.query.data.adapter.CartStateRepositoryAdapter;
import edu.noia.myoffice.sale.query.data.hateoas.CartIdResourceProcessor;
import edu.noia.myoffice.sale.query.data.jpa.JpaCartState;
import edu.noia.myoffice.sale.query.data.jpa.JpaCartStateRepository;
import edu.noia.myoffice.sale.query.repository.CartStateRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.core.support.EntityLookupSupport;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@EntityScan
@Configuration
public class SaleQueryDataComponentConfig {

    @Bean
    public CartStateRepository cartStateRepository(JpaCartStateRepository jpaCartStateRepository) {
        return new CartStateRepositoryAdapter(jpaCartStateRepository);
    }

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return new RepositoryRestConfigurerAdapter() {
            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
                config.setBasePath("/api/sale/query/v1/");
            }
        };
    }

    @Bean
    public EntityLookupSupport<JpaCartState> cartStateEntityLookupSupport(JpaCartStateRepository repository) {
        return new EntityLookupSupport<JpaCartState>() {
            @Override
            public Serializable getResourceIdentifier(JpaCartState entity) {
                return entity.getId().getId();
            }

            @Override
            public Optional<JpaCartState> lookupEntity(Object id) {
                return repository.findById(CartId.of(UUID.fromString(id.toString())));
            }
        };
    }

    @Bean
    public ResourceProcessor<Resource<CartId>> cartIdResourceProcessor(RepositoryEntityLinks entityLinks) {
        return new CartIdResourceProcessor(entityLinks);
    }
}
