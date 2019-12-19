/*
 * Copyright 2002-2015 the original author or authors.
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

package org.springframework.aop.framework;

import java.io.Serializable;
import java.lang.reflect.Proxy;

import org.springframework.aop.SpringProxy;

/**
 * Default {@link AopProxyFactory} implementation, creating either a CGLIB proxy
 * or a JDK dynamic proxy.
 *
 * <p>Creates a CGLIB proxy if one the following is true for a given
 * {@link AdvisedSupport} instance:
 * <ul>
 * <li>the {@code optimize} flag is set
 * <li>the {@code proxyTargetClass} flag is set
 * <li>no proxy interfaces have been specified
 * </ul>
 *
 * <p>In general, specify {@code proxyTargetClass} to enforce a CGLIB proxy,
 * or specify one or more interfaces to use a JDK dynamic proxy.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 12.03.2004
 * @see AdvisedSupport#setOptimize
 * @see AdvisedSupport#setProxyTargetClass
 * @see AdvisedSupport#setInterfaces
 */
@SuppressWarnings("serial")
public class DefaultAopProxyFactory implements AopProxyFactory, Serializable {

	@Override
	public AopProxy createAopProxy(AdvisedSupport config) throws AopConfigException {
		//isOptimize和proxyTargetClass默认为false
		if (config.isOptimize() || config.isProxyTargetClass() || hasNoUserSuppliedProxyInterfaces(config)) {
			//从config中取出配置对象
			Class<?> targetClass = config.getTargetClass();
			if (targetClass == null) {
				throw new AopConfigException("TargetSource cannot determine target class: " +
						"Either an interface or a target is required for proxy creation.");
			}
			/**
			 * 如果targetClass是接口，并且这个targetClass在缓存中有，就使用JDKProxy
			 *
			 *
			 * isAssignableFrom
			 * 判定一个当前Class对象表示的类或者接口与传入的Class参数相比，是否相同，或者
			 * 是否是它的一个超类或者超接口。是的话返回true，否则就返回false。当这个Class
			 * 对象表示一种原始类型时，如果传入的Class对象参数就是这个Class对象，那么返回
			 * 真，否则为假。
			 *
			 * 特别的，这个方法可以检查传入的Class参数所代表的类是否可以通过一个类型转换，
			 * 或者一个扩大的引用转换转化为当前Class对象所表示的类型。更多细节请参考Java语
			 * 言规范 5.1.1 和 5.1.4 两部分章节。
			 *
			 *     参数： cls 需要检查的Class对象
			 *
			 *     返回值：一个表示cls类的对象是否可以转换为这个类对象的布尔值
			 *
			 *     抛出的异常：如果传入的cls参数为空，则抛出空指针异常
			 *
			 *     版本：JDK 1.1
			 *
			 *     总结起来就是说isAssignableFrom方法用于判断两个类或者接口是否是相同的类，
			 *     或者入参类是否是该类的子类。
			 */
			if (targetClass.isInterface() || Proxy.isProxyClass(targetClass)) {
				return new JdkDynamicAopProxy(config);
			}
			return new ObjenesisCglibAopProxy(config);
		}
		else {
			return new JdkDynamicAopProxy(config);
		}
	}

	/**
	 * Determine whether the supplied {@link AdvisedSupport} has only the
	 * {@link org.springframework.aop.SpringProxy} interface specified
	 * (or no proxy interfaces specified at all).
	 */
	private boolean hasNoUserSuppliedProxyInterfaces(AdvisedSupport config) {
		//查看是否有接口，如果接口数为0或者只有一个接口是SpringProxy
		Class<?>[] ifcs = config.getProxiedInterfaces();
		return (ifcs.length == 0 || (ifcs.length == 1 && SpringProxy.class.isAssignableFrom(ifcs[0])));
	}

}
