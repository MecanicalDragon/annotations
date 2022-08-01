package io.github.mecanicaldragon.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * This annotation checks if annotated field is named as its 'value' parameter.
 * If it isn't {@link RuntimeException} will be thrown during compilation.
 *
 * @author Stanislav Tretyakov
 * 11.07.2022
 */
@Retention(SOURCE)
@Target(
    value = {
        ElementType.TYPE,
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.PARAMETER
    }
)
public @interface StrictName {
    String value();
}
