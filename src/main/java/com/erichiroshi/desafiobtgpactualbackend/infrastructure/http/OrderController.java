package com.erichiroshi.desafiobtgpactualbackend.infrastructure.http;

import com.erichiroshi.desafiobtgpactualbackend.application.output.OrderOutput;
import com.erichiroshi.desafiobtgpactualbackend.application.output.CustomerOrderSummaryOutput;
import com.erichiroshi.desafiobtgpactualbackend.application.port.in.FindOrderPort;
import com.erichiroshi.desafiobtgpactualbackend.application.port.in.FindCustomerOrderSummaryPort;
import com.erichiroshi.desafiobtgpactualbackend.infrastructure.http.response.ApiResponse;
import com.erichiroshi.desafiobtgpactualbackend.infrastructure.http.response.OrderResponse;
import com.erichiroshi.desafiobtgpactualbackend.infrastructure.http.response.CustomerOrderSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/customers")
public class OrderController {

    private final FindOrderPort findOrderPort;
    private final FindCustomerOrderSummaryPort findCustomerOrderSummaryPort;

    public OrderController(FindOrderPort findOrderPort, FindCustomerOrderSummaryPort findCustomerOrderSummaryPort) {
        this.findOrderPort = findOrderPort;
        this.findCustomerOrderSummaryPort = findCustomerOrderSummaryPort;
    }

    @GetMapping("/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrders(@PathVariable long customerId, Pageable pageable) {
        Page<OrderOutput> outputPage = findOrderPort.execute(customerId, pageable);
        Page<OrderResponse> responsePage = outputPage.map(OrderResponse::fromOutput);

        CustomerOrderSummaryOutput summary = findCustomerOrderSummaryPort.execute(customerId);

        Map<String, Object> summaryMap = Map.of(
                "customerId", customerId,
                "quantityOrders", summary.quantityOrder(),
                "totalAmountOrders", summary.total()
        );

        return ResponseEntity.ok(new ApiResponse<>(summaryMap, responsePage));
    }

    @GetMapping("/{customerId}/orders/summary")
    public ResponseEntity<CustomerOrderSummaryResponse> getSummaries(@PathVariable long customerId) {
        CustomerOrderSummaryOutput output = findCustomerOrderSummaryPort.execute(customerId);
        return ResponseEntity.ok(CustomerOrderSummaryResponse.fromOutput(output));
    }
}
