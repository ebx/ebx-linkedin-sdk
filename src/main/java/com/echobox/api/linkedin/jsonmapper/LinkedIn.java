package com.echobox.api.linkedin.jsonmapper;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface LinkedIn {
  
  /**
   * Name of the Facebook API result attribute to map to - {@code affiliation}, for example.
   * 
   * @return Name of the Facebook API result attribute to map to.
   */
  String value() default "";

}
