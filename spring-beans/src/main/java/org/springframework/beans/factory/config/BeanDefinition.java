/*
 * Copyright 2002-2019 the original author or authors.
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

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.AttributeAccessor;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

/**

 *
 */

/**
 * 生词
 * concrete adj.具体的，确实的
 * introspect v.自省,反省
 * identifier n.描述符，标识号
 * corresponds v.相一致;符合;类似于;相当于;通信
 * corresponds to 相当于
 * hint n.v.暗示，透露，示意，征兆，少量
 * relevance n.重要性；相关性
 * post-process 后处理；后置处理
 * variant n.变种；变体 adj.相异的，多样的，易变的
 * eager adj.热切地，渴望的
 * consequence n.结果，后果，重要性
 * even if 即使
 * nevertheless adv.尽管如此；不过；然而
 * serve as 充当
 * indication n.表明;标示;显示;象征
 * retrieve v.取回；索回；扭转颓势；挽回
 * if any 如果有的话
 *
 *
 *
 *
 *
 * A BeanDefinition describes a bean instance, which has property values,
 * constructor argument values, and further information supplied by
 * concrete implementations.
 *
 * BeanDefinition描述一个Bean实例，包含属性值，构造器参数以及更多关于实现方法的信息。
 * 这个是最小的接口，为的是让BeanFactoryPostProcessor通过自省机制来对类的元信息进行修改
 *
 * <p>This is just a minimal interface: The main intention is to allow a
 * {@link BeanFactoryPostProcessor} to introspect and modify property values
 * and other bean metadata.
 *
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @since 19.03.2004
 * @see ConfigurableListableBeanFactory#getBeanDefinition
 * @see org.springframework.beans.factory.support.RootBeanDefinition
 * @see org.springframework.beans.factory.support.ChildBeanDefinition
 */
public interface BeanDefinition extends AttributeAccessor, BeanMetadataElement {

	/**
	 * Scope identifier for the standard singleton scope: "singleton".
	 * <p>Note that extended bean factories might support further scopes.
	 *
	 * 作用域描述符，标记此为单例，其它的BeanFactory实现类中可能出现更多的作用域
	 * @see #setScope
	 */
	String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

	/**
	 * Scope identifier for the standard prototype scope: "prototype".
	 * <p>Note that extended bean factories might support further scopes.
	 * @see #setScope
	 */
	String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;


	/**
	 * Role hint indicating that a {@code BeanDefinition} is a major part
	 * of the application. Typically corresponds to a user-defined bean.
	 *
	 * 表示BeanDefinition是应用程序中最主要的部分，通常对应于用户定义的bean
	 */
	int ROLE_APPLICATION = 0;

	/**
	 * Role hint indicating that a {@code BeanDefinition} is a supporting
	 * part of some larger configuration, typically an outer
	 * {@link org.springframework.beans.factory.parsing.ComponentDefinition}.
	 * {@code SUPPORT} beans are considered important enough to be aware
	 * of when looking more closely at a particular
	 * {@link org.springframework.beans.factory.parsing.ComponentDefinition},
	 * but not when looking at the overall configuration of an application.
	 *
	 *
	 */
	int ROLE_SUPPORT = 1;

	/**
	 * Role hint indicating that a {@code BeanDefinition} is providing an
	 * entirely background role and has no relevance to the end-user. This hint is
	 * used when registering beans that are completely part of the internal workings
	 * of a {@link org.springframework.beans.factory.parsing.ComponentDefinition}.
	 */
	int ROLE_INFRASTRUCTURE = 2;


	// Modifiable attributes

	/**
	 * Set the name of the parent definition of this bean definition, if any.
	 *
	 */
	void setParentName(@Nullable String parentName);

	/**
	 * Return the name of the parent definition of this bean definition, if any.
	 */
	@Nullable
	String getParentName();

	/**
	 * Specify the bean class name of this bean definition.
	 * <p>The class name can be modified during bean factory post-processing,
	 * typically replacing the original class name with a parsed variant of it.
	 *
	 * 具体描述这个BeanDefinition中这个Bean的名字，Bean名字能够在整个后处理过程中被修改
	 * ，通常是把原来的类名替换成变形名称
	 *
	 * @see #setParentName
	 * @see #setFactoryBeanName
	 * @see #setFactoryMethodName
	 */
	void setBeanClassName(@Nullable String beanClassName);

