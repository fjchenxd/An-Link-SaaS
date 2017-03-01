package com.miteno.com.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SirqulAccount entity.
 */
public class SirqulAccountDTO implements Serializable {

    private Long id;

    @Size(max = 20)
    private String name;

    @Size(max = 32)
    private String username;

    @Size(max = 20)
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SirqulAccountDTO sirqulAccountDTO = (SirqulAccountDTO) o;

        if ( ! Objects.equals(id, sirqulAccountDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SirqulAccountDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", username='" + username + "'" +
            ", password='" + password + "'" +
            '}';
    }
}
