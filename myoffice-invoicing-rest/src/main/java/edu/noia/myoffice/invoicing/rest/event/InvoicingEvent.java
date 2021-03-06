package edu.noia.myoffice.invoicing.rest.event;

import edu.noia.myoffice.common.domain.event.Event;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoicingEvent<T> implements Event<T> {

    @NonNull
    Instant timestamp;
    @NonNull
    Class eventClass;
    @NonNull
    T payload;
}
