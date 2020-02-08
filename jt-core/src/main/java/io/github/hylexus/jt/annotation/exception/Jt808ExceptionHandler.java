package io.github.hylexus.jt.annotation.exception;

import java.lang.annotation.*;

/**
 * @author hylexus
 * Created At 2020-02-08 3:12 下午
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Jt808ExceptionHandler {

    Class<? extends Throwable>[] value() default {};

}