package com.erichiroshi.desafiobtgpactualbackend.application.port.in;

import com.erichiroshi.desafiobtgpactualbackend.application.output.OrderOutput;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FindOrderPort {

    Page<OrderOutput> execute(long customerId, Pageable pageable);
}
