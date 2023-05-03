package hibernate.entity;

import lombok.*;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Airport {
    private String code;
    private String country;
    private String city;
}
