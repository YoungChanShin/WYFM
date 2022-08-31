package com.wyfm.critic.model.oauth;

import lombok.Getter;

@Getter
public class KakaoProfile {

    public Long id;
    public String connectedAt;
    public Properties properties;
    public KakaoAccount kakaoAccount;

    @Getter
    public class Properties {
        public String nickname;
        public String profileImage;
        public String thumbnailImage;
    }

    @Getter
    public class KakaoAccount {
        public Boolean profileNicknameNeedsAgreement;
        public Boolean profileImageNeedsAgreement;
        public Profile profile;
        public Boolean has_email;
        public Boolean emailNeedsAgreement;
        public Boolean isEmailValid;
        public Boolean isEmailVerified;
        public String email;

        @Getter
        public class Profile {
            public String nickname;
            public String thumbnailImageUrl;
            public String profileImageUrl;
            public Boolean isDefaultImage;
        }
    }

}
