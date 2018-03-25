package edu.noia.myoffice.sale.rest.endpoint;

import edu.noia.myoffice.common.rest.dto.RevisionDto;
import edu.noia.myoffice.common.rest.util.IdentifiantPropertyEditorSupport;
import edu.noia.myoffice.sale.domain.vo.CartId;
import edu.noia.myoffice.sale.query.data.jpa.JpaCartStateRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/sale/v1/carts")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartRevisionEndpoint {

    @Autowired
    JpaCartStateRepository repository;

    @GetMapping(value = "/{id}/revisions")
    public ResponseEntity<List<RevisionDto>> cartRevisions(@PathVariable(value = "id") Long primaryId) {
        return ok(RevisionDto.from(repository.findRevisions(primaryId)));
    }

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.registerCustomEditor(CartId.class,
                new IdentifiantPropertyEditorSupport<>(s -> CartId.of(UUID.fromString(s))));
    }
}
