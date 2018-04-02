package edu.noia.myoffice.invoicing.domain.vo;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@EqualsAndHashCode(of = {"customerId"}, doNotUseGetters = true, callSuper = false)
@RequiredArgsConstructor(staticName = "of")
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class Affiliate {

    @NonNull
    CustomerId customerId;
    Boolean primaryDebtor = false;
}
