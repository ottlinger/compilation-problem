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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

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

// https://stackoverflow.com/questions/45692179/spring-boot-validation-message-is-not-being-resolved
   @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

@Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
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
            log.error("{} validation errors: {}", result.getErrorCount(), result.getAllErrors());
            log.error("{} global validation errors: {}", result.getGlobalErrorCount(), result.getGlobalErrors());
            return new ModelAndView("names.html");
        }

        log.info("Saved form result {}", form);

        return new ModelAndView("redirect:/");
    }

}
