# :rocket: 0.10.0
> Published 30 Mar 2020

## Features

* :heavy_check_mark: [#30](https://github.com/valiktor/valiktor/pull/30) Added a new constraint to validate website URLs 
* :heavy_check_mark: [#36](https://github.com/valiktor/valiktor/pull/36) Added japanese messages

## Improvements

* :heavy_check_mark: [#33](https://github.com/valiktor/valiktor/pull/33) Removed the rule that does not allow more than 1 constraint per property
* :heavy_check_mark: [#37](https://github.com/valiktor/valiktor/pull/37) Upgraded to Kotlin 1.3.71

# :rocket: 0.9.0
> Published 28 Oct 2019

## Features

* :heavy_check_mark: [#23](https://github.com/valiktor/valiktor/issues/23) Added catalan and spanish messages

# :rocket: 0.8.0
> Published 02 Jul 2019

## Features

* :heavy_check_mark: [#13](https://github.com/valiktor/valiktor/issues/13) Added module `valiktor-test`

# :rocket: 0.7.0
> Published 26 Jun 2019

## Features

* :heavy_check_mark: [#21](https://github.com/valiktor/valiktor/pull/21) Added german messages

# :rocket: 0.6.0
> Published 06 Jun 2019

## Features

* :heavy_check_mark: [#8](https://github.com/valiktor/valiktor/issues/8) Added module `valiktor-jodatime`
* :heavy_check_mark: [#9](https://github.com/valiktor/valiktor/issues/9) Added module `valiktor-jodamoney`

## Internal

* :heavy_check_mark: [#16](https://github.com/valiktor/valiktor/issues/16) Added more sample applications

# :rocket: 0.5.0
> Published 28 Apr 2019

## Improvements

* :heavy_check_mark: [#10](https://github.com/valiktor/valiktor/issues/10) Added property value as parameter in nested and collection validation functions

## Features

* :heavy_check_mark: [#15](https://github.com/valiktor/valiktor/issues/15) Added `validateForEachIndexed` function for collections and arrays

# :rocket: 0.4.0
> Published 16 Jan 2019

## Improvements

* :heavy_check_mark: [#1](https://github.com/valiktor/valiktor/issues/1) Removed message key prefix restriction in favor of custom constraints
* :heavy_check_mark: [#2](https://github.com/valiktor/valiktor/issues/2) Renamed `ValiktorExceptionHandler` to `ConstraintViolationExceptionHander`
* :heavy_check_mark: [#2](https://github.com/valiktor/valiktor/issues/2) Renamed `ValiktorJacksonExceptionHandler` to `MissingKotlinParameterExceptionHandler`

## Features

* :heavy_check_mark: [#2](https://github.com/valiktor/valiktor/issues/2) Added exception handler for `InvalidFormatException`

## Internal

* :heavy_check_mark: [#3](https://github.com/valiktor/valiktor/issues/3) Upgraded to Gradle 5 and Kotlin 1.3
* :heavy_check_mark: [#4](https://github.com/valiktor/valiktor/issues/4) Added build step with Java 11

# :rocket: 0.3.1
> Published 05 Oct 2018

## Bug fixes

* :heavy_check_mark: Fixed `ValiktorWebFluxAutoConfiguration` to validate if the bean `CodecConfigurer` exists on classpath

# :rocket: 0.3.0
> Published 05 Sep 2018

## Features

* :heavy_check_mark: Added `ConstraintViolationException` handler for Spring WebFlux
* :heavy_check_mark: Added `MissingKotlinParameterException` handler for Spring WebFlux

# :rocket: 0.2.0
> Published 23 Aug 2018

## Features

* :heavy_check_mark: Added NotToday constraint for Date, Calendar and JavaTime types

## Improvements

* :heavy_check_mark: Removed JAXB annotations for compatibility with JDK > 8

# :rocket: 0.1.0
> Published 13 Aug 2018

## Features

* :heavy_check_mark: Core module containing the validation engine and functions for all `kotlin` and `java.lang` types and internationalization support for `en_US` and `pt_BR` locales
* :heavy_check_mark: JavaMoney API module containing validation functions and formatters for `JSR 354` types
* :heavy_check_mark: JavaTime API module containing validation functions and formatters for `JSR 310` types
* :heavy_check_mark: Spring module containing `ValiktorConfiguration` bean and a global `ExceptionHandler` for `ConstraintViolationException`
* :heavy_check_mark: Spring Boot AutoConfigure module for auto configuration beans based on application properties
* :heavy_check_mark: Spring Boot Starter module containing all dependencies for Spring Boot applications
