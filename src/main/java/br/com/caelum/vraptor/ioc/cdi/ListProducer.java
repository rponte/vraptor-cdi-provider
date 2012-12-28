package br.com.caelum.vraptor.ioc.cdi;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

import br.com.caelum.vraptor.ioc.cdi.BeanManagerUtil;

public class ListProducer {
	
	private BeanManager beanManager;
	private BeanManagerUtil beanManagerUtil;
	
	@Inject
	public ListProducer(BeanManager beanManager) {
		this.beanManager = beanManager;
		beanManagerUtil = new BeanManagerUtil(beanManager);
	}

	@Deprecated
	public ListProducer() {
	}



	@Produces
	public List producesList(InjectionPoint injectionPoint){
		ParameterizedType type = (ParameterizedType) injectionPoint.getType();
	    Class classe = (Class) type.getActualTypeArguments()[0];
	    Set<Bean<?>> beans = beanManager.getBeans(classe);
	    ArrayList objects = new ArrayList();
	    for (Bean<?> bean : beans) {			
			objects.add(beanManagerUtil.instanceFor(bean));
		}
		return objects;
	}
}
