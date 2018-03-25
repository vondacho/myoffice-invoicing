package edu.noia.myoffice.sale.common.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.noia.myoffice.common.domain.vo.Amount;

public interface CartItemMixin {

    @JsonIgnore
    Amount getPrice();
}
