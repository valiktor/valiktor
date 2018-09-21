# Valiktor

> Valiktor is a type-safe, powerful and extensible fluent DSL to validate objects in Kotlin.

[![Build Status](https://travis-ci.org/valiktor/valiktor.svg?branch=master)](https://travis-ci.org/valiktor/valiktor)
[![Build status](https://ci.appveyor.com/api/projects/status/github/valiktor/valiktor?branch=master&svg=true)](https://ci.appveyor.com/project/rodolphocouto/valiktor)
[![Coverage Status](https://codecov.io/gh/valiktor/valiktor/branch/master/graph/badge.svg)](https://codecov.io/gh/valiktor/valiktor)
[![Quality Status](https://api.codacy.com/project/badge/Grade/1826622893374838856952b9c013793a)](https://www.codacy.com/app/rodolphocouto/valiktor?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=valiktor/valiktor&amp;utm_campaign=Badge_Grade)
[![Code Style](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io)
[![Maven Central](https://img.shields.io/maven-central/v/org.valiktor/valiktor-core.svg)](https://search.maven.org/search?q=g:org.valiktor)
[![Apache License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](LICENSE)

## Installation

Gradle (Groovy):

```groovy
compile 'org.valiktor:valiktor-core:0.3.0'
```

Gradle (Kotlin DSL):

```groovy
compile("org.valiktor:valiktor-core:0.3.0")
```

Maven:

```xml
<dependency>
  <groupId>org.valiktor</groupId>
  <artifactId>valiktor-core</artifactId>
  <version>0.3.0</version>
</dependency>
```

* For install other modules, see [Modules](#modules).

## Getting Started

```kotlin
import org.valiktor.validate
import org.valiktor.functions.*

data class Employee(val id: Int, val name: String, val email: String) {
    init {
        validate(this) {
            validate(Employee::id).isPositive()
            validate(Employee::name).isNotBlank().hasSize(min = 1, max = 80)
            validate(Employee::email).isNotBlank().hasSize(min = 1, max = 50).isEmail()
        }
    }
}
```

### How it works

The main function `org.valiktor.validate` expects an object and a anonymous function that will validate it. Within this, it's possible to validate the object properties by calling `org.valiktor.validate` with the respective property as parameter. Thanks to Kotlin's powerful reflection, it's type safe and very easy, e.g.: `Employee::name`. There are many validation constraints (`org.valiktor.constraints.*`) and extended functions (`org.valiktor.functions.*`) for each data type. For example, to validate that the employee's name cannot be empty: `validate(Employee::name).isNotEmpty()`.

All the `validate` functions are evaluated and if any constraint is violated, a `ConstraintViolationException` will be thrown with a set of `ConstraintViolation` containing the property, the invalid value and the violated constraint.

For example, consider this data class:

```kotlin
data class Employee(val id: Int, val name: String)
```

And this invalid object:

```kotlin
val employee = Employee(id = 0, name = "")
```

Now, let's validate its `id` and `name` properties and handle the exception that will be thrown by printing the property name and the violated constraint:

```kotlin
try {
    validate(employee) {
        validate(Employee::id).isPositive()
        validate(Employee::name).isNotEmpty()
    }
} catch (ex: ConstraintViolationException) {
    ex.constraintViolations
        .map { "${it.property}: ${it.constraint.name}" }
        .forEach(::println)
}
```

This code will return:

```
id: Greater
name: NotEmpty
```

### Nested object properties

Valiktor can also validate nested objects and properties recursively.

For example, consider these data classes:

```kotlin
data class Employee(val company: Company)
data class Company(val city: City)
data class City(val name: String)
```

And this invalid object:

```kotlin
val employee = Employee(company = Company(city = City(name = "")))
```

Now, let's validate the property `name` of `City` object and handle the exception that will be thrown by printing the property name and the violated constraint:

```kotlin
try {
    validate(employee) {
        validate(Employee::company).validate {
            validate(Company::city).validate {
                validate(City::name).isNotEmpty()
            }
        }
    }
} catch (ex: ConstraintViolationException) {
    ex.constraintViolations
        .map { "${it.property}: ${it.constraint.name}" }
        .forEach(::println)
}
```

This code will return:

```
company.city.name: NotEmpty
```

### Array and collection properties

Array and collection properties can also be validated, including its elements.

For example, consider these data classes:

```kotlin
data class Employee(val dependents: List<Dependent>)
data class Dependent(val name: String)
```

And this invalid object:

```kotlin
val employee = Employee(dependents = listOf(
    Dependent(name = ""), 
    Dependent(name = ""), 
    Dependent(name = "")))
```

Now, let's validate the property `name` of all `Dependent` objects and handle the exception that will be thrown by printing the property name and the violated constraint:

```kotlin
try {
    validate(employee) {
        validate(Employee::dependents).validateForEach {
            validate(Dependent::name).isNotEmpty()
        }
    }
} catch (ex: ConstraintViolationException) {
    ex.constraintViolations
        .map { "${it.property}: ${it.constraint.name}" }
        .forEach(::println)
}
```

This code will return:

```
dependent[0].name: NotEmpty
dependent[1].name: NotEmpty
dependent[2].name: NotEmpty
```

### Internationalization

Valiktor supports decoupled internationalization, this allows to maintain the validation logic in the core of the application and the internationalization in another layer, such as presentation or RESTful adapter. This guarantees some design principles proposed by Domain Driven Design or Clean Architecture, for example.

The internationalization works by converting a collection of `ConstraintViolation` into a collection of `ConstraintViolationMessage` through the extended function `org.valiktor.i18n.mapToMessage` by passing the following parameters:

* `baseName`: specifies the prefix name of the message properties, the default value is `org/valiktor/messages`.
* `locale`: specifies the `java.util.Locale` of the message properties, the default value is the default locale of the application.

For example:

```kotlin
try {
    validate(employee) {
        validate(Employee::id).isPositive()
        validate(Employee::name).isNotEmpty()
    }
} catch (ex: ConstraintViolationException) {
    ex.constraintViolations
        .mapToMessage(baseName = "messages", locale = Locale.ENGLISH)
        .map { "${it.property}: ${it.message}" }
        .forEach(::println)
}
```

This code will return:

```
id: Must be greater than 1
name: Must not be empty
```

Currently the following locales are natively supported by Valiktor:

* `en`
* `pt_BR`

#### Customizing a message

Any constraint message of any language can be overwritten simply by adding the message key into your message bundle file. Generally the constraint key is the qualified class name plus `message` suffix, e.g.: `org.valiktor.constraints.NotEmpty.message`.

#### Formatters

Some constraints have parameters of many types and these parameters need to be interpolated with the message. The default behavior of Valiktor is to call the object `toString()` function, but some data types require specific formatting, such as date/time and monetary values. So for these cases, there are custom formatters (`org.valiktor.i18n.formatters.*`).

For example:

```kotlin
try {
    validate(employee) {
        validate(Employee::dateOfBirth).isGreaterThan(LocalDate.of(1950, 12, 31))
    }
} catch (ex: ConstraintViolationException) {
    ex.constraintViolations
        .mapToMessage(baseName = "messages")
        .map { "${it.property}: ${it.message}" }
        .forEach(::println)
}
```

With `en` as the default locale, this code will return:

```
dateOfBirth: Must be greater than Dec 31, 1950
```

With `pt_BR` as the default locale, this code will return:

```
dateOfBirth: Must be greater than 31/12/1950
```

Currently the following types have a custom formatter supported by Valiktor:

| Type                          | Formatter                                              | Module                                   |
| ----------------------------- | ------------------------------------------------------ | ---------------------------------------- |
| `kotlin.Any`                  | `org.valiktor.i18n.formatters.AnyFormatter`            | [valiktor-core](valiktor-core)           |
| `kotlin.Array`                | `org.valiktor.i18n.formatters.ArrayFormatter`          | [valiktor-core](valiktor-core)           |
| `kotlin.Number`               | `org.valiktor.i18n.formatters.NumberFormatter`         | [valiktor-core](valiktor-core)           |
| `kotlin.collections.Iterable` | `org.valiktor.i18n.formatters.IterableFormatter`       | [valiktor-core](valiktor-core)           |
| `java.util.Calendar`          | `org.valiktor.i18n.formatters.CalendarFormatter`       | [valiktor-core](valiktor-core)           |
| `java.util.Date`              | `org.valiktor.i18n.formatters.DateFormatter`           | [valiktor-core](valiktor-core)           |
| `java.time.LocalDate`         | `org.valiktor.i18n.formatters.LocalDateFormatter`      | [valiktor-javatime](valiktor-javatime)   |
| `java.time.LocalTime`         | `org.valiktor.i18n.formatters.LocalTimeFormatter`      | [valiktor-javatime](valiktor-javatime)   |
| `java.time.LocalDateTime`     | `org.valiktor.i18n.formatters.LocalDateTimeFormatter`  | [valiktor-javatime](valiktor-javatime)   |
| `java.time.OffsetTime`        | `org.valiktor.i18n.formatters.OffsetTimeFormatter`     | [valiktor-javatime](valiktor-javatime)   |
| `java.time.OffsetDateTime`    | `org.valiktor.i18n.formatters.OffsetDateTimeFormatter` | [valiktor-javatime](valiktor-javatime)   |
| `java.time.ZonedDateTime`     | `org.valiktor.i18n.formatters.ZonedDateTimeFormatter`  | [valiktor-javatime](valiktor-javatime)   |
| `javax.money.MonetaryAmount`  | `org.valiktor.i18n.formatters.MonetaryAmountFormatter` | [valiktor-javamoney](valiktor-javamoney) |

#### Creating a custom formatter

Creating a custom formatter is very simple, just implement the interface `org.valiktor.i18n.Formatter`, like this:

```kotlin
object CustomFormatter : Formatter<Custom> {
    
    override fun format(value: Custom, messageBundle: MessageBundle): String {
        return value.toString()
    }
}
```

Then add it to the list of formatters (`org.valiktor.i18n.Formatters`):

```kotlin
Formatters[Custom::class] = CustomFormatter
```

It's also possible to use the SPI (Service Provider Interface) provided by Valiktor using the `java.util.ServiceLoader` to discover the formatters automatically without adding to the list programmatically. For this approach, it's necessary to implement the interface `org.valiktor.i18n.FormatterSpi`, like this:

```kotlin
class CustomFormatterSpi : FormatterSpi {

    override val formatters = setOf(
        Custom::class to CustomFormatter
    )
}
```

Then create a file `org.valiktor.i18n.FormatterSpi` within the directory `META-INF.services` with the content:

```
com.company.CustomFormatterSpi
```

## Modules

| Module                                                                                   | Description                              | Artifacts                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| ---------------------------------------------------------------------------------------- | --------------------------------------   | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [valiktor-core](valiktor-core)                                                           | Core module with engine and i18n support | [![jar](https://img.shields.io/badge/jar-v0.3.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-core/0.3.0/jar) [![javadoc](https://img.shields.io/badge/javadoc-v0.3.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-core/0.3.0/javadoc) [![sources](https://img.shields.io/badge/sources-v0.3.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-core/0.3.0/sources)                                                                |
| [valiktor-javamoney](valiktor-javamoney)                                                 | JavaMoney API support                    | [![jar](https://img.shields.io/badge/jar-v0.3.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javamoney/0.3.0/jar) [![javadoc](https://img.shields.io/badge/javadoc-v0.3.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javamoney/0.3.0/javadoc) [![sources](https://img.shields.io/badge/sources-v0.3.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javamoney/0.3.0/sources)                                                 |
| [valiktor-javatime](valiktor-javatime)                                                   | JavaTime API support                     | [![jar](https://img.shields.io/badge/jar-v0.3.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javatime/0.3.0/jar) [![javadoc](https://img.shields.io/badge/javadoc-v0.3.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javatime/0.3.0/javadoc) [![sources](https://img.shields.io/badge/sources-v0.3.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javatime/0.3.0/sources)                                                    |
| [valiktor-spring](valiktor-spring/valiktor-spring)                                       | SpringMVC support                        | [![jar](https://img.shields.io/badge/jar-v0.3.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring/0.3.0/jar) [![javadoc](https://img.shields.io/badge/javadoc-v0.3.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring/0.3.0/javadoc) [![sources](https://img.shields.io/badge/sources-v0.3.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring/0.3.0/sources)                                                          |
| [valiktor-spring-boot-autoconfigure](valiktor-spring/valiktor-spring-boot-autoconfigure) | Spring Boot Auto Configuration support   | [![jar](https://img.shields.io/badge/jar-v0.3.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-autoconfigure/0.3.0/jar) [![javadoc](https://img.shields.io/badge/javadoc-v0.3.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-autoconfigure/0.3.0/javadoc) [![sources](https://img.shields.io/badge/sources-v0.3.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-autoconfigure/0.3.0/sources) |
| [valiktor-spring-boot-starter](valiktor-spring/valiktor-spring-boot-starter)             | Spring Boot Starter support              | [![jar](https://img.shields.io/badge/jar-v0.3.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-starter/0.3.0/jar) [![javadoc](https://img.shields.io/badge/javadoc-v0.3.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-starter/0.3.0/javadoc) [![sources](https://img.shields.io/badge/sources-v0.3.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-starter/0.3.0/sources)                   |

## Samples

| Project                                                                                                   | Description                       |
| --------------------------------------------------------------------------------------------------------- | --------------------------------- |
| [valiktor-sample-spring-boot-1](valiktor-samples/valiktor-sample-spring-boot-1)                           | Spring Boot 1 Example             |
| [valiktor-sample-spring-boot-2](valiktor-samples/valiktor-sample-spring-boot-2)                           | Spring Boot 2 Example             |
| [valiktor-sample-spring-boot-2-reactive](valiktor-samples/valiktor-sample-spring-boot-2-reactive)         | Spring Boot 2 WebFlux Example     |
| [valiktor-sample-spring-boot-2-reactive-dsl](valiktor-samples/valiktor-sample-spring-boot-2-reactive-dsl) | Spring Boot 2 WebFlux DSL Example |

## Changelog

For latest updates see [CHANGELOG.md](CHANGELOG.md) file.

## Contributing 

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for more details, and the process for submitting pull requests to us.

## Authors

* **[Rodolpho S. Couto](https://github.com/rodolphocouto)**

## License

This project is licensed under the Apache License, Version 2.0 - see the [LICENSE](LICENSE) file for details.