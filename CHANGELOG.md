# 0.3.1
> Published 04 Oct 2018

* Fixed `ValiktorWebFluxAutoConfiguration` to validate if the class `CodecConfigurer` exists on classpath.

# 0.3.0
> Published 05 Sep 2018

* Added `ConstraintViolationException` handler for Spring WebFlux.
* Added `MissingKotlinParameterException` handler for Spring WebFlux.

# 0.2.0
> Published 23 Aug 2018

* Added NotToday constraint for Date, Calendar and JavaTime types.
* Removed JAXB annotations for compatibility with JDK > 8.

# 0.1.0
> Published 13 Aug 2018

* Core module containing the validation engine and functions for all `kotlin` and `java.lang` types and internationalization support for `en_US` and `pt_BR` locales.
* JavaMoney API module containing validation functions and formatters for `JSR 354` types.
* JavaTime API module containing validation functions and formatters for `JSR 310` types.
* Spring module containing `ValiktorConfiguration` bean and a global `ExceptionHandler` for `ConstraintViolationException`.
* Spring Boot AutoConfigure module for auto configuration beans based on application properties.
* Spring Boot Starter module containing all dependencies for Spring Boot applications.