	/**
	 * Return the current bean class name of this bean definition.
	 * <p>Note that this does not have to be the actual class name used at runtime, in
	 * case of a child definition overriding/inheriting the class name from its parent.
	 * Also, this may just be the class that a factory method is called on, or it may
	 * even be empty in case of a factory bean reference that a method is called on.
	 * Hence, do <i>not</i> consider this to be the definitive bean type at runtime but
	 * rather only use it for parsing purposes at the individual bean definition level.
	 *
	 * 返回此BeanDefinition所描述的Bean的类名。需要注意的是，这个名称可能不是真正环境中的类名，有可能
	 * 被子类修改过类名，也有可能只是BeanFactory调用的或者是空的当BeanFactory引用它的方法进行调用时。
	 * 因此，不要肯定的认为这个类名就是真正的类定义，而是应该把它作为转换的类型。
	 * @see #getParentName()
	 * @see #getFactoryBeanName()
	 * @see #getFactoryMethodName()
	 */
	@Nullable
	String getBeanClassName();

	/**
	 * Override the target scope of this bean, specifying a new scope name.
	 * @see #SCOPE_SINGLETON
	 * @see #SCOPE_PROTOTYPE
	 */
	void setScope(@Nullable String scope);

	/**
	 * Return the name of the current target scope for this bean,
	 * or {@code null} if not known yet.
	 */
	@Nullable
	String getScope();

	/**
	 * Set whether this bean should be lazily initialized.
	 * <p>If {@code false}, the bean will get instantiated on startup by bean
	 * factories that perform eager initialization of singletons.
	 *
	 * 是否需要懒加载，如果是，这个Bean将不会在启动时被加入容器
	 */
	void setLazyInit(boolean lazyInit);

	/**
	 * Return whether this bean should be lazily initialized, i.e. not
	 * eagerly instantiated on startup. Only applicable to a singleton bean.
	 *
	 * 返回这个Bean是否是懒加载，只适用于单例模式
	 */
	boolean isLazyInit();

	/**
	 * Set the names of the beans that this bean depends on being initialized.
	 * The bean factory will guarantee that these beans get initialized first.
	 *
	 * 设置此Bean构造时依赖的其它Bean的名字，BeanFactory会保证这些Bean会被最先实例化
	 */
	void setDependsOn(@Nullable String... dependsOn);

	/**
	 * Return the bean names that this bean depends on.
	 */
	@Nullable
	String[] getDependsOn();

	/**
	 * Set whether this bean is a candidate for getting autowired into some other bean.
	 * <p>Note that this flag is designed to only affect type-based autowiring.
	 * It does not affect explicit references by name, which will get resolved even
	 * if the specified bean is not marked as an autowire candidate. As a consequence,
	 * autowiring by name will nevertheless inject a bean if the name matches.
	 *
	 * 设置标识Bean是否是要被其它Bean装配。需要注意的是，这个标志仅仅被设计用于影响基于类型的
	 * 自动装配。它不影响按名称的显式引用，即使指定的Bean未标记为@Autowire，也会解析显式引用。
	 * 结果是，如果名称匹配，按名称自动连接仍将注入Bean。
	 */
	void setAutowireCandidate(boolean autowireCandidate);

	/**
	 * Return whether this bean is a candidate for getting autowired into some other bean.
	 */
	boolean isAutowireCandidate();

	/**
	 * Set whether this bean is a primary autowire candidate.
	 * <p>If this value is {@code true} for exactly one bean among multiple
	 * matching candidates, it will serve as a tie-breaker.
	 *
	 * 设置此Bean是首先被装配的对象，如果为true，则在有多个自动装配对象时，它会被直接装配
	 */
	void setPrimary(boolean primary);

	/**
	 * Return whether this bean is a primary autowire candidate.
	 */
	boolean isPrimary();

	/**
	 * Specify the factory bean to use, if any.
	 * This the name of the bean to call the specified factory method on.
	 * @see #setFactoryMethodName
	 */
	void setFactoryBeanName(@Nullable String factoryBeanName);

	/**
	 * Return the factory bean name, if any.
	 */
	@Nullable
	String getFactoryBeanName();

	/**
	 * Specify a factory method, if any. This method will be invoked with
	 * constructor arguments, or with no arguments if none are specified.
	 * The method will be invoked on the specified factory bean, if any,
	 * or otherwise as a static method on the local bean class.
	 * @see #setFactoryBeanName
	 * @see #setBeanClassName
	 */
	void setFactoryMethodName(@Nullable String factoryMethodName);

