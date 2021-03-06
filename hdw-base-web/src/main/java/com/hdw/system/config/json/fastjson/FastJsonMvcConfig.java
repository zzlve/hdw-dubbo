package com.hdw.system.config.json.fastjson;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author TuMingLong
 * @date 2018-11-08
 */
public class FastJsonMvcConfig implements WebMvcConfigurer {

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";


    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();

        List<MediaType> mediaTypes = new ArrayList<>();
        mediaTypes.add(MediaType.APPLICATION_JSON);
        mediaTypes.add(MediaType.TEXT_HTML);
        mediaTypes.add(new MediaType("application", "xml"));
        mediaTypes.add(new MediaType("text", "xml"));
        mediaTypes.add(new MediaType("application", "*+xml"));
        mediaTypes.add(MediaType.ALL);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);

        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setCharset(Charset.forName(DEFAULT_CHARSET));
        fastJsonConfig.setDateFormat(DEFAULT_DATE_FORMAT);
        SerializerFeature[] serializerFeatures = new SerializerFeature[]{
            SerializerFeature.PrettyFormat,                 // 格式化
            SerializerFeature.WriteMapNullValue,            // 输出空值
            SerializerFeature.WriteNullListAsEmpty,         // 空集合输出[]
            SerializerFeature.WriteDateUseDateFormat        // 日期格式化
        };
        fastJsonConfig.setSerializerFeatures(serializerFeatures);


        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(Long.class, ToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        fastJsonConfig.setSerializeConfig(serializeConfig);

        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);

        converters.add(0,fastJsonHttpMessageConverter);

    }
}
