package com.example.demo;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


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
//    private List<@NotEmpty(message = "{NotEmpty.option.optionelement}") String> options = new ArrayList<>();
//    private List<String> options = new ArrayList<>();
    private List<@NotEmpty(message = "{NotEmpty.option.optionelement}") String> options = new ArrayList<>();
}

@Controller
@RequestMapping("names")
@Slf4j
class NameController {

    @GetMapping(value = "/")
    public ModelAndView addNames(ModelMap model) {

        final ValidationInputForm form = ValidationInputForm.builder().build();
        model.addAttribute("proposal", form);

        return new ModelAndView("names.html", model);
    }

    @PostMapping(value = "/")
    public ModelAndView saveNames(final @Valid @ModelAttribute("proposal") ValidationInputForm form, final BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            log.warn("{} validation errors: {}", result.getErrorCount(), result.getAllErrors());
            return new ModelAndView("names.html");
        }

        log.info("Saved form result {}", form);

        return new ModelAndView("redirect:/");
    }

}
