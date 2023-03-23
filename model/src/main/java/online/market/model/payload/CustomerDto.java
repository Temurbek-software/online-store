package online.market.model.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto
{
    @NotEmpty(message = "First name can't be empty!")
    private String firstName;

    @NotEmpty(message = "Last name can't be empty!")
    private String lastName;

    @NotEmpty(message = "Email can't be empty!")
    @Email(message = "*Please provide a valid Email")
    private String username;

    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @NotEmpty(message = "*Please provide your phone")
    private String phoneNumber;

    @NotEmpty(message = "Confirm Password is mandatory")
    private String confirmPassword;

    private String countryName;
    private String cityName;
    private String postalCode;
    private String address;
}