	/**
	 * Return a factory method, if any.
	 */
	@Nullable
	String getFactoryMethodName();

	/**
	 * Return the constructor argument values for this bean.
	 * <p>The returned instance can be modified during bean factory post-processing.
	 * @return the ConstructorArgumentValues object (never {@code null})
	 */
	ConstructorArgumentValues getConstructorArgumentValues();

	/**
	 * Return if there are constructor argument values defined for this bean.
	 * @since 5.0.2
	 */
	default boolean hasConstructorArgumentValues() {
		return !getConstructorArgumentValues().isEmpty();
	}

	/**
	 * Return the property values to be applied to a new instance of the bean.
	 * <p>The returned instance can be modified during bean factory post-processing.
	 * @return the MutablePropertyValues object (never {@code null})
	 */
	MutablePropertyValues getPropertyValues();

	/**
	 * Return if there are property values values defined for this bean.
	 * @since 5.0.2
	 */
	default boolean hasPropertyValues() {
		return !getPropertyValues().isEmpty();
	}

	/**
	 * Set the name of the initializer method.
	 * @since 5.1
	 */
	void setInitMethodName(@Nullable String initMethodName);

	/**
	 * Return the name of the initializer method.
	 * @since 5.1
	 */
	@Nullable
	String getInitMethodName();

	/**
	 * Set the name of the destroy method.
	 * @since 5.1
	 */
	void setDestroyMethodName(@Nullable String destroyMethodName);

	/**
	 * Return the name of the destroy method.
	 * @since 5.1
	 */
	@Nullable
	String getDestroyMethodName();

	/**
	 * Set the role hint for this {@code BeanDefinition}. The role hint
	 * provides the frameworks as well as tools with an indication of
	 * the role and importance of a particular {@code BeanDefinition}.
	 * @since 5.1
	 * @see #ROLE_APPLICATION
	 * @see #ROLE_SUPPORT
	 * @see #ROLE_INFRASTRUCTURE
	 */
	void setRole(int role);

	/**
	 * Get the role hint for this {@code BeanDefinition}. The role hint
	 * provides the frameworks as well as tools with an indication of
	 * the role and importance of a particular {@code BeanDefinition}.
	 * @see #ROLE_APPLICATION
	 * @see #ROLE_SUPPORT
	 * @see #ROLE_INFRASTRUCTURE
	 */
	int getRole();

	/**
	 * Set a human-readable description of this bean definition.
	 * @since 5.1
	 */
	void setDescription(@Nullable String description);

	/**
	 * Return a human-readable description of this bean definition.
	 */
	@Nullable
	String getDescription();


	// Read-only attributes

	/**
	 * Return a resolvable type for this bean definition,
	 * based on the bean class or other specific metadata.
	 * <p>This is typically fully resolved on a runtime-merged bean definition
	 * but not necessarily on a configuration-time definition instance.
	 * @return the resolvable type (potentially {@link ResolvableType#NONE})
	 * @since 5.2
	 * @see ConfigurableBeanFactory#getMergedBeanDefinition
	 */
	ResolvableType getResolvableType();

	/**
	 * Return whether this a <b>Singleton</b>, with a single, shared instance
	 * returned on all calls.
	 * @see #SCOPE_SINGLETON
	 */
	boolean isSingleton();

	/**
	 * Return whether this a <b>Prototype</b>, with an independent instance
	 * returned for each call.
	 * @since 3.0
	 * @see #SCOPE_PROTOTYPE
	 */
	boolean isPrototype();

	/**
	 * Return whether this bean is "abstract", that is, not meant to be instantiated.
	 */
	boolean isAbstract();

	/**
	 * Return a description of the resource that this bean definition
	 * came from (for the purpose of showing context in case of errors).
	 */
	@Nullable
	String getResourceDescription();

	/**
	 * Return the originating BeanDefinition, or {@code null} if none.
	 * Allows for retrieving the decorated bean definition, if any.
	 * <p>Note that this method returns the immediate originator. Iterate through the
	 * originator chain to find the original BeanDefinition as defined by the user.
	 *
	 * 返回最初的BeanDefinition或者Null,允许检索包装类的BeanDefinition.需要注意的是，这个方法
	 * 返回的是当前的发起方，通过originator链去查询用户定义的原始BeanDefinition.
	 */
	@Nullable
	BeanDefinition getOriginatingBeanDefinition();

}
