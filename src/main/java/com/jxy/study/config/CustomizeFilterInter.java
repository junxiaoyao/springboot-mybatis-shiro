package com.jxy.study.config;

import java.util.Map;
import javax.servlet.Filter;

public interface CustomizeFilterInter {

    Map<String, Filter> getCustomizeFilters();
}
