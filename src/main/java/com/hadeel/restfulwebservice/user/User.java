package com.hadeel.restfulwebservice.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@ApiModel(description = "This is the user object in the system")
public class User {

    private Integer id;

    @Size(min = 2, message = "Name should at least have 2 characters")
    private String name;

    @Past
    @ApiModelProperty(notes = "Birthdate cannot be in the past")
    private Date birthDate;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
