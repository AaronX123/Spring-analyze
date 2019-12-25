/*
 * Copyright 2002-2018 the original author or authors.
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

package org.springframework.beans.factory;

/**
 * 生词
 * mandatory adj.强制的；法定的 n.受托者
 * compulsory adj.	(因法律或规则而) 必须做的，强制的，强迫的
 * perform v.执行;表演
 * Interface to be implemented by beans that need to react once all their properties
 * have been set by a {@link BeanFactory}: e.g. to perform custom initialization,
 * or merely to check that all mandatory properties have been set.
 *
 * 被一些需要在它们所有方法被{@link BeanFactory}设置完成后进行其它响应的Bean所实现。例如执行一些特定的
 * 初始化操作，或者仅仅检查是否所有必要的参数被正确的设置。
 *
 * <p>An alternative to implementing {@code InitializingBean} is specifying a custom
 * init method, for example in an XML bean definition. For a list of all bean
 * lifecycle methods, see the {@link BeanFactory BeanFactory javadocs}.
 *
 * 一个可以替代的实现是指定一个初始化方法，例如XMLBeanDefinition。有关所有bean生命周期方法的列表，
 * 请参见{@link BeanFactory BeanFactory javadocs}。
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @see DisposableBean
 * @see org.springframework.beans.factory.config.BeanDefinition#getPropertyValues()
 * @see org.springframework.beans.factory.support.AbstractBeanDefinition#getInitMethodName()
 */
public interface InitializingBean {

	/**
	 * Invoked by the containing {@code BeanFactory} after it has set all bean properties
	 * and satisfied {@link BeanFactoryAware}, {@code ApplicationContextAware} etc.
	 * <p>This method allows the bean instance to perform validation of its overall
	 * configuration and final initialization when all bean properties have been set.
	 * @throws Exception in the event of misconfiguration (such as failure to set an
	 * essential property) or if initialization fails for any other reason
	 *
	 * 被包括BeanFactory在内的，当其已经完成所有bean属性的设置并且满足BeanFactoryAware,ApplicationContextAware
	 * 时调用。
	 * 此方法允许bean实例去执行校验属性和配置，当它的属性已经全部被设置完成后。
	 */
	void afterPropertiesSet() throws Exception;

}
