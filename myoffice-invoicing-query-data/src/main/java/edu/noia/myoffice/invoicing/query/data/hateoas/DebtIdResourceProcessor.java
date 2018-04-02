package edu.noia.myoffice.invoicing.query.data.hateoas;

import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import edu.noia.myoffice.invoicing.query.data.jpa.JpaDebtState;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

@RequiredArgsConstructor
public class DebtIdResourceProcessor implements ResourceProcessor<Resource<DebtId>> {
    @NonNull
    RepositoryEntityLinks entityLinks;

    @Override
    public Resource<DebtId> process(Resource<DebtId> resource) {
        resource.add(entityLinks.linkToSingleResource(JpaDebtState.class, resource.getContent().getId()));
        return resource;
    }
}
