package com.example.parkinglot.service;

import com.example.parkinglot.dto.PageResponse;
import com.example.parkinglot.dto.VehicleCreateRequest;
import com.example.parkinglot.dto.VehicleResponse;
import com.example.parkinglot.entity.Vehicle;
import com.example.parkinglot.repository.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    /**
     * Lấy danh sách xe có phân trang, sắp xếp và lọc theo từ khóa.
     *
     * @param page      Số trang (bắt đầu từ 0)
     * @param size      Số phần tử mỗi trang
     * @param sortBy    Tên trường cần sắp xếp (ví dụ: licensePlate, color, id)
     * @param direction Hướng sắp xếp (ASC / DESC)
     * @param keyword   Từ khóa tìm kiếm theo biển số hoặc màu xe
     * @return PageResponse<VehicleResponse>
     */
    public PageResponse<VehicleResponse> getPagedVehicles(int page, int size, String sortBy, String direction, String keyword) {
        // 1. Safety check: page < 0 -> mặc định về 0
        if (page < 0) {
            page = 0;
        }

        // 2. Xử lý sắp xếp (Sort):
        // Nếu không có sortBy hoặc direction -> Sort.unsorted()
        Sort sort = Sort.unsorted();
        if (sortBy != null && !sortBy.trim().isEmpty() && direction != null && !direction.trim().isEmpty()) {
            Sort.Direction sortDirection = direction.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
            sort = Sort.by(sortDirection, sortBy.trim());
        }

        // 3. Tạo Pageable từ page, size, sort
        Pageable pageable = PageRequest.of(page, size, sort);

        // 4. Lấy dữ liệu thông qua DTO Projection từ Repository
        Page<VehicleResponse> vehicleResponsePage = vehicleRepository.findAllByKeyword(
                (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null,
                pageable
        );

        // 5. Đóng gói vào PageResponse chuẩn hóa
        return PageResponse.<VehicleResponse>builder()
                .items(vehicleResponsePage.getContent())
                .page(vehicleResponsePage.getNumber())
                .size(vehicleResponsePage.getSize())
                .totalItems(vehicleResponsePage.getTotalElements())
                .totalPages(vehicleResponsePage.getTotalPages())
                .isLast(vehicleResponsePage.isLast())
                .build();
    }

    /**
     * Tạo mới một phương tiện vào hệ thống.
     */
    public VehicleResponse createVehicle(VehicleCreateRequest request) {
        Vehicle vehicle = Vehicle.builder()
                .licensePlate(request.getLicensePlate())
                .color(request.getColor())
                .type(request.getVehicleType())
                .build();

        Vehicle saved = vehicleRepository.save(vehicle);

        return VehicleResponse.builder()
                .id(saved.getId())
                .licensePlate(saved.getLicensePlate())
                .color(saved.getColor())
                .vehicleType(saved.getType())
                .build();
    }
}
