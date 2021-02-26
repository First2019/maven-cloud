package design.first.commons.tools.IP;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("ExcludesFilter")
public interface ExcludesFilteMixIn {
	// 用来标记排除的接口
}
