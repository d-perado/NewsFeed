package org.example.newsfeed.common.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "follows")
@Getter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "followed_id")
    private User followedId;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private User followingId;
}
