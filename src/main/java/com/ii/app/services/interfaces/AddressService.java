package com.ii.app.services.interfaces;

import com.ii.app.dto.edit.AddressEdit;
import com.ii.app.dto.out.AddressOut;

public interface AddressService
{
        AddressOut update (Long id, AddressEdit addressEdit);
}
