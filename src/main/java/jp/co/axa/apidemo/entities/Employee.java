package jp.co.axa.apidemo.entities;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEE")
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name="EMPLOYEE_NAME")
    private String name;

    @NonNull
    @Column(name="EMPLOYEE_SALARY")
    private BigDecimal salary;

    @NonNull
    @Column(name="DEPARTMENT")
    private String department;

}
