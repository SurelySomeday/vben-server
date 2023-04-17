package top.yxlgx.wink.config.orm.p6spy;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.apache.commons.lang3.StringUtils;

public class P6SpyLogger implements MessageFormattingStrategy {

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category,
                                String prepared, String sql, String url) {
        return StringUtils.isNotBlank(sql) ? " Consume Time：" + elapsed + " ms " + now +
                "\n Execute SQL：" + sql.replaceAll("[\\s]+", " ") + "\n" : "";
    }
}
