package gift.auth.domain;

import gift.entity.enums.SocialType;
import org.springframework.beans.factory.annotation.Value;

public class JWT {

    private Long id;
    private String email;
    private String socialToken = "";
    private SocialType socialType = SocialType.OTHER;
    private Integer exp;

    public JWT(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public JWT(Long id, String email, String socialToken, SocialType socialType, @Value("${jwt.tokenExpTime}") Integer exp) {
        this.id = id;
        this.email = email;
        this.socialToken = socialToken;
        this.socialType = socialType;
        this.exp = exp;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getSocialToken() {
        return socialToken;
    }

    public SocialType getSocialType() {
        return socialType;
    }

    public Integer getExp() {
        return exp;
    }
}
