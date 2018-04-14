package edu.noia.myoffice.invoicing.query.data;

import edu.noia.myoffice.common.data.jpa.hibernate.converter.AmountConverter;
import edu.noia.myoffice.common.data.jpa.hibernate.converter.PercentageConverter;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import edu.noia.myoffice.invoicing.query.data.adapter.DebtStateRepositoryAdapter;
import edu.noia.myoffice.invoicing.query.data.adapter.FolderStateRepositoryAdapter;
import edu.noia.myoffice.invoicing.query.data.hateoas.DebtIdResourceProcessor;
import edu.noia.myoffice.invoicing.query.data.hateoas.FolderIdResourceProcessor;
import edu.noia.myoffice.invoicing.query.data.jpa.JpaDebtState;
import edu.noia.myoffice.invoicing.query.data.jpa.JpaDebtStateRepository;
import edu.noia.myoffice.invoicing.query.data.jpa.JpaFolderState;
import edu.noia.myoffice.invoicing.query.data.jpa.JpaFolderStateRepository;
import edu.obya.myoffice.invoicing.query.repository.DebtStateRepository;
import edu.obya.myoffice.invoicing.query.repository.FolderStateRepository;
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
@EntityScan(
        basePackageClasses = {
                JpaDebtState.class,
                JpaFolderState.class,
                AmountConverter.class,
                PercentageConverter.class
        }
)
@Configuration
public class InvoicingQueryDataComponentConfiguration {

    @Bean
    public FolderStateRepository cartStateRepository(JpaFolderStateRepository jpaFolderStateRepository) {
        return new FolderStateRepositoryAdapter(jpaFolderStateRepository);
    }

    @Bean
    public DebtStateRepository debtStateRepository(JpaDebtStateRepository jpaDebtStateRepository) {
        return new DebtStateRepositoryAdapter(jpaDebtStateRepository);
    }

    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {
        return new RepositoryRestConfigurerAdapter() {
            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
                config.setBasePath("/api/invoicing/query/v1/");
            }
        };
    }

    @Bean
    public EntityLookupSupport<JpaFolderState> folderStateEntityLookupSupport(JpaFolderStateRepository repository) {
        return new EntityLookupSupport<JpaFolderState>() {
            @Override
            public Serializable getResourceIdentifier(JpaFolderState entity) {
                return entity.getId().getId();
            }

            @Override
            public Optional<JpaFolderState> lookupEntity(Object id) {
                return repository.findById(FolderId.of(UUID.fromString(id.toString())));
            }
        };
    }

    @Bean
    public EntityLookupSupport<JpaDebtState> debtStateEntityLookupSupport(JpaDebtStateRepository repository) {
        return new EntityLookupSupport<JpaDebtState>() {
            @Override
            public Serializable getResourceIdentifier(JpaDebtState entity) {
                return entity.getId().getId();
            }

            @Override
            public Optional<JpaDebtState> lookupEntity(Object id) {
                return repository.findById(DebtId.of(UUID.fromString(id.toString())));
            }
        };
    }

    @Bean
    public ResourceProcessor<Resource<FolderId>> folderIdResourceProcessor(RepositoryEntityLinks entityLinks) {
        return new FolderIdResourceProcessor(entityLinks);
    }

    @Bean
    public ResourceProcessor<Resource<DebtId>> debtIdResourceProcessor(RepositoryEntityLinks entityLinks) {
        return new DebtIdResourceProcessor(entityLinks);
    }
}
