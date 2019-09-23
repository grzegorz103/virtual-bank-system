package com.ii.app.services.interfaces;

import com.ii.app.dto.in.CreditIn;
import com.ii.app.dto.out.CreditOut;
import com.ii.app.models.Credit;

public interface CreditService
{
        CreditOut create ( CreditIn creditIn );
}
