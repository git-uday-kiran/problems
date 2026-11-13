package problems.parking_lot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VehicleType {
    CAR(100),
    AUTO(80),
    BIKE(50),
    BYCYCLE(20),
    ;

    private final int price;
}
