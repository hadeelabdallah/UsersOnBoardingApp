package com.hadeel.restfulwebservice.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "This is the user object in the system")
@Entity
public class User {

    public User(Integer id, String name, Date birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    @Id
    @GeneratedValue
    private Integer id;

    @Size(min = 2, message = "Name should at least have 2 characters")
    private String name;

    @Past
    @ApiModelProperty(notes = "Birthdate cannot be in the past")
    private Date birthDate;

    @OneToMany(mappedBy = "user")
    private List<Post> posts;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
