import com.leyou.common.JwtUtils;
import com.leyou.common.RsaUtils;
import com.leyou.common.UserInfo;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    private static final String pubKeyPath = "C:\\tmp\\rsa\\rsa.pub";

    private static final String priKeyPath = "C:\\tmp\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

//    @Before
//    public void testGetRsa() throws Exception {
//        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
//        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
//    }

//    @Test
//    public void testGenerateToken() throws Exception {
//        // 生成token
//        String token = JwtUtils.generateToken(new UserInfo(20L, "jack"), privateKey, 5);
//        System.out.println("token = " + token);
//    }
//
//    @Test
//    public void testParseToken() throws Exception {
//        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MjAsInVzZXJuYW1lIjoiamFjayIsImV4cCI6MTU5MjMwNDQxOH0.Rq0JTWPcVOdZQJrlxhxGs0VlnX9EDj7R0LsWlnAwyHrvbT7i_CL7EPmzBICfQyp6wngwXP-PMHl41R3f-6dFlij4ebX6Ifh0KLyzsNt6H7wwzrj-pPuj_tdf-mi3gpVq2hg_uA1ObPrQN5LHu3n_hfMc-2cRLuxxqU4UEZ9Ksdo";
//
//        // 解析token
//        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
//        System.out.println("id: " + user.getId());
//        System.out.println("userName: " + user.getUsername());
//    }
}
