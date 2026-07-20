package com.erichiroshi.desafiobtgpactualbackend.application.port.in;

import com.erichiroshi.desafiobtgpactualbackend.application.output.CustomerOrderSummaryOutput;

public interface FindCustomerOrderSummaryPort {

    CustomerOrderSummaryOutput execute(long customerId);
}
