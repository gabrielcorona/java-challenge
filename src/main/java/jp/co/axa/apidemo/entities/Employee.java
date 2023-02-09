package jp.co.axa.apidemo.entities;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="EMPLOYEE")
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="EMPLOYEE_NAME")
    private String name;

    @NotNull
    @Column(name="EMPLOYEE_SALARY")
    private BigDecimal salary;

    @NotNull
    @Column(name="DEPARTMENT")
    private String department;

}
