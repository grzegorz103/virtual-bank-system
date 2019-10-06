package com.ii.app.mappers;

import com.ii.app.dto.edit.AddressEdit;
import com.ii.app.dto.in.AddressIn;
import com.ii.app.dto.out.AddressOut;
import com.ii.app.models.user.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    Address DTOtoEntity(AddressIn addressIn);

    AddressOut entityToDTO(Address address);
}
