# 0.4.0
> Published 16 Jan 2019

#### Improvements:

* [#1](https://github.com/valiktor/valiktor/issues/1) Removed message key prefix restriction in favor of custom constraints
* [#2](https://github.com/valiktor/valiktor/issues/2) Renamed `ValiktorExceptionHandler` to `ConstraintViolationExceptionHander`
* [#2](https://github.com/valiktor/valiktor/issues/2) Renamed `ValiktorJacksonExceptionHandler` to `MissingKotlinParameterExceptionHandler`

#### Features:

* [#2](https://github.com/valiktor/valiktor/issues/2) Added exception handler for `InvalidFormatException`

#### Internal:

* [#3](https://github.com/valiktor/valiktor/issues/3) Upgraded to Gradle 5 and Kotlin 1.3
* [#4](https://github.com/valiktor/valiktor/issues/4) Added build step with Java 11

# 0.3.1
> Published 05 Oct 2018

#### Bug fixes:

* Fixed `ValiktorWebFluxAutoConfiguration` to validate if the bean `CodecConfigurer` exists on classpath

# 0.3.0
> Published 05 Sep 2018

#### Features:

* Added `ConstraintViolationException` handler for Spring WebFlux
* Added `MissingKotlinParameterException` handler for Spring WebFlux

# 0.2.0
> Published 23 Aug 2018

#### Features:

* Added NotToday constraint for Date, Calendar and JavaTime types

#### Improvements:

* Removed JAXB annotations for compatibility with JDK > 8

# 0.1.0
> Published 13 Aug 2018

#### Features:

* Core module containing the validation engine and functions for all `kotlin` and `java.lang` types and internationalization support for `en_US` and `pt_BR` locales
* JavaMoney API module containing validation functions and formatters for `JSR 354` types
* JavaTime API module containing validation functions and formatters for `JSR 310` types
* Spring module containing `ValiktorConfiguration` bean and a global `ExceptionHandler` for `ConstraintViolationException`
* Spring Boot AutoConfigure module for auto configuration beans based on application properties
* Spring Boot Starter module containing all dependencies for Spring Boot applications