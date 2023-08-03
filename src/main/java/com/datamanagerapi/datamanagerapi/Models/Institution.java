package com.datamanagerapi.datamanagerapi.Models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Setter
@Getter
@ToString
@Entity
public class Institution {
    @Id
    @Column(name = "institution_id", nullable = false)
    private int institution_id;

    private String institution_name,institution_type;

}
