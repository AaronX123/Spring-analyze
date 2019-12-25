/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory.config;

import java.util.Iterator;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.lang.Nullable;

/**
 * 生词
 * In addition to 除了
 * analogous adj.相似的
 * similar adj.相似的
 * eligible adj.合格的，有资格的。
 *
 *
 *
 * Configuration interface to be implemented by most listable bean factories.
 * In addition to {@link ConfigurableBeanFactory}, it provides facilities to
 * analyze and modify bean definitions, and to pre-instantiate singletons.
 *
 * 被绝大多数可列举的容器所实现。除了ConfigurableBeanFactory之外，提供了分析和修改
 * BeanDefinition的能力，并且能够提前初始化单例。
 * <p>This subinterface of {@link org.springframework.beans.factory.BeanFactory}
 * is not meant to be used in normal application code: Stick to
 * {@link org.springframework.beans.factory.BeanFactory} or
 * {@link org.springframework.beans.factory.ListableBeanFactory} for typical
 * use cases. This interface is just meant to allow for framework-internal
 * plug'n'play even when needing access to bean factory configuration methods.
 *
 * 这个子接口不应该在普通的应用程序代码中使用：对于典型的用例，请使用
 * {@link org.springframework.beans.factory.BeanFactory}
 * 或{@link org.springframework.beans.factory.ListableBeanFactory}。这个接口只是为了允许框架内部即插即用，
 * 即使在需要访问bean工厂配置方法时也是如此。
 * @author Juergen Hoeller
 * @since 03.11.2003
 * @see org.springframework.context.support.AbstractApplicationContext#getBeanFactory()
 */
