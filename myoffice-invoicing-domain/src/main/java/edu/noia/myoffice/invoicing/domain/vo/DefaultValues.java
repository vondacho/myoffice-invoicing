package edu.noia.myoffice.invoicing.domain.vo;

import edu.noia.myoffice.common.domain.vo.Percentage;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DefaultValues {
    @NonNull
    Percentage taxRate;
    @NonNull
    Percentage discountRate;
    @NonNull
    Integer delayDayCount;
}
