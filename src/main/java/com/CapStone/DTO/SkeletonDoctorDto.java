package com.CapStone.DTO;

import lombok.Data;

@Data
public class SkeletonDoctorDto {
    private String firstName;
    private String lastName;
    private String email;
    private String specialisation = "";
}
