package com.datamanagerapi.datamanagerapi.Models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Setter
@Getter
//@ToString
@Entity
@Table(name="institution")
public class Institution implements Serializable {
    @Id
    @Column(name = "institution_id", nullable = false)
    private int institution_id;

    @Column(name="institution_name")
    private String institution_name;
    @Column(name="institution_type")
    private String institution_type;

}
