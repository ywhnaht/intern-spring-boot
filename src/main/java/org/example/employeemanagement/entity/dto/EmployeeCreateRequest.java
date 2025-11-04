package org.example.employeemanagement.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeCreateRequest {
    @Size(min = 2, message = "Tên nhân viên phải tối thiểu 2 ký tự")
    @NotBlank(message = "Tên nhân viên không được để trống")
    String name;

    @Email(message = "Email phải đúng định dạng")
    String email;

    Long departmentId;
}
