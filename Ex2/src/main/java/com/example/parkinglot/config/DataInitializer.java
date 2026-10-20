package com.example.parkinglot.config;

import com.example.parkinglot.entity.Vehicle;
import com.example.parkinglot.entity.Zone;
import com.example.parkinglot.enums.VehicleType;
import com.example.parkinglot.repository.VehicleRepository;
import com.example.parkinglot.repository.ZoneRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;
    private final ZoneRepository zoneRepository;

    public DataInitializer(VehicleRepository vehicleRepository, ZoneRepository zoneRepository) {
        this.vehicleRepository = vehicleRepository;
        this.zoneRepository = zoneRepository;
    }

    @Override
    public void run(String... args) {
        if (zoneRepository.count() == 0) {
            zoneRepository.saveAll(List.of(
                    Zone.builder().name("Khu A").capacity(50).occupiedSpots(0).build(),
                    Zone.builder().name("Khu B").capacity(30).occupiedSpots(0).build()
            ));
        }

        if (vehicleRepository.count() == 0) {
            vehicleRepository.saveAll(List.of(
                    Vehicle.builder().licensePlate("18B-11111").color("Đỏ").type(VehicleType.BIKE).build(),
                    Vehicle.builder().licensePlate("18B-22222").color("Xanh").type(VehicleType.BIKE).build(),
                    Vehicle.builder().licensePlate("18A-33333").color("Trắng").type(VehicleType.CAR).build(),
                    Vehicle.builder().licensePlate("29A-44444").color("Đen").type(VehicleType.CAR).build(),
                    Vehicle.builder().licensePlate("30E-55555").color("Bạc").type(VehicleType.CAR).build(),
                    Vehicle.builder().licensePlate("51F-66666").color("Vàng").type(VehicleType.CAR).build(),
                    Vehicle.builder().licensePlate("18C-77777").color("Xám").type(VehicleType.BIKE).build()
            ));
            System.out.println("✅ [Ex2] Đã khởi tạo dữ liệu mẫu cho danh sách xe!");
        }
    }
}
