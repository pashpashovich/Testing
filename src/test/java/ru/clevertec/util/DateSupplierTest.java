package ru.clevertec.util;

import ru.clevertec.common.supplier.DateSupplier;

import java.time.OffsetDateTime;

public class DateSupplierTest implements DateSupplier {

    @Override
    public OffsetDateTime getCurrentDateTime() {
        return OffsetDateTime.parse("2024-08-08T23:23:23.123123Z");
    }
}
