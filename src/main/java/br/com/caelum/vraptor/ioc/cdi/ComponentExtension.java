package br.com.caelum.vraptor.ioc.cdi;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.util.AnnotationLiteral;

import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;

public class ComponentExtension implements Extension {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void processAnnotatedType(@Observes final ProcessAnnotatedType pat) {		
		final AnnotatedType defaultType = pat.getAnnotatedType();
		if (pat.getAnnotatedType().getJavaClass()
				.isAnnotationPresent(Component.class)) {
			AnnotatedTypeBuilder builder = new AnnotatedTypeBuilder();
			builder.readFromType(defaultType);
			ScopesUtil registry = new ScopesUtil();
			if(!registry.isScoped(pat.getAnnotatedType().getJavaClass())){
				builder.addToClass(new AnnotationLiteral<RequestScoped>() {});
			}
			pat.setAnnotatedType(builder.create());
		}
	}
}