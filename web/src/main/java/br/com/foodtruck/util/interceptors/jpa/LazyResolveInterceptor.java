package br.com.foodtruck.util.interceptors.jpa;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LazyResolveInterceptor {

	private static final Logger log = Logger.getLogger(LazyResolveInterceptor.class.getName());

	private static Map<Class<?>, Field[]> classesEntities = new HashMap<>();

	@AroundInvoke
	public Object intercept(InvocationContext context) throws Exception {
		log.info("LazyResolveInterceptor - Logging BEFORE calling method :" + context.getMethod().getName());
		Object result = context.proceed();
		if (context.getMethod().getReturnType() == java.util.List.class) {
			eleminteAllProxysToList((List<?>) result);
		} else if ((result != null) && (isEntity(result.getClass()))) {
			eleminteAllProxysToObject(result);
		}
		return result;
	}

	private void eleminteAllProxysToList(List<?> listOfObjects) {

		if ((listOfObjects != null) && (!listOfObjects.isEmpty())) {
			for (Iterator<?> iterator = listOfObjects.iterator(); iterator.hasNext();) {
				Object objectOfList = iterator.next();
				eleminteAllProxysToObject(objectOfList);
			}

		}
	}

	private void eleminteAllProxysToObject(Object entity) {
		Field[] fields = fieldsOfEntity(entity);
		if (fields != null) {
			for (Field field : fields) {
				try {
					entity.getClass().getDeclaredField(field.getName()).setAccessible(true);
					if (field.get(entity) != null) {
						String nameCanonical = field.get(entity).getClass().getCanonicalName();
						if (nameCanonical
								.equalsIgnoreCase("org.eclipse.persistence.internal.indirection.jdk8.IndirectList")) {
							Object proxy = field.get(entity);
							Field[] fieldsProxy = proxy.getClass().getDeclaredFields();
							for (int j = 0; j < fieldsProxy.length; j++) {
								log.info(fieldsProxy[j].getName());
							}
							field.set(entity, null);
						} else if (nameCanonical.equalsIgnoreCase("org.hibernate.collection.internal.PersistentBag")) {
							Object proxy = field.get(entity);
							Field fieldProxy = proxy.getClass().getDeclaredField("bag");
							fieldProxy.setAccessible(true);
							if (fieldProxy.get(proxy) == null) {
								field.set(entity, null);
							}
						} else if (!nameCanonical.equalsIgnoreCase(field.getType().getCanonicalName())) {
							field.set(entity, null);
						} else if (isEntity(field.getType())) {
							eleminteAllProxysToObject(field.get(entity));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	private Field[] fieldsOfEntity(Object object) {
		Field[] result = null;
		if (classesEntities.containsKey(object.getClass())) {
			result = classesEntities.get(object.getClass());
		} else if (isEntity(object.getClass())) {
			List<Field> fieldsList = new ArrayList<>();
			Field[] fieldsToClass = object.getClass().getDeclaredFields();
			for (int j = 0; j < fieldsToClass.length; j++) {
				if ((fieldsToClass[j].getType() == java.util.List.class) || (isEntity(fieldsToClass[j].getType()))) {
					fieldsToClass[j].setAccessible(true);
					fieldsList.add(fieldsToClass[j]);
				}
			}
			Field[] fields = new Field[fieldsList.size()];
			fields = fieldsList.toArray(fields);
			classesEntities.put(object.getClass(), fields);
			result = fields;

		}

		return result;
	}

	private boolean isEntity(Class<?> classOfObjects) {
		if (classesEntities.containsKey(classOfObjects)) {
			return true;
		}
		Annotation[] annotations = classOfObjects.getAnnotations();
		if ((annotations == null) || (annotations.length == 0)) {
			return false;
		}
		for (int i = 0; i < annotations.length; i++) {
			Annotation annotation = annotations[i];
			if (annotation.annotationType() == javax.persistence.Entity.class) {
				return true;
			}
		}
		return false;
	}

}