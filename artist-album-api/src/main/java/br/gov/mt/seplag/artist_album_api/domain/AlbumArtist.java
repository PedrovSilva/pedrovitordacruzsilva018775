package br.gov.mt.seplag.artist_album_api.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "album_artists", uniqueConstraints = {@UniqueConstraint(columnNames = {"album_id", "artist_id"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AlbumArtist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "album_id", nullable = false)
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artist_id", nullable = false)
    private Artist artist;

    @Column(name = "is_main_singer")
    private Boolean isMainSinger = false;

}