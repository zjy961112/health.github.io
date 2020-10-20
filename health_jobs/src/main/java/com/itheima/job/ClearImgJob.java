package com.itheima.job;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiniuUtils;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

/**
 * @author 柠檬茶灬薄荷味丿
 * @date 2020/10/12 16:17
 * @see
 */
public class ClearImgJob {

    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        if (sdiff != null) {
            for (String fileName : sdiff) {
                QiniuUtils.deleteFileFromQiniu(fileName);
            }
        }
    }
}
