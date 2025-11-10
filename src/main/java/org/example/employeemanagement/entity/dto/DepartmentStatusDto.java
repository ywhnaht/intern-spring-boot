package org.example.employeemanagement.entity.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepartmentStatusDto {
    String departmentName;
    long employeeCount;
}
