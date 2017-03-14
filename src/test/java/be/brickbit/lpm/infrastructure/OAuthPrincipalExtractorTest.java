package be.brickbit.lpm.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import be.brickbit.lpm.catering.config.LpmUserAuthenticationConverter;
import be.brickbit.lpm.catering.config.OAuthPrincipalExtractor;

import static be.brickbit.lpm.catering.util.RandomValueUtil.randomLong;
import static be.brickbit.lpm.catering.util.RandomValueUtil.randomString;
import static org.assertj.core.api.Assertions.assertThat;

public class OAuthPrincipalExtractorTest {

    private OAuthPrincipalExtractor extractor;

    @Before
    public void setUp() throws Exception {
        extractor = new OAuthPrincipalExtractor();
    }

    @Test
    public void extractsPrincipalId() throws Exception {
        Map<String, Object> principalDetails = new HashMap<>();
        final Long id = randomLong();
        principalDetails.put("id", id);

        LpmUserAuthenticationConverter.LpmTokenPrincipal dto = extractor.extractPrincipal(principalDetails);

        assertThat(dto.getId()).isEqualTo(id);
    }

    @Test
    public void extractsPrincipalUsername() throws Exception {
        Map<String, Object> principalDetails = new HashMap<>();
        final String username = randomString();
        principalDetails.put("username", username);

        LpmUserAuthenticationConverter.LpmTokenPrincipal dto = extractor.extractPrincipal(principalDetails);

        assertThat(dto.getUsername()).isEqualTo(username);
    }
}