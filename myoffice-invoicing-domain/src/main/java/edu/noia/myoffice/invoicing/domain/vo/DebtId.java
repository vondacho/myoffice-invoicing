package edu.noia.myoffice.invoicing.domain.vo;

import edu.noia.myoffice.common.domain.vo.Identity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@EqualsAndHashCode(of = "id", callSuper = false, doNotUseGetters = true)
@RequiredArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class DebtId implements Identity {
    @NonNull
    UUID id;

    public static DebtId random() {
        return new DebtId(UUID.randomUUID());
    }
}
