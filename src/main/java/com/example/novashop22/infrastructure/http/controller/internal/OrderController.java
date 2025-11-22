package com.example.novashop22.infrastructure.http.controller.internal;


import java.util.List;

import com.example.novashop22.commun.payload.AppConstants;
import com.example.novashop22.domain.model.OrderDTO;
import com.example.novashop22.domain.model.OrderResponse;
import com.example.novashop22.domain.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


//import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")
//@SecurityRequirement(name = "E-Commerce Application")
@Tag(name = "Orders", description = "Endpoints for placing and managing orders")
public class OrderController {

    @Autowired
    public IOrderService orderService;

    @Operation(summary = "Place order", description = "Place an order for the specified user's cart using a payment method")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order placed"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Cart or user not found")
    })
    @PostMapping("/public/users/{emailId}/carts/{cartId}/payments/{paymentMethod}/order")
    public ResponseEntity<OrderDTO> orderProducts(@PathVariable String emailId, @PathVariable Long cartId, @PathVariable String paymentMethod) {
        OrderDTO order = orderService.placeOrder(emailId, cartId, paymentMethod);

        return new ResponseEntity<OrderDTO>(order, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all orders", description = "Retrieve paginated orders (admin)")
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "Orders found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/admin/orders")
    public ResponseEntity<OrderResponse> getAllOrders(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_ORDERS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder) {

        OrderResponse orderResponse = orderService.getAllOrders(pageNumber, pageSize, sortBy, sortOrder);

        return new ResponseEntity<OrderResponse>(orderResponse, HttpStatus.FOUND);
    }

    @Operation(summary = "Get orders by user", description = "Retrieve all orders placed by a user")
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "Orders found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("public/users/{emailId}/orders")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(@PathVariable String emailId) {
        List<OrderDTO> orders = orderService.getOrdersByUser(emailId);

        return new ResponseEntity<List<OrderDTO>>(orders, HttpStatus.FOUND);
    }

    @Operation(summary = "Get order by id for user", description = "Retrieve a specific order for a user")
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "Order found"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @GetMapping("public/users/{emailId}/orders/{orderId}")
    public ResponseEntity<OrderDTO> getOrderByUser(@PathVariable String emailId, @PathVariable Long orderId) {
        OrderDTO order = orderService.getOrder(emailId, orderId);

        return new ResponseEntity<OrderDTO>(order, HttpStatus.FOUND);
    }

    @Operation(summary = "Update order status", description = "Update the status of an order (admin)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order updated"),
            @ApiResponse(responseCode = "404", description = "Order not found")
    })
    @PutMapping("admin/users/{emailId}/orders/{orderId}/orderStatus/{orderStatus}")
    public ResponseEntity<OrderDTO> updateOrderByUser(@PathVariable String emailId, @PathVariable Long orderId, @PathVariable String orderStatus) {
        OrderDTO order = orderService.updateOrder(emailId, orderId, orderStatus);

        return new ResponseEntity<OrderDTO>(order, HttpStatus.OK);
    }

}
