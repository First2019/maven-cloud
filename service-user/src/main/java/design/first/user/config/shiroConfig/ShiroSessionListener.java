//package design.first.user.config.shiroConfig;
//
//import org.apache.shiro.session.Session;
//import org.apache.shiro.session.SessionListener;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * 2、SessionListener，这个监听器在发生session创建、变化、销毁等事件时，可以进行捕捉，这个类主要处理session销毁时，清楚redis中的数据
// */
//public class ShiroSessionListener implements SessionListener {
//    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroSessionListener.class);
//
//    @Override
//    public void onStart(Session session) {
//        // 会话创建时触发
//        LOGGER.info("ShiroSessionListener session {} 被创建", session.getId());
//    }
//
//    @Override
//    public void onStop(Session session) {
//        // 会话被停止时触发
//        ShiroSessionRedisUtil.deleteSession(session);
//        LOGGER.info("ShiroSessionListener session {} 被销毁", session.getId());
//    }
//
//    @Override
//    public void onExpiration(Session session) {
//        //会话过期时触发
//        ShiroSessionRedisUtil.deleteSession(session);
//        LOGGER.info("ShiroSessionListener session {} 过期", session.getId());
//    }
//}