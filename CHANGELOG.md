# 0.10.0
> Published 30 Mar 2020

#### Features:

* [#30](https://github.com/valiktor/valiktor/pull/30) Added a new constraint to validate website URLs 
* [#36](https://github.com/valiktor/valiktor/pull/36) Added japanese messages

#### Improvements:

* [#33](https://github.com/valiktor/valiktor/pull/33) Removed the rule that does not allow more than 1 constraint per property
* [#37](https://github.com/valiktor/valiktor/pull/37) Upgraded to Kotlin 1.3.71

# 0.9.0
> Published 28 Oct 2019

#### Features:

* [#23](https://github.com/valiktor/valiktor/issues/23) Added catalan and spanish messages

# 0.8.0
> Published 02 Jul 2019

#### Features:

* [#13](https://github.com/valiktor/valiktor/issues/13) Added module `valiktor-test`

# 0.7.0
> Published 26 Jun 2019

#### Features:

* [#21](https://github.com/valiktor/valiktor/pull/21) Added german messages

# 0.6.0
> Published 06 Jun 2019

#### Features:

* [#8](https://github.com/valiktor/valiktor/issues/8) Added module `valiktor-jodatime`
* [#9](https://github.com/valiktor/valiktor/issues/9) Added module `valiktor-jodamoney`

#### Internal:

* [#16](https://github.com/valiktor/valiktor/issues/16) Added more sample applications

# 0.5.0
> Published 28 Apr 2019

#### Improvements:

* [#10](https://github.com/valiktor/valiktor/issues/10) Added property value as parameter in nested and collection validation functions

#### Features:

* [#15](https://github.com/valiktor/valiktor/issues/15) Added `validateForEachIndexed` function for collections and arrays

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
