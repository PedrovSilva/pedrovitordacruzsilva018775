-- Artistas e álbuns de exemplo
INSERT INTO artists (name, artist_type) VALUES
    ('Serj Tankian', 'SOLO'),
    ('Mike Shinoda', 'SOLO'),
    ('Michel Teló', 'SOLO'),
    ('Guns N'' Roses', 'BAND');

INSERT INTO albums (title, release_date) VALUES
    ('Harakiri', '2012-07-10'),
    ('Black Blooms', '2020-05-15'),
    ('The Rough Dog', '2018-03-22'),
    ('The Rising Tied', '2005-11-22'),
    ('Post Traumatic', '2018-06-15'),
    ('Post Traumatic EP', '2018-01-25'),
    ('Where''d You Go', '2005-05-02'),
    ('Bem Sertanejo', '2015-08-07'),
    ('Bem Sertanejo - O Show (Ao Vivo)', '2016-11-04'),
    ('Bem Sertanejo - (1ª Temporada) - EP', '2015-05-15'),
    ('Use Your Illusion I', '1991-09-17'),
    ('Use Your Illusion II', '1991-09-17'),
    ('Greatest Hits', '2004-03-23');

INSERT INTO album_artists (album_id, artist_id, is_main_singer) VALUES
    (1, 1, true), (2, 1, true), (3, 1, true),
    (4, 2, true), (5, 2, true), (6, 2, true), (7, 2, true),
    (8, 3, true), (9, 3, true), (10, 3, true),
    (11, 4, false), (12, 4, false), (13, 4, false);