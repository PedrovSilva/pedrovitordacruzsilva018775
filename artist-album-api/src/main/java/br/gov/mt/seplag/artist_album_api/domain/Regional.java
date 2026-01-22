package br.gov.mt.seplag.artist_album_api.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "regionals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Regional {

    @Id
    @EqualsAndHashCode.Include
    private Integer id; // ID vindo da API externa

    @NotBlank
    @Column(nullable = false, length = 200)
    private String nome;

    @Column(nullable = false)
    private Boolean ativo = true;
}
