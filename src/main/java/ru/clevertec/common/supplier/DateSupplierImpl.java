package ru.clevertec.common.supplier;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class DateSupplierImpl implements DateSupplier {
    @Override
    public OffsetDateTime getCurrentDateTime() {
        return OffsetDateTime.now();
    }
}
