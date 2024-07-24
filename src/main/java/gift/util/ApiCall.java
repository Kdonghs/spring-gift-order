package gift.util;

import static gift.util.url.KakaoUrl.redirectUrl;
import static org.springframework.http.HttpMethod.GET;

import gift.auth.domain.KakaoToken.kakaoInfo;
import gift.auth.domain.KakaoToken.kakaoToken;
import gift.util.url.KakaoUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ApiCall {

    private HttpHeaders headers;
    //    private final RestTemplate restTemplate = new RestTemplate();
    private final RestCall restCall;
    private final String clientId;
    private final String commonUrl = "http://localhost:8080";

    @ConfigurationProperties("kakao")
    public record kakaoProperties(String clientId) {

    }

    @Autowired
    public ApiCall(kakaoProperties properties, RestCall restCall) {
        this.clientId = properties.clientId();
        this.restCall = restCall;
    }

    public kakaoToken getKakaoToken(String code) {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Long> requestEntity = new HttpEntity(null, headers);

        String url = KakaoUrl.getToken + "?grant_type=authorization_code"
            + "&client_id=" + clientId
            + "&redirect_uri=" + commonUrl + redirectUrl
            + "&code=" + code;

        ResponseEntity<kakaoToken> responseEntity = restCall.apiCall(url, GET, requestEntity,
            kakaoToken.class);
        return responseEntity.getBody();
    }

    public kakaoInfo getKakaoTokenInfo(String token) {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(token);

        HttpEntity<Long> requestEntity = new HttpEntity(null, headers);

        String url = KakaoUrl.getInfo;

        ResponseEntity<kakaoInfo> responseEntity = restCall.apiCall(url, GET, requestEntity,
            kakaoInfo.class);
        return responseEntity.getBody();
    }
}
