package com.share.auth.domain.converter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 将入参日期字符串("-"为分隔符),转化为java.util.Date对象
 *
 * @author xuzt <xuzt@gillion.com.cn>
 * @date 2022/7/27
 */
public class String2DateDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String dateString = jsonParser.getText();
        if (StrUtil.isNotBlank(dateString)) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            try {
                return format.parse(dateString);
            } catch (Exception e) {
                throw new RuntimeException("日期格式化错误");
            }
        }
        return null;
    }
}
