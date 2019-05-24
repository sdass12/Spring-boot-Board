package com.hhk.board.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode(of = "uid")
@ToString
public class MemberVO {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true, length=50)
    private String uid;

    @Column(nullable = false, length=200)
    private String upw;

    @Column(nullable = false, unique = true, length=50)
    private String uemail;

    @CreationTimestamp
    private Date regdate;

    @UpdateTimestamp
    private Date updatedate;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="uid")
    private List<MemberRole> roles;

}