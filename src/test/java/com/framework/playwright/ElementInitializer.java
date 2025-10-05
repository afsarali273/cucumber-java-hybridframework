package com.framework.playwright;

import java.lang.reflect.Field;
import com.framework.playwright.annotations.FindBy;
import com.microsoft.playwright.Page;

/**
 * Initializes PlaywrightElement fields in Page Objects
 */
public class ElementInitializer {
    
    public static void initElements(Page page, Object pageObject) {
        Class<?> clazz = pageObject.getClass();
        
        while (clazz != Object.class) {
            Field[] fields = clazz.getDeclaredFields();
            
            for (Field field : fields) {
                if (field.getType() == PlaywrightElement.class && field.isAnnotationPresent(FindBy.class)) {
                    initElement(page, pageObject, field);
                }
            }
            
            clazz = clazz.getSuperclass();
        }
    }
    
    private static void initElement(Page page, Object pageObject, Field field) {
        try {
            FindBy findBy = field.getAnnotation(FindBy.class);
            String selector = buildSelector(findBy);
            
            if (selector.isEmpty()) {
                throw new RuntimeException("No selector specified for field: " + field.getName());
            }
            
            PlaywrightElement element = new PlaywrightElement(page, selector);
            
            field.setAccessible(true);
            field.set(pageObject, element);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize element: " + field.getName(), e);
        }
    }
    
    private static String buildSelector(FindBy findBy) {
        if (!findBy.css().isEmpty()) {
            return findBy.css();
        }
        if (!findBy.xpath().isEmpty()) {
            return findBy.xpath();
        }
        if (!findBy.id().isEmpty()) {
            return "#" + findBy.id();
        }
        if (!findBy.text().isEmpty()) {
            return "text=" + findBy.text();
        }
        if (!findBy.role().isEmpty()) {
            return "role=" + findBy.role();
        }
        if (!findBy.testId().isEmpty()) {
            return "[data-testid='" + findBy.testId() + "']";
        }
        if (!findBy.placeholder().isEmpty()) {
            return "[placeholder='" + findBy.placeholder() + "']";
        }
        if (!findBy.label().isEmpty()) {
            return "label=" + findBy.label();
        }
        
        return "";
    }
}