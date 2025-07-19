package ru.skypro.homework.model;

import liquibase.repackaged.org.apache.commons.lang3.builder.ToStringExclude;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ads")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ad {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

@Column
private String title;

@Column
private String description;

@Column
private Integer price;

@OneToOne
@JoinColumn(name = "image_id")
@OnDelete(action = OnDeleteAction.CASCADE)
private Image image;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id")
private User author;

@OneToMany(mappedBy = "ad", fetch = FetchType.LAZY)
@OnDelete(action = OnDeleteAction.CASCADE)
@ToStringExclude
private List <Comment> comments;
}