package com.ii.app.services;

import com.ii.app.dto.edit.AddressEdit;
import com.ii.app.dto.out.AddressOut;
import com.ii.app.mappers.AddressMapper;
import com.ii.app.models.user.Address;
import com.ii.app.repositories.AddressRepository;
import com.ii.app.services.interfaces.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService
{
        private final AddressRepository addressRepository;

        private final AddressMapper addressMapper;

        @Autowired
        public AddressServiceImpl ( AddressRepository addressRepository,
                                    AddressMapper addressMapper )
        {
                this.addressRepository = addressRepository;
                this.addressMapper = addressMapper;
        }

        @Override
        public AddressOut update ( Long id, AddressEdit addressEdit )
        {
                Address address = addressRepository.findById( id ).orElseThrow( () -> new RuntimeException( "Not found" ) );
                address.setCity( addressEdit.getCity() );
                address.setPhoneNumber( addressEdit.getPhoneNumber() );
                address.setHouseNumber( addressEdit.getHouseNumber() );
                address.setPostCode( addressEdit.getPostCode() );
                address.setStreet( addressEdit.getStreet() );

                return addressMapper.entityToDTO( addressRepository.save( address ) );
        }
}
