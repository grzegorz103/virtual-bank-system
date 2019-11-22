package com.ii.app.controllers;

import com.ii.app.dto.edit.AddressEdit;
import com.ii.app.dto.out.AddressOut;
import com.ii.app.services.interfaces.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PutMapping("/{id}")
    public AddressOut update(@PathVariable("id") Long id,
                             @RequestBody AddressEdit addressEdit) {
        return addressService.update(id, addressEdit);
    }
}