public interface ConfigurableListableBeanFactory
		extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

	/**
	 * Ignore the given dependency type for autowiring:
	 * for example, String. Default is none.
	 * @param type the dependency type to ignore
	 *
	 * 忽略给定的自动装配依赖，例如String类型，默认为none。
	 */
	void ignoreDependencyType(Class<?> type);

	/**
	 * Ignore the given dependency interface for autowiring.
	 * <p>This will typically be used by application contexts to register
	 * dependencies that are resolved in other ways, like BeanFactory through
	 * BeanFactoryAware or ApplicationContext through ApplicationContextAware.
	 * <p>By default, only the BeanFactoryAware interface is ignored.
	 * For further types to ignore, invoke this method for each type.
	 * @param ifc the dependency interface to ignore
	 * @see org.springframework.beans.factory.BeanFactoryAware
	 * @see org.springframework.context.ApplicationContextAware
	 *
	 * 自动装配时忽略给定的依赖接口。
	 * 通常会被应用上下文当通过其它方式注册依赖时例如BeanFactory通过BeanFactoryAware,
	 * ApplicationContext通过ApplictaionContextAware来解析时调用
	 */
	void ignoreDependencyInterface(Class<?> ifc);

	/**
	 * Register a special dependency type with corresponding autowired value.
	 * <p>This is intended for factory/context references that are supposed
	 * to be autowirable but are not defined as beans in the factory:
	 * e.g. a dependency of type ApplicationContext resolved to the
	 * ApplicationContext instance that the bean is living in.
	 * <p>Note: There are no such default types registered in a plain BeanFactory,
	 * not even for the BeanFactory interface itself.
	 * @param dependencyType the dependency type to register. This will typically
	 * be a base interface such as BeanFactory, with extensions of it resolved
	 * as well if declared as an autowiring dependency (e.g. ListableBeanFactory),
	 * as long as the given value actually implements the extended interface.
	 * @param autowiredValue the corresponding autowired value. This may also be an
	 * implementation of the {@link org.springframework.beans.factory.ObjectFactory}
	 * interface, which allows for lazy resolution of the actual target value.
	 *
	 * 为指定的自动装配值设置一个特殊的依赖类型。
	 * 这适用于工厂/上下文引用，这些引用本应是可自动装配的，但在工厂中未定义为bean：
	 * 例如，ApplicationContext类型的依赖项解析为bean所在的ApplicationContext实例。
	 * 在纯BeanFactory中没有注册这样的默认类型，甚至BeanFactory接口本身也没有。
	 */
	void registerResolvableDependency(Class<?> dependencyType, @Nullable Object autowiredValue);

	/**
	 * Determine whether the specified bean qualifies as an autowire candidate,
	 * to be injected into other beans which declare a dependency of matching type.
	 * <p>This method checks ancestor factories as well.
	 * @param beanName the name of the bean to check
	 * @param descriptor the descriptor of the dependency to resolve
	 * @return whether the bean should be considered as autowire candidate
	 * @throws NoSuchBeanDefinitionException if there is no bean with the given name
	 *
	 * 确定指定的bean是否符合autowire候选条件，以便注入到声明匹配类型依赖项的其他bean中。
	 * 这个方法也检查祖先工厂。
	 */
	boolean isAutowireCandidate(String beanName, DependencyDescriptor descriptor)
			throws NoSuchBeanDefinitionException;

	/**
	 * Return the registered BeanDefinition for the specified bean, allowing access
	 * to its property values and constructor argument value (which can be
	 * modified during bean factory post-processing).
	 * <p>A returned BeanDefinition object should not be a copy but the original
	 * definition object as registered in the factory. This means that it should
	 * be castable to a more specific implementation type, if necessary.
	 * <p><b>NOTE:</b> This method does <i>not</i> consider ancestor factories.
	 * It is only meant for accessing local bean definitions of this factory.
	 * @param beanName the name of the bean
	 * @return the registered BeanDefinition
	 * @throws NoSuchBeanDefinitionException if there is no bean with the given name
	 * defined in this factory
	 *
	 * 获取指定Bean的BeanDefinition,运行访问其属性和构造器参数值（可能在后置处理过程中被修改）。
	 * 返回的BeanDefinition应该是原生的而不是一个拷贝。这意味着必要时应该能够转换为更加具体的实现类。
	 * 只访问本层容器，不去父容器中查询。
	 */
	BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;

	/**
	 * Return a unified view over all bean names managed by this factory.
	 * <p>Includes bean definition names as well as names of manually registered
	 * singleton instances, with bean definition names consistently coming first,
	 * analogous to how type/annotation specific retrieval of bean names works.
	 * @return the composite iterator for the bean names view
	 * @since 4.1.2
	 * @see #containsBeanDefinition
	 * @see #registerSingleton
	 * @see #getBeanNamesForType
	 * @see #getBeanNamesForAnnotation
	 *
	 * 返回一个本容器所有bean的统一视图。
	 * 包括bean定义名称以及手动注册的单例实例的名称，bean定义名称始终排在第一位，
	 * 类似于bean名称的类型/注释特定检索的工作方式
	 */
	Iterator<String> getBeanNamesIterator();

	/**
	 * Clear the merged bean definition cache, removing entries for beans
	 * which are not considered eligible for full metadata caching yet.
	 * <p>Typically triggered after changes to the original bean definitions,
	 * e.g. after applying a {@link BeanFactoryPostProcessor}. Note that metadata
	 * for beans which have already been created at this point will be kept around.
	 * @since 4.2
	 * @see #getBeanDefinition
	 * @see #getMergedBeanDefinition
	 *
	 * 清空合并后的bean definition缓存，移除没有存放完整信息的资格的缓存。
	 * 通常在原有的bean definition发生更改时被触发，例如在执行完BeanFactoryPostProcessor。
	 * 注意：已经创建的bean元数据在这时将会保留。
	 */
	void clearMetadataCache();

	/**
	 * Freeze all bean definitions, signalling that the registered bean definitions
	 * will not be modified or post-processed any further.
	 * <p>This allows the factory to aggressively cache bean definition metadata.
	 *
	 * 冻结所有bean definition,标记已注册的bean definition不会被修改或者被后置处理。
	 * 这允许容器主动缓存bean definition元数据。
	 */
	void freezeConfiguration();

	/**
	 * Return whether this factory's bean definitions are frozen,
	 * i.e. are not supposed to be modified or post-processed any further.
	 * @return {@code true} if the factory's configuration is considered frozen
	 */
	boolean isConfigurationFrozen();

	/**
	 * Ensure that all non-lazy-init singletons are instantiated, also considering
	 * {@link org.springframework.beans.factory.FactoryBean FactoryBeans}.
	 * Typically invoked at the end of factory setup, if desired.
	 * @throws BeansException if one of the singleton beans could not be created.
	 * Note: This may have left the factory with some beans already initialized!
	 * Call {@link #destroySingletons()} for full cleanup in this case.
	 * @see #destroySingletons()
	 *
	 * 确保所有非懒加载的单例均被实例化，同时考虑FactoryBean。
	 */
	void preInstantiateSingletons() throws BeansException;

}
