package design.first.user.config.shiroConfig;

import org.apache.shiro.session.mgt.SimpleSession;

import java.io.Serializable;

/**
 * shiro默认的session是SimpleSession，这里我们自定义session，目前不做什么变化，如果有需要，我们就可以扩展自定义Session实现一些特殊功能
 */
public class ShiroSession extends SimpleSession implements Serializable {
}
