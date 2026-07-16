package com.erichiroshi.desafiobtgpactualbackend.application.port.in;

import com.erichiroshi.desafiobtgpactualbackend.application.output.SummaryOrdersCustomerOutput;

public interface FindOrderClienteSummaryPort {

    SummaryOrdersCustomerOutput execute(long customerId);
}
