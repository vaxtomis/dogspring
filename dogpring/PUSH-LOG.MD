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
4. 定义 BeanDefinitionReader，指定加载方法，对应的抽象基类 AbstractBeanDefinitionReader (构造时内部持有 BeanDefinitionRegistry 和 ResourceLoader 的引用)，
最后由 XmlBeanDefinitionReader 继承基类，并实现了具体的注册逻辑，由此将 ResourceLoader 的信息注册进 BeanDefinitionRegistry 中  
5. 完善了 ConfigurableBeanFactory, HierarchicalBeanFactory 等接口继承关系，后续明细  
## 五、增加 BeanFactoryPostProcessor 和 BeanPostProcessor
Bean 创建的过程分别为：  
加载 -> 注册 -> 修改(BD)[BeanFactoryPostProcessor] -> 实例化 -> 扩展(Bean实例)[BeanPostProcessor]  
1. 因此首先我们要定义接口 BeanFactoryPostProcessor 和 BeanPostProcessor，BeanFactoryPostProcessor 是对 BD 的修改，而 BeanPostProcessor 是在实例化并填充属性后，
再依次执行 BeanPostProcessor 前置处理、Bean 的 init 方法、BeanPostProcessor 后置处理方法  
2. 为了让新加入的功能便于调用，需要进一步的封装，继 ListableBeanFactory 接口后，进一步创建 ApplicationContext 接口，
其子接口 ConfigurableApplicationContext 新增 refresh 方法，方法内部用于管理 BeanFactory 的创建 (BD 的加载) 和获取、调用 BeanFactoryPostProcessor 的方法、注册 BeanPostProcessor、预实例化 Bean 等逻辑，
AbstractApplicationContext 是抽象基类，ClassPathXmlApplicationContext 是使用 Xml 加载 BD 资源的一种具体实现  
3. BeanFactoryPostProcessor 由于是对 BD 的修改，因此实现在 refresh 的编排中，BeanFactoryPostProcessor 在 Xml 文件中被配置，
因此存放在 DefaultListableBeanFactory 的 Map 缓存中，在 refresh 中检索 BeanFactoryPostProcessor，并执行对应的方法实现修改 BD  
4. BeanPostProcessor 是实例化后的修改。一切调用的前提是注册 BeanPostProcessor，
因此在 ConfigurableBeanFactory 接口中增加了注册方法，在 AbstractBeanFactory 中增加 List 用于缓存。之后此方法在更高层的封装 ApplicationContext 的实现中被调用。  
实例化部分我们目前是通过 AbstractAutowireCapableBeanFactory 实现的，因此我们在继承 BeanFactory 接口的基础上创建 AutowireCapableBeanFactory，
声明 BeanPostProcessor 前置和后置处理方法，并在 AbstractAutowireCapableBeanFactory 实现，
在实例化、属性填充的逻辑后，增加对 Bean 的初始化处理。初始化处理包括三部分：前置处理、bean 的 init方法和后置处理  
## 六、增加 DisposableBean 和 InitializingBean
1. 定义标识性接口 DisposableBean 和 InitializingBean，一是指定了对应的 destroy 和 afterPropertiesSet 方法，
二是容器也可以通过检查 Bean 是否实现这两个接口来获取信息  
2. 在 XmlBeanDefenitionReader 中加入逻辑，使得我们在 Xml 中配置的 init-method 和 destroy-method 可以被获取到，
获取到两个方法名后，需要配置入 BeanDefinition 中，因此 BD 内部也需要增添信息  
3. init 方面，BeanDefinition 完成了信息获取之后，实现 AbstractAutowireCapableBeanFactory 中预留的 invokeInitMethods 方法，
方法内部首先检测是否实现了 InitializingBean 接口，如果有则直接调用 afterPropertiesSet 方法，再尝试从 BD 中获取
initMethodName，如果能获取成功，通过反射获取 Method 并调用  
4. destroy 方面，首先在 ConfigurableBeanFactory 定义单例 Bean 销毁方法 destroySingletons，
并于 DefaultSingletonBeanRegistry 中实现  
这里非常奇怪，因为 DefaultSingletonBeanRegistry 并没有实现 ConfigurableBeanFactory 接口，实现此接口的为它的子类，
通过这种设计方式来隔离分层服务  
DefaultSingletonBeanRegistry 内部新增 disposableBeans 用于缓存 DisposableBean，
在 AbstractAutowireCapableBeanFactory 的 init 部分执行完毕后，注册 DisposableBean，
其内部方法会检查 bean 对象是否实现 DisposableBean 接口，或是 BD 中有 destroy-method 的信息，并创建
DisposableBeanAdapter 封装信息，DisposableBeanAdapter 内部实现了 destroy 方法，会根据两种情况选择不同的函数：  
如果是实现 DisposableBean 并重写 destroy 则调用 destroy  
如果自定义 destroy 方法并在 Xml 中配置，则调用自定义方法  
5. ConfigurableApplicationContext 接口，定义了 registerShutdownHook 和 close 方法，
并在最外层的 AbstractApplicationContext 中实现，如测试代码所示，通过 ApplicationContext 注册钩子 close，
实际就是 调用 DefaultSingletonBeanRegistry 内部实现的 destroySingletons 方法，批量获取注册在 disposableBeans 的对象，
执行其 destroy 方法  
## 七、新增标记类型 Aware 接口，用于感知容器对象
1. 首先创建顶级接口 Aware，以及继承此接口衍生 BeanClassLoaderAware、BeanFactoryAware、BeanNameAware、ApplicationContext 等，
我们的需求是，当 Bean 实现对应接口时，就可以获取到对应的引用，例如实现 BeanFactoryAware 时，Bean 实例就可以
通过 getBeanFactory() 获取到其容器的引用。Bean 实例会对容器和一些服务有需求或者依赖，通过这种方式向 Bean 提供服务  
2. 为了实现上述功能，对于 beanName、BeanFactory 和 BeanClassLoader，可以直接在 initializeBean() 方法内进行赋值。
3. 而对于 ApplicationContext，因为我们无法在 AbstractAutowireCapableBeanFactory 内部获取到 ApplicationContext 的引用，
我们要借助 BeanPostProcessor，也就是在 BeanPostProcessor 创建时，将 ApplicationContext 的引用存放进入，
在 Bean 实例化过程中，会通过 BeanPostProcessor 再将内部的 ApplicationContext 引用赋给对象  
3. 为了获取到 ApplicationContext 的引用，我们需要在 AbstractApplicationContext 的 refresh 流程中，
通过注册 ApplicationContextAwareProcessor 保存 ApplicationContext 的引用。这样引用关系就保存在 BeanPostProcessor 中了，
在实例化的过程中，就会调用 ApplicationContextAwareProcessor 实现的 postProcessBeforeInitialization 方法，
注入 ApplicationContext 的引用  