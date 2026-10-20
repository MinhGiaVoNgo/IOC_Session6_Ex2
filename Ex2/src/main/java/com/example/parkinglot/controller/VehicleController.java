package com.example.parkinglot.controller;

import com.example.parkinglot.dto.ApiResponse;
import com.example.parkinglot.dto.PageResponse;
import com.example.parkinglot.dto.VehicleCreateRequest;
import com.example.parkinglot.dto.VehicleResponse;
import com.example.parkinglot.service.VehicleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    /**
     * Endpoint tạo mới phương tiện:
     * POST /api/v1/vehicles
     */
    @PostMapping
    public ResponseEntity<ApiResponse<VehicleResponse>> createVehicle(@RequestBody VehicleCreateRequest request) {
        VehicleResponse createdVehicle = vehicleService.createVehicle(request);

        ApiResponse<VehicleResponse> response = ApiResponse.<VehicleResponse>builder()
                .success(true)
                .message("Tạo phương tiện thành công")
                .data(createdVehicle)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Endpoint lấy danh sách xe với Pageable, Sort và Keyword:
     * GET /api/v1/vehicles?page=0&size=5&sortBy=licensePlate&direction=ASC&keyword=18
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<VehicleResponse>>> getVehicles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String direction,
            @RequestParam(required = false) String keyword
    ) {
        PageResponse<VehicleResponse> pageResponse = vehicleService.getPagedVehicles(page, size, sortBy, direction, keyword);

        ApiResponse<PageResponse<VehicleResponse>> response = ApiResponse.<PageResponse<VehicleResponse>>builder()
                .success(true)
                .message("Lấy danh sách xe thành công")
                .data(pageResponse)
                .build();

        return ResponseEntity.ok(response);
    }
}
