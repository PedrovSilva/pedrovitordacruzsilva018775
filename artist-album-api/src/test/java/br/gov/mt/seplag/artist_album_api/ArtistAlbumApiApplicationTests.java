package br.gov.mt.seplag.artist_album_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.NONE
)
@ActiveProfiles("test")
class ArtistAlbumApiApplicationTests {

	@Test
	void contextLoads() {
	}
}
