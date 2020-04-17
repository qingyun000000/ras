package com.zy.zyrasc.gateway;

import com.zy.zyrasc.status.ClientStatus;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 网关通用请求处理
 * @author wuhailong
 */
@Component
public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig fc) throws ServletException {}

    @Override
    public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) srequest;
        HttpServletResponse rep = (HttpServletResponse) sresponse;
        if("gateway".equals(ClientStatus.getType())){
            //网关
            
            //设置允许跨域的配置
            // 这里填写你允许进行跨域的主机ip（正式上线时可以动态配置具体允许的域名和IP）
            rep.setHeader("Access-Control-Allow-Origin", "*");
            // 允许的访问方法
            rep.setHeader("Access-Control-Allow-Methods", "*");   //POST, GET, PUT, OPTIONS, DELETE, PATCH
            // Access-Control-Max-Age 用于 CORS 相关配置的缓存
            rep.setHeader("Access-Control-Max-Age", "3600");
            rep.setHeader("Access-Control-Allow-Headers", "token, Origin, X-Requested-With, Content-Type, Accept");
            
            //转发
            request.getRequestDispatcher("/gateway").forward(srequest, sresponse);
        }
    }

    @Override
    public void destroy() {}
    
    
}
