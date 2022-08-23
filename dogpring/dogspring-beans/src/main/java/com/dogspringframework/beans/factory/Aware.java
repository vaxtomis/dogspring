package com.dogspringframework.beans.factory;

/**
 * 一个标记超接口，指示 bean 有资格通过回调样式方法由特定框架对象的 Spring 容器通知。
 * 实际的方法签名由各个子接口确定，但通常应仅包含一个接受单个参数的返回 void 的方法。
 */
public interface Aware {
}
