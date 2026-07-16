package com.erichiroshi.desafiobtgpactualbackend.infrastructure.http;

import com.erichiroshi.desafiobtgpactualbackend.application.output.OrderOutput;
import com.erichiroshi.desafiobtgpactualbackend.application.output.SummaryOrdersCustomerOutput;
import com.erichiroshi.desafiobtgpactualbackend.application.port.in.FindOrderPort;
import com.erichiroshi.desafiobtgpactualbackend.application.port.in.FindOrderCustomerSummaryPort;
import com.erichiroshi.desafiobtgpactualbackend.infrastructure.http.response.ApiResponse;
import com.erichiroshi.desafiobtgpactualbackend.infrastructure.http.response.OrderResponse;
import com.erichiroshi.desafiobtgpactualbackend.infrastructure.http.response.SummaryCustomerOrderResponse;
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
    private final FindOrderCustomerSummaryPort findOrderCustomerSummaryPort;

    public OrderController(FindOrderPort findOrderPort, FindOrderCustomerSummaryPort findOrderCustomerSummaryPort) {
        this.findOrderPort = findOrderPort;
        this.findOrderCustomerSummaryPort = findOrderCustomerSummaryPort;
    }

    @GetMapping("/{customerId}/orders")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrders(@PathVariable long customerId, Pageable pageable) {
        Page<OrderOutput> outputPage = findOrderPort.execute(customerId, pageable);
        Page<OrderResponse> responsePage = outputPage.map(OrderResponse::fromOutput);

        SummaryOrdersCustomerOutput summary = findOrderCustomerSummaryPort.execute(customerId);

        Map<String, Object> summaryMap = Map.of(
                "customerId", customerId,
                "quantityOrders", summary.quantityOrder(),
                "totalAmountOrders", summary.total()
        );

        return ResponseEntity.ok(new ApiResponse<>(summaryMap, responsePage));
    }

    @GetMapping("/{customerId}/orders/summary")
    public ResponseEntity<SummaryCustomerOrderResponse> getSummaries(@PathVariable long customerId) {
        SummaryOrdersCustomerOutput output = findOrderCustomerSummaryPort.execute(customerId);
        return ResponseEntity.ok(SummaryCustomerOrderResponse.fromOutput(output));
    }
}
