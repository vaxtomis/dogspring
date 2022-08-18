# Push Log
## 一、初始化
## 二、带实例化策略的简化 BeanFactory 结构
1. BeanDefinitionRegistry BD注册接口
2. 实现 DefaultSingletonBeanRegistry 引入单例注册功能
3. AbstractBeanFactory 继承 DefaultSingletonBeanRegistry 增加并实现通过 BD 获取 getBean 方法
4. AbstractAutowireCapableBeanFactory 继承 AbstractBeanFactory，引入实例化策略，增加并实现 creatBean 方法
5. DefaultListableBeanFactory 继承 AbstractAutowireCapableBeanFactory，增加 Map<String, BeanDefinition> 缓存
6. 实现基础 BeansException
## 三、完善 AbstractAutowireCapableBeanFactory
1. 引入 PropertyValue, PropertyValues
2. 引入 BeanReference (属性为基础类型时可以直接进行填充，而属性为引用类型时，此类可以通过 BeanName 逻辑引用去获取引用对象的信息)
3. 在 BeanDefinition 中加入 PropertyValues，PropertyValues 本质是 List<PropertyValue> 用于记录实例化后对象需要填充的属性
4. 在 AbstractAutowireCapableBeanFactory 中实现 applyPropertyValues 方法，在实例化 Bean 对象后会根据 PropertyValues 填充对象的属性
## 四、增加 Resource 解析和配置注册
1. 获取资源的前提是定义资源，通过 Resource 接口定义抽象功能，通过三个实现类 UrlResource, FileSystemResource, ClassPathResource 定义三种资源获取和打开的方式
2. 有了资源之后，定义加载资源的抽象功能接口 ResourceLoader，实现此接口的默认实现为 DefaultResourceLoader
3. 由此资源的获取问题简要处理完毕，接下来考虑资源的注册
4. 定义 BeanDefinitionReader，指定加载方法，对应的抽象基类 AbstractBeanDefinitionReader (构造时内部持有 BeanDefinitionRegistry 和 ResourceLoader 的引用)，最后由 XmlBeanDefinitionReader 继承基类，并实现了具体的注册逻辑，由此将 ResourceLoader 的信息注册进 BeanDefinitionRegistry 中
5. 完善了 ConfigurableBeanFactory, HierarchicalBeanFactory 等接口继承关系，后续明细
## 五、增加 BeanFactoryPostProcessor 和 BeanPostProcessor
Bean 创建的过程分别为：
加载 -> 注册 -> 修改(BD)[BeanFactoryPostProcessor] -> 实例化 -> 扩展(Bean实例)[BeanPostProcessor]
1. 因此首先我们要定义接口 BeanFactoryPostProcessor 和 BeanPostProcessor，BeanFactoryPostProcessor 是对 BD 的修改，而 BeanPostProcessor 是在实例化并填充属性后，再依次执行 BeanPostProcessor 前置处理、Bean 的 init 方法、BeanPostProcessor 后置处理方法
2. 为了让新加入的功能便于调用，需要进一步的封装，继 ListableBeanFactory 接口后，进一步创建 ApplicationContext 接口，其子接口 ConfigurableApplicationContext 新增 refresh 方法，方法内部用于管理 BeanFactory 的创建 (BD 的加载) 和获取、调用 BeanFactoryPostProcessor 的方法、注册 BeanPostProcessor、预实例化 Bean 等逻辑，AbstractApplicationContext 是抽象基类，ClassPathXmlApplicationContext 是使用 Xml 加载 BD 资源的一种具体实现
3. BeanFactoryPostProcessor 由于是对 BD 的修改，因此实现在 refresh 的编排中，BeanFactoryPostProcessor 在 Xml 文件中被配置，因此存放在 DefaultListableBeanFactory 的 Map 缓存中，在 refresh 中检索 BeanFactoryPostProcessor，并执行对应的方法实现修改 BD
4. BeanPostProcessor 是实例化后的修改。一切调用的前提是注册 BeanPostProcessor，因此在 ConfigurableBeanFactory 接口中增加了注册方法，在 AbstractBeanFactory 中增加 List 用于缓存。之后此方法在更高层的封装 ApplicationContext 的实现中被调用。
实例化部分我们目前是通过 AbstractAutowireCapableBeanFactory 实现的，因此我们在继承 BeanFactory 接口的基础上创建 AutowireCapableBeanFactory，声明 BeanPostProcessor 前置和后置处理方法，并在 AbstractAutowireCapableBeanFactory 实现，在实例化、属性填充的逻辑后，增加对 Bean 的初始化处理。初始化处理包括三部分：前置处理、bean 的 init方法和后置处理