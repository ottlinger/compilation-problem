package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class ValidationCompileErrorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ValidationCompileErrorApplication.class, args);
	}

}

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
class ValidationInputForm {
    @Size(min = 1, max = 10)
    @NotEmpty(message = "{NotEmpty.option.options}")
    private List<@NotEmpty(message = "{NotEmpty.option.options}") String> options = new ArrayList<>();
}
