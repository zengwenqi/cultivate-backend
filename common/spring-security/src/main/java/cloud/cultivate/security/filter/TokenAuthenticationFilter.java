//package cloud.cultivate.security.filter;
//
//import cloud.cultivate.jwt.JwtHelper;
//import cloud.cultivate.result.Result;
//import cloud.cultivate.result.ResultCodeEnum;
//import cloud.cultivate.security.custom.LoginUserInfoHelper;
//import cloud.cultivate.utils.ResponseUtil;
//import com.alibaba.fastjson2.JSON;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * <p>
// * 认证解析token过滤器
// * </p>
// */
//public class TokenAuthenticationFilter extends OncePerRequestFilter {
//
//    private RedisTemplate redisTemplate;
//
//    public TokenAuthenticationFilter(RedisTemplate redisTemplate) {
//        this.redisTemplate = redisTemplate;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        logger.info("uri:"+request.getRequestURI());
//        //如果是登录接口，直接放行
//        if("/admin/system/index/login".equals(request.getRequestURI())) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
//        if(null != authentication) {
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            chain.doFilter(request, response);
//        } else {
//            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.PERMISSION));
//        }
//    }
//
//    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
//        // token置于header里
//        String token = request.getHeader("token");
//        logger.info("token:"+token);
//        if (!StringUtils.isEmpty(token)) {
//            String username = JwtHelper.getUsername(token);
//            logger.info("username:"+username);
//            if (!StringUtils.isEmpty(username)) {
//                //通过ThreadLocal记录当前登录人信息
//                LoginUserInfoHelper.setUserId(JwtHelper.getUserId(token));
//                LoginUserInfoHelper.setUsername(username);
//                String authoritiesString = (String) redisTemplate.opsForValue().get(username);
//                List<Map> mapList = JSON.parseArray(authoritiesString, Map.class);
//                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//                for (Map map : mapList) {
//                    authorities.add(new SimpleGrantedAuthority((String)map.get("authority")));
//                }
//                return new UsernamePasswordAuthenticationToken(username, null, authorities);
//            } else {
//                return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
//            }
//        }
//        return null;
//    }
//}