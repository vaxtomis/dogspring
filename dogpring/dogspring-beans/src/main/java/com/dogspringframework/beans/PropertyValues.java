package com.dogspringframework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * 包含一个或多个 {@link PropertyValue} 对象的持有者，
 * 通常包含针对特定目标 bean 的一次更新。
 *
 * @author vaxtomis
 */
public class PropertyValues {

	private final List<PropertyValue> propertyValueList = new ArrayList<>();

	public void addPropertyValue(PropertyValue pv) {
		this.propertyValueList.add(pv);
	}

	public PropertyValue[] getPropertyValues() {
		return this.propertyValueList.toArray(new PropertyValue[0]);
	}

	public PropertyValue getPropertyValue(String propertyName) {
		for (PropertyValue pv : this.propertyValueList) {
			if (pv.getName().equals(propertyName)) {
				return pv;
			}
		}
		return null;
	}

}
