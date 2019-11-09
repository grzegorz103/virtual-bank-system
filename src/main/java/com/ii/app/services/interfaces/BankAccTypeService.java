package com.ii.app.services.interfaces;

import com.ii.app.dto.edit.BankAccTypeEdit;
import com.ii.app.dto.out.BankAccTypeOut;

import java.util.List;

public interface BankAccTypeService {
    List<BankAccTypeOut> findAll();

    BankAccTypeOut update(Long id, BankAccTypeEdit bankAccTypeEdit);

    BankAccTypeOut findById(Long id);
}
