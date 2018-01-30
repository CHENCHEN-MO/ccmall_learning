package com.ccmall.util;

import com.ccmall.pojo.Test;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //对象所有字段全部列入
        objectMapper.setSerializationInclusion(Inclusion.NON_EMPTY);

        //取消默认转换timestamps形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);

        //忽略空bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,true);

        //所有的日期格式都统一成一下的样式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        //忽略在json字符中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,true);

    }

    public static <T> String obj2String(T obj){
        if (obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse object to String error",e);
            return null;
        }
    }

    public static <T> String obj2StringPretty(T obj){
        if (obj == null){
            return null;
        }
        try {
            return obj instanceof String?(String)obj:objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse object to String error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str,Class<T> clazz){
        if (StringUtils.isEmpty(str) || clazz == null){
            return null;
        }
        try {
            return clazz.equals(String.class)?(T) str:objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            log.warn("Parse object to String error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str,TypeReference<T> typeReference){
        if (StringUtils.isEmpty(str) || typeReference == null){
            return null;
        }
        try {
            return typeReference.getType().equals(String.class)?(T)str: (T) objectMapper.readValue(str, typeReference);
        } catch (IOException e) {
            log.warn("Parse object to String error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str,Class<?> collectionClass,Class<?>... elementClasses){
        if (StringUtils.isEmpty(str) ){
            return null;
        }
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (IOException e) {
            log.warn("Parse object to String error",e);
            return null;
        }
    }



    public static void main(String[] args) {
        Test user = new Test();
        user.setName("chenchen");
        user.setYear("19");
        Test user1 = new Test();
        user1.setName("hahha");
        user1.setYear("12");
        List<HashMap<String ,Test>> list = new ArrayList<>();
        HashMap<String ,Test> hashMap = new HashMap<String ,Test>();
        hashMap.put("1",user);
        hashMap.put("2",user1);
        list.add(hashMap);

//        user.setId(1);
//        user.setPassword("chenchen");
//        Date date = new Date();
//        user.setUpdateTime(new Date());
        String relsut = obj2StringPretty(list);
        List<HashMap<String ,Test>>  test2 = string2Obj(relsut, new TypeReference<List<HashMap<String, Test>>>() {
        });
        log.info(relsut);
//        log.warn(test2.toString());
    }

}
