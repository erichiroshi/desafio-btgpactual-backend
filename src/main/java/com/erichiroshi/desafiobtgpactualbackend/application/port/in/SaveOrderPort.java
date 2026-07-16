package com.erichiroshi.desafiobtgpactualbackend.application.port.in;

import com.erichiroshi.desafiobtgpactualbackend.application.input.OrderInput;

public interface SaveOrderPort {

    void execute(OrderInput input);
}
