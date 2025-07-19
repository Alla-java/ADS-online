package ru.skypro.homework.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.User;

import javax.persistence.*;
import java.awt.*;
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
private Long id;

@Column
private String title;

@Column
private String description;

@Column
private Integer price;

@OneToOne
@JoinColumn(name = "image_id")
private Image image;

@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "author_id")
private User author;

@OneToMany(fetch = FetchType.LAZY)
@JoinColumn(name = "comment_id")
private List <Comment> comments;
}