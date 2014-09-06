package com.app.spring.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author malalanayake
 *
 */
@Entity
@Table(name = "CUSTOMER")
public class Customer implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6730983515674294472L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    private String tel;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public boolean equals(Object obj) {
        boolean retr = true;

        if (obj.getClass() == Customer.class) {
            Customer them = (Customer) obj;
            if (them.id != this.id) {
                retr = false;
            } else if (them.address.equalsIgnoreCase(this.address) == false) {
                retr = false;
            } else if (them.name.equalsIgnoreCase(this.name) == false) {
                retr = false;
            } else if (them.tel.equalsIgnoreCase(this.tel) == false) {
                retr = false;
            }
        } else {
            retr = false;
        }

        return retr;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        hash = 97 * hash + Objects.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.address);
        hash = 97 * hash + Objects.hashCode(this.tel);
        return hash;
    }

    @Override
    public String toString() {
        return "id:" + id + ", name:" + name + ", address:" + address
                + ", tel:" + tel;
    }
}
