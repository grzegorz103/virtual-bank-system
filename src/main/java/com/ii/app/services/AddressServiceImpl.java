package com.ii.app.services;

import com.ii.app.dto.edit.AddressEdit;
import com.ii.app.dto.out.AddressOut;
import com.ii.app.mappers.AddressMapper;
import com.ii.app.models.user.Address;
import com.ii.app.repositories.AddressRepository;
import com.ii.app.services.interfaces.AddressService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository,
                              AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public AddressOut update(Long id, AddressEdit addressEdit) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        if (StringUtils.isNotBlank(address.getCity()))
            address.setCity(addressEdit.getCity().trim());
        if (StringUtils.isNotBlank(address.getPhoneNumber()))
            address.setPhoneNumber(addressEdit.getPhoneNumber().trim());
        if (StringUtils.isNotBlank(address.getHouseNumber()))
            address.setHouseNumber(addressEdit.getHouseNumber().trim());
        if (StringUtils.isNotBlank(address.getPostCode()))
            address.setPostCode(addressEdit.getPostCode().trim());
        if (StringUtils.isNotBlank(address.getStreet()))
            address.setStreet(addressEdit.getStreet().trim());
        if (StringUtils.isNotBlank(address.getName()))
            address.setName(addressEdit.getName().trim());
        if (StringUtils.isNotBlank(address.getSurname()))
            address.setSurname(addressEdit.getSurname().trim());
        if (Objects.nonNull(addressEdit.getDateOfBirth()))
            address.setDateOfBirth(addressEdit.getDateOfBirth());

        return addressMapper.entityToDTO(addressRepository.save(address));
    }
}
