package in.sdqali.experiments.spring;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonFilter {
    // JSON keys that will be used for filtering
    String[] keys() default {};
}
