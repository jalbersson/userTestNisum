package com.nisum.usercreation.apiuser.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phone")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @Column(name = "city_code")
    private String citycode;

    @Column(name = "country_code")
    private String contryCode;

    public Phone(String number, String citycode, String contryCode) {
        this.number = number;
        this.citycode = citycode;
        this.contryCode = contryCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCitycode() {
        return citycode;
    }

    public void setCitycode(String cityCode) {
        this.citycode = cityCode;
    }

    public String getContrycode() {
        return contryCode;
    }

    public void setContrycode(String contryCode) {
        this.contryCode = contryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Phone phone = (Phone) o;
        return number == phone.number && citycode == phone.citycode && contryCode == phone.contryCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, citycode, contryCode);
    }

    @Override
    public String toString() {
        return "Phone{" +
                "number=" + number +
                ", cityCode=" + citycode +
                ", countryCode=" + contryCode +
                '}';
    }
}
