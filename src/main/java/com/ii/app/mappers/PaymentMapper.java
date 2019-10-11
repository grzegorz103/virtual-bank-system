package com.ii.app.mappers;

import com.ii.app.dto.out.PaymentOut;
import com.ii.app.models.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentOut entityToDto(Payment payment);
}
