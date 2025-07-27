package ru.skypro.homework.model;

import lombok.Data;
import ru.skypro.homework.model.User;
import ru.skypro.homework.model.Ad;

import javax.persistence.*;


@Data
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "bytea", nullable = false)
    private byte[] data;

    @Column(nullable = false)
    private String mediaType;

   /*

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "ad_id")
   private Ad ad;

   */
}