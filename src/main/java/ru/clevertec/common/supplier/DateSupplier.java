package ru.clevertec.common.supplier;

import java.time.OffsetDateTime;

public interface DateSupplier {
    OffsetDateTime getCurrentDateTime();
}
