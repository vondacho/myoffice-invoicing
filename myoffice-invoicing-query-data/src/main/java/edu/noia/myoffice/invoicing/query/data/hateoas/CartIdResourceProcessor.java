package edu.noia.myoffice.invoicing.query.data.hateoas;

import edu.noia.myoffice.sale.domain.vo.CartId;
import edu.noia.myoffice.sale.query.data.jpa.JpaCartState;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

@RequiredArgsConstructor
public class CartIdResourceProcessor implements ResourceProcessor<Resource<CartId>> {
    @NonNull
    RepositoryEntityLinks entityLinks;

    @Override
    public Resource<CartId> process(Resource<CartId> resource) {
        resource.add(entityLinks.linkToSingleResource(JpaCartState.class, resource.getContent().getId()));
        return resource;
    }
}
