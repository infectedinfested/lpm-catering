package be.brickbit.lpm.core.client.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
public class UserDetailsDto {
    private Long id;
    private String username;
    private Long age;
    private Integer seatNumber;
    private BigDecimal wallet;
    private String mood;
    private String firstName;
    private String lastName;
    private String email;
}