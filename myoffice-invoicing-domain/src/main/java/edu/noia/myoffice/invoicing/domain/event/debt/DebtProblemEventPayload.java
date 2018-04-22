package edu.noia.myoffice.invoicing.domain.event.debt;

import edu.noia.myoffice.common.domain.event.ProblemEventPayload;
import edu.noia.myoffice.common.util.exception.Problem;
import edu.noia.myoffice.invoicing.domain.vo.DebtId;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;

@ToString
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DebtProblemEventPayload extends ProblemEventPayload {
    DebtId debtId;

    public DebtProblemEventPayload(@NonNull List<Problem> problems, @NonNull DebtId debtId) {
        super(problems);
        this.debtId = debtId;
    }
}
