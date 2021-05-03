package com.chryl.redis.bitmap;

import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * bitMap:用bit位来标记对应的value,优点:大大的节省空间
 * 如果存储20个int:200000000/4/1024/1024/1024=7.45G
 * 如果按位存储:200000000/8/1024/1024.1024=0.233G
 * <p>
 * 应用:用户签到功能,比如用户uid=10294的用户在20201020签到了,可以执行setBit 20201020 10294 1 ,
 * 1代表已签到,上年的语句代表20201020用户10294已经签到.
 */
@Component
public class BitMapService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 赋值第几位
     * key=日期,offset=id,value=是否签到
     *
     * @param key    key
     * @param offset 第几位
     * @param value  值
     */
    public void bitSet(String key, long offset, boolean value) {
        stringRedisTemplate.opsForValue().setBit(key, offset, value);
    }

    /**
     * 获取第几位的value
     *
     * @param key    key
     * @param offset 第几位
     * @return
     */
    public Boolean bitGet(String key, long offset) {
        return stringRedisTemplate.opsForValue().getBit(key, offset);
    }

    /**
     * 有多少个1
     * 下标从0开始  -1表示到结束
     *
     * @param key   key
     * @param start 开始下标
     * @param end   结束下标
     * @return
     */
//    public long bitCount(String key, Integer start, Integer end) {
//        if (start == null || end == null) {
    public long bitCount(String key, long start, long end) {
        if (start == 0 || end == 0) {
            return stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes(), 0, -1));
        }
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitCount(key.getBytes(), start, end));
    }

    /**
     * 并且
     * 将key1和key2的值进行且操作，然后将值赋值给result这个key
     *
     * @param result
     * @param key1
     * @param key2
     * @return
     */
    public long bitOpAnd(String result, String key1, String key2) {
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(RedisStringCommands.BitOperation.AND, result.getBytes(), key1.getBytes(), key2.getBytes()));
    }

    /**
     * 或
     * 将key1和key2的值进行或操作，然后将值赋值给result这个key
     *
     * @param result
     * @param key1
     * @param key2
     * @return
     */
    public long bitOpOr(String result, String key1, String key2) {
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(RedisStringCommands.BitOperation.OR, result.getBytes(), key1.getBytes(), key2.getBytes()));
    }

    /**
     * 异或
     * 将key1和key2的值进行异或操作，然后将值赋值给result这个key
     * 相同则0 不同则1
     *
     * @param result
     * @param key1
     * @param key2
     * @return
     */
    public long bitOpXor(String result, String key1, String key2) {
        return stringRedisTemplate.execute((RedisCallback<Long>) con -> con.bitOp(RedisStringCommands.BitOperation.XOR, result.getBytes(), key1.getBytes(), key2.getBytes()));
    }
}
