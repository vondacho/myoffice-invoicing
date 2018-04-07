package edu.noia.myoffice.invoicing.domain.vo;

import edu.noia.myoffice.common.domain.vo.Percentage;

public interface DefaultValues {

    Percentage getTaxRate();

    Percentage getDiscountRate();

    Integer getDelayDayCount();
}
