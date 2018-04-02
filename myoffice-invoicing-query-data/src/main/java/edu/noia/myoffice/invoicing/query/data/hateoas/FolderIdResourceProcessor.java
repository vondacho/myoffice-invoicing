package edu.noia.myoffice.invoicing.query.data.hateoas;

import edu.noia.myoffice.invoicing.domain.vo.FolderId;
import edu.noia.myoffice.invoicing.query.data.jpa.JpaFolderState;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

@RequiredArgsConstructor
public class FolderIdResourceProcessor implements ResourceProcessor<Resource<FolderId>> {
    @NonNull
    RepositoryEntityLinks entityLinks;

    @Override
    public Resource<FolderId> process(Resource<FolderId> resource) {
        resource.add(entityLinks.linkToSingleResource(JpaFolderState.class, resource.getContent().getId()));
        return resource;
    }
}
