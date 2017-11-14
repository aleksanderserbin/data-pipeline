package me.aserbin.datapipeline.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Map;

/**
 * Dummy entity to demonstrate Data Pipeline.
 */
@Entity
public class Dummy {

    private Integer someNumber;
    private String someString;
    private Boolean someBoolean;
    private LocalDate createdDate;

    public Dummy() {
    }

    @Id
    @GeneratedValue
    @Column(name = "some_number", nullable = false)
    public Integer getSomeNumber() {
        return someNumber;
    }

    public void setSomeNumber(Integer someNumber) {
        this.someNumber = someNumber;
    }


    @Column(name = "some_str", nullable = false)
    public String getSomeString() {
        return someString;
    }

    public void setSomeString(String someString) {
        this.someString = someString;
    }

    @Column(name = "some_bool", nullable = true)
    public Boolean getSomeBoolean() {
        return someBoolean;
    }

    public void setSomeBoolean(Boolean someBoolean) {
        this.someBoolean = someBoolean;
    }


    @JsonIgnore
    @Column(name = "created_at", nullable = false)
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
