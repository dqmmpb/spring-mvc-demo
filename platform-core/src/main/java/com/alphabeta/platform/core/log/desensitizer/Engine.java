package com.alphabeta.platform.core.log.desensitizer;

import com.alphabeta.platform.core.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Engine {

    private static Logger logger = LoggerFactory.getLogger(Engine.class);

    /**
     * 要脱敏的字段
     */
    private static final String FIELD = "#FIELD#";
    /**
     * 要脱敏的内容片断
     */
    private static final String HIDE = "HIDE";
    /**
     * 替换的文本
     */
    private static final String REPLACE = "#REPLACE#";

    /**
     * 把编译好的正则表达式放到缓存里面
     */
    private static Map<String, Pattern> patterns = new HashMap<>();

    /**
     * 脱敏日志信息
     *
     * @param input
     * @param desRules
     * @return
     */
    public static String desensitize(String input, List<DesensitizeModel> desRules, boolean clearMemory) {

        try {

            // 计算脱敏所耗时间 - 开始时间
            long startTime = System.nanoTime();

            if (clearMemory) {
                logger.debug("---清空已编译正则表达式缓存，重新编译---");
                patterns = new HashMap<>();
            }

            if (desRules == null) {
                return input;
            }

            for (DesensitizeModel model : desRules) {

                String fieldName = model.getFieldName();
                String replacement = model.getReplacement();

                Pattern pattern = null;

                if (patterns.containsKey(fieldName)) {
                    pattern = patterns.get(fieldName);
                } else {
                    String regex = model.getExpression().replace(FIELD, fieldName);
                    pattern = Pattern.compile(regex);
                    patterns.put(fieldName, pattern);
                }

                Matcher matcher = pattern.matcher(input);
                input = replaceAll(matcher, input, replacement);
            }

            // 计算脱敏所耗时间 - 结束时间
            long endTime = System.nanoTime();

            logger.info("desensitize cost time:" + (endTime - startTime) / 1000 + "μs");

        } catch (Exception exception) {
            ExceptionUtil.logError(logger, "Engine.desensitize", exception);
        }

        return input;
    }

    /**
     * 替换文本
     *
     * @param matcher
     * @param source
     * @param replacement
     * @return
     */
    public static String replaceAll(Matcher matcher, String source, String replacement) {
        matcher.reset();
        boolean result = matcher.find();
        if (result) {
            StringBuffer sb = new StringBuffer();
            do {

                // 获取需要脱敏的内容片断，计算出长度，用相等长度的*代替
                String text = getReplacement(matcher.group(HIDE));
                // 替换文本
                String temp = replacement.replace(REPLACE, text);
                matcher.appendReplacement(sb, temp);
                result = matcher.find();
            } while (result);
            matcher.appendTail(sb);
            return sb.toString();
        }
        return source;
    }

    /**
     * 获取需要脱敏的内容片断，计算出长度，用相等长度的*代替
     *
     * @param hideText
     * @return
     */
    private static String getReplacement(String hideText) {
        int length = hideText.length();
        String text = "";
        int index = 1;
        while (index <= length) {
            text = text + "*";
            index = index + 1;
        }

        return text;
    }
}
