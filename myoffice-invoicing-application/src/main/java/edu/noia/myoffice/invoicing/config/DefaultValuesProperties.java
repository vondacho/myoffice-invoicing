package edu.noia.myoffice.invoicing.config;

import edu.noia.myoffice.common.domain.vo.Percentage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@ConfigurationProperties(prefix = "invoicing.default")
public class DefaultValuesProperties {
    @NotNull
    Percentage taxRate;
    @NotNull
    Percentage discountRate;
    @NotNull
    Integer delayDayCount;
}
