//package cn.mirror6.rbac.server.util;
//
//import cn.mirror6.rbac.constant.security.SecurityConstant;
//import cn.mirror6.rbac.server.security.LoginUser;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//
//import javax.crypto.SecretKey;
//import javax.xml.bind.DatatypeConverter;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author ：gong sun
// * @description: token工具类
// * @date ：Created in 2021/5/17 2:34 下午
// */
//public class JwtTokenUtil {
//
//    /**
//     * 生成足够的安全随机密钥，以适合符合规范的签名
//     */
//    private static final byte[] API_KEY_SECRET_BYTES = DatatypeConverter.parseBase64Binary(SecurityConstant.JWT_SECRET_KEY);
//    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(API_KEY_SECRET_BYTES);
//
//    public static String createToken(LoginUser user, Boolean isRememberMe) {
//        long expiration = isRememberMe ? SecurityConstant.EXPIRATION_REMEMBER : SecurityConstant.EXPIRATION;
//        final Date createdDate = new Date();
//        final Date expirationDate = new Date(createdDate.getTime() + expiration * 1000);
//        String tokenPrefix = Jwts.builder()
//                .setHeaderParam("type", SecurityConstant.TOKEN_TYPE)
//                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
//                .claim(SecurityConstant.ROLE_CLAIMS, String.join(",", user.getAuthList()))
//                .setId(user.getUser().getId().toString())
//                .setIssuer("mirror6")
//                .setIssuedAt(createdDate)
//                .setSubject(user.getUsername())
//                .setExpiration(expirationDate)
//                .compact();
//        // 添加 token 前缀 "Bearer ";
//        return SecurityConstant.TOKEN_PREFIX + tokenPrefix;
//    }
//
//    public static String getId(String token) {
//        Claims claims = getClaims(token);
//        return claims.getId();
//    }
//
//    public static UsernamePasswordAuthenticationToken getAuthentication(String token) {
//        Claims claims = getClaims(token);
//        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
//        String userName = claims.getSubject();
//        return new UsernamePasswordAuthenticationToken(userName, token, authorities);
//    }
//
//    private static List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
//        String role = (String) claims.get(SecurityConstant.ROLE_CLAIMS);
//        return Arrays.stream(role.split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//    }
//
//    private static Claims getClaims(String token) {
//        return Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//}
