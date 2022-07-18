package com.share.auth.config;

import com.google.common.base.Objects;
import org.springframework.util.AntPathMatcher;

/**
 * Created by wengms.
 * Email: wengms@gillion.com.cn
 * Date: 2018/11/12
 * Time: 11:08 AM
 * Description:
 *
 * @author wengms
 */
public class PatternPathMatcher {
    private AntPathMatcher matcher;
    private String pattern;

    public PatternPathMatcher(String pattern) {
        this.pattern = pattern;
        this.matcher = new AntPathMatcher();
    }

    public boolean match(String path) {
        return this.matcher.match(pattern, path);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PatternPathMatcher that = (PatternPathMatcher) o;
        return Objects.equal(pattern, that.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pattern);
    }
}
