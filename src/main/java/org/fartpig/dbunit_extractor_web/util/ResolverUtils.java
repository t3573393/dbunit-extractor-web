package org.fartpig.dbunit_extractor_web.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class ResolverUtils {

	public static Pageable createPageable(int pageIndex, int pageSize, Sort sort) {
		Pageable pageSpecification = new PageRequest(pageIndex, pageSize, sort);
		return pageSpecification;
	}

	public static Pageable createPageableForOffset(int offset, int pageSize, Sort sort) {
		Pageable pageSpecification = new PageRequest(
				offset % pageSize == 0 ? offset / pageSize : offset / pageSize + 1, pageSize, sort);
		return pageSpecification;
	}

	public static Sort createDBConfigSort() {
		Sort tablesOrder = new Sort(Direction.ASC, "name");
		return tablesOrder;
	}

	public static Sort createDataSetSort() {
		Sort tablesOrder = new Sort(Direction.ASC, "tableName");
		return tablesOrder;
	}

}
