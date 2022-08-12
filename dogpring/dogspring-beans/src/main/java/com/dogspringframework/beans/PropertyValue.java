package com.dogspringframework.beans;

/**
 * 保存单个 bean 属性的信息和值的对象。
 * 在此处使用对象，而不是仅将所有属性存储在以属性名称为键的映射中，
 * 可以提供更大的灵活性，并能够以优化的方式处理索引属性等。
 *
 * @author vaxtomis
 */
public class PropertyValue {
	private final String name;

	private final Object value;

	public PropertyValue(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}
}
