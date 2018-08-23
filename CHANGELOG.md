# 0.2.0
> Published Aug 23, 2018

* Added NotToday constraint for Date, Calendar and JavaTime types.
* Removed JAXB annotations for compatibility with JDK > 8.

# 0.1.0
> Published Aug 13, 2018

* Core module containing the validation engine and functions for all `kotlin` and `java.lang` types and internationalization support for `en_US` and `pt_BR` locales.
* JavaMoney API module containing validation functions and formatters for `JSR 354` types.
* JavaTime API module containing validation functions and formatters for `JSR 310` types.
* Spring module containing `ValiktorConfiguration` bean and a global `ExceptionHandler` for `ConstraintViolationException`.
* Spring Boot AutoConfigure module for auto configuration beans based on application properties.
* Spring Boot Starter module containing all dependencies for Spring Boot applications.