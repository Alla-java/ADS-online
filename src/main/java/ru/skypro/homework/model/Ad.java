package ru.skypro.homework.model;

import liquibase.repackaged.org.apache.commons.lang3.builder.ToStringExclude;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Builder
@Table(name = "ads")
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

public Ad(Integer id,String title,String description,Integer price,Image image,User author,List<Comment> comments){
    this.id=id;
    this.title=title;
    this.description=description;
    this.price=price;
    this.image=image;
    this.author=author;
    this.comments=comments;
}

public Integer getId(){
    return id;
}

public void setId(Integer id){
    this.id=id;
}

public String getTitle(){
    return title;
}

public void setTitle(String title){
    this.title=title;
}

public String getDescription(){
    return description;
}

public void setDescription(String description){
    this.description=description;
}

public Integer getPrice(){
    return price;
}

public void setPrice(Integer price){
    this.price=price;
}

public Image getImage(){
    return image;
}

public void setImage(Image image){
    this.image=image;
}

public User getAuthor(){
    return author;
}

public void setAuthor(User author){
    this.author=author;
}

public List<Comment> getComments(){
    return comments;
}

public void setComments(List<Comment> comments){
    this.comments=comments;
}

@Override
public boolean equals(Object o){
    if(this==o)
        return true;
    if(!(o instanceof Ad))
        return false;
    Ad ad=(Ad)o;
    return Objects.equals(id,ad.id)&&Objects.equals(title,ad.title)&&Objects.equals(description,ad.description)&&Objects.equals(price,ad.price)&&Objects.equals(image,ad.image)&&Objects.equals(author,ad.author)&&Objects.equals(comments,ad.comments);
}

@Override
public int hashCode(){
    return Objects.hash(id,title,description,price,image,author,comments);
}
}