package edu.noia.myoffice.invoicing.domain.vo;

import edu.noia.myoffice.common.domain.vo.Amount;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@ToString
@Getter
@AllArgsConstructor(staticName = "of")
@RequiredArgsConstructor(staticName = "of")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {

    @NonNull
    Amount amount;
    @NonNull
    LocalDate date;
    Ticket ticket;
}
