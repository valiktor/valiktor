![Valiktor](docs/logo.png)

> Valiktor is a type-safe, powerful and extensible fluent DSL to validate objects in Kotlin.

[![Build Status](https://travis-ci.org/valiktor/valiktor.svg?branch=master)](https://travis-ci.org/valiktor/valiktor)
[![Build status](https://ci.appveyor.com/api/projects/status/github/valiktor/valiktor?branch=master&svg=true)](https://ci.appveyor.com/project/rodolphocouto/valiktor)
[![Coverage Status](https://codecov.io/gh/valiktor/valiktor/branch/master/graph/badge.svg)](https://codecov.io/gh/valiktor/valiktor)
[![Quality Status](https://api.codacy.com/project/badge/Grade/1826622893374838856952b9c013793a)](https://www.codacy.com/app/rodolphocouto/valiktor?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=valiktor/valiktor&amp;utm_campaign=Badge_Grade)
[![Code Style](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io)
[![Maven Central](https://img.shields.io/maven-central/v/org.valiktor/valiktor-core.svg)](https://search.maven.org/search?q=g:org.valiktor)
[![Apache License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](LICENSE)

## Installation

![Gradle](docs/gradle.png)

```groovy
implementation 'org.valiktor:valiktor-core:0.5.0'
```

![Gradle](docs/gradle.png) (Kotlin DSL)

```kotlin
implementation("org.valiktor:valiktor-core:0.5.0")
```

![Maven](docs/maven.png) 

```xml
<dependency>
  <groupId>org.valiktor</groupId>
  <artifactId>valiktor-core</artifactId>
  <version>0.5.0</version>
</dependency>
```

* For install other modules, see [Modules](#modules).

## Getting Started

```kotlin
data class Employee(val id: Int, val name: String, val email: String) {
    init {
        validate(this) {
            validate(Employee::id).isPositive()
            validate(Employee::name).hasSize(min = 3, max = 80)
            validate(Employee::email).isNotBlank().isEmail()
        }
    }
}
```

### How it works

The main function `org.valiktor.validate` expects an object and an anonymous function that will validate it. Within this, it's possible to validate the object properties by calling `org.valiktor.validate` with the respective property as parameter. Thanks to Kotlin's powerful reflection, it's type safe and very easy, e.g.: `Employee::name`. There are many validation constraints (`org.valiktor.constraints.*`) and extension functions (`org.valiktor.functions.*`) for each data type. For example, to validate that the employee's name cannot be empty: `validate(Employee::name).isNotEmpty()`.

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

See the [sample](valiktor-samples/valiktor-sample-hello-world)

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

See the [sample](valiktor-samples/valiktor-sample-nested-properties)

### Array and collection properties

Array and collection properties can also be validated, including its elements.

For example, consider these data classes:

```kotlin
data class Employee(val dependents: List<Dependent>)
data class Dependent(val name: String)
```

And this invalid object:

```kotlin
val employee = Employee(
    dependents = listOf(
        Dependent(name = ""), 
        Dependent(name = ""), 
        Dependent(name = "")
    )
)
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
dependents[0].name: NotEmpty
dependents[1].name: NotEmpty
dependents[2].name: NotEmpty
```

See the [sample](valiktor-samples/valiktor-sample-collections)

### Internationalization

Valiktor supports decoupled internationalization, this allows to maintain the validation logic in the core of the application and the internationalization in another layer, such as presentation or RESTful adapter. This guarantees some design principles proposed by Domain-Driven Design or Clean Architecture, for example.

The internationalization works by converting a collection of `ConstraintViolation` into a collection of `ConstraintViolationMessage` through the extension function `org.valiktor.i18n.mapToMessage` by passing the following parameters:

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

#### Message formatters

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
dateOfBirth: Deve ser maior que 31/12/1950
```

Currently the following types have a custom formatter supported by Valiktor:

| Type                          | Formatter                                                                                                                                          |
| ----------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------- |
| `kotlin.Any`                  | [org.valiktor.i18n.formatters.AnyFormatter](valiktor-core/src/main/kotlin/org/valiktor/i18n/formatters/AnyFormatter.kt)                            |
| `kotlin.Array`                | [org.valiktor.i18n.formatters.ArrayFormatter](valiktor-core/src/main/kotlin/org/valiktor/i18n/formatters/ArrayFormatter.kt)                        |
| `kotlin.Number`               | [org.valiktor.i18n.formatters.NumberFormatter](valiktor-core/src/main/kotlin/org/valiktor/i18n/formatters/NumberFormatter.kt)                      |
| `kotlin.collections.Iterable` | [org.valiktor.i18n.formatters.IterableFormatter](valiktor-core/src/main/kotlin/org/valiktor/i18n/formatters/IterableFormatter.kt)                  |
| `java.util.Calendar`          | [org.valiktor.i18n.formatters.CalendarFormatter](valiktor-core/src/main/kotlin/org/valiktor/i18n/formatters/CalendarFormatter.kt)                  |
| `java.util.Date`              | [org.valiktor.i18n.formatters.DateFormatter](valiktor-core/src/main/kotlin/org/valiktor/i18n/formatters/DateFormatter.kt)                          |
| `java.time.LocalDate`         | [org.valiktor.i18n.formatters.LocalDateFormatter](valiktor-javatime/src/main/kotlin/org/valiktor/i18n/formatters/LocalDateFormatter.kt)            |
| `java.time.LocalTime`         | [org.valiktor.i18n.formatters.LocalTimeFormatter](valiktor-javatime/src/main/kotlin/org/valiktor/i18n/formatters/LocalTimeFormatter.kt)            |
| `java.time.LocalDateTime`     | [org.valiktor.i18n.formatters.LocalDateTimeFormatter](valiktor-javatime/src/main/kotlin/org/valiktor/i18n/formatters/LocalDateTimeFormatter.kt)    |
| `java.time.OffsetTime`        | [org.valiktor.i18n.formatters.OffsetTimeFormatter](valiktor-javatime/src/main/kotlin/org/valiktor/i18n/formatters/OffsetTimeFormatter.kt)          |
| `java.time.OffsetDateTime`    | [org.valiktor.i18n.formatters.OffsetDateTimeFormatter](valiktor-javatime/src/main/kotlin/org/valiktor/i18n/formatters/OffsetDateTimeFormatter.kt)  |
| `java.time.ZonedDateTime`     | [org.valiktor.i18n.formatters.ZonedDateTimeFormatter](valiktor-javatime/src/main/kotlin/org/valiktor/i18n/formatters/ZonedDateTimeFormatter.kt)    |
| `javax.money.MonetaryAmount`  | [org.valiktor.i18n.formatters.MonetaryAmountFormatter](valiktor-javamoney/src/main/kotlin/org/valiktor/i18n/formatters/MonetaryAmountFormatter.kt) |

#### Creating a custom formatter

Creating a custom formatter is very simple, just implement the interface `org.valiktor.i18n.Formatter`, like this:

```kotlin
object CustomFormatter : Formatter<Custom> {
    override fun format(value: Custom, messageBundle: MessageBundle) = value.toString()
}
```

Then add it to the list of formatters (`org.valiktor.i18n.Formatters`):

```kotlin
Formatters[Custom::class] = CustomFormatter
```

It's also possible to use the SPI (Service Provider Interface) provided by Valiktor using the `java.util.ServiceLoader` to discover the formatters automatically without adding them to the list programmatically. For this approach, it's necessary to implement the interface `org.valiktor.i18n.FormatterSpi`, like this:

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

See the [sample](valiktor-samples/valiktor-sample-custom-formatter)

### Creating a custom validation

Valiktor provides a lot of constraints and validation functions for the most common types, but in some cases this is not enough to meet all needs.

It's possible to create custom validations in three steps:

#### 1. Define the constraint 

To create a custom constraint, it's necessary to implement the interface `org.valiktor.Constraint`, which has these properties:

* `name`: specifies the name of the constraint, the default value is the class name, e.g.: `Between`.
* `messageBundle`: specifies the base name of the default message property file, the default value is `org/valiktor/messages`.
* `messageKey`: specifies the name of the key in the message property file, the default value is the qualified class name plus `message` suffix, e.g.: `org.valiktor.constraints.Between.message`.
* `messageParams`: specifies a `Map<String, *>` containing the parameters to be replaced in the message, the default values are all class properties, obtained through reflection.

For example:

```kotlin
data class Between<T>(val start: T, val end: T) : Constraint
```

#### 2. Create the extension function

The validation logic must be within an extension function of `org.valiktor.Validator<E>.Property<T>`, where `E` represents the object and `T` represents the property to be validated.

There is an auxiliary function named `validate` that expects a `Constraint` and a validation function as parameters.

For example:

```kotlin
fun <E> Validator<E>.Property<Int?>.isBetween(start: Int, end: Int) = 
    this.validate(Between(start, end)) { it == null || it in start.rangeTo(end) }
```

And to use it:

```kotlin
validate(employee) {
    validate(Employee::age).isBetween(start = 1, end = 99)
}
```

Note: null properties are valid (`it == null || ...`), this is the default behavior for all Valiktor functions. If the property is nullable and cannot be null, the function `isNotNull()` should be used.

#### 3. Add the internationalization messages

Add internationalization support for the custom constraint is very simple. Just add a message to each message bundle file.

For example:

* `en` (e.g.: `messages_en.properties`):

```
org.valiktor.constraints.Between.message=Must be between {start} and {end}
```

* `pt_BR` (e.g.: `messages_pt_BR.properties`):

```
org.valiktor.constraints.Between.message=Deve estar entre {start} e {end}
```

Note: the variables `start` and `end` are extracted through the property `messageParams` of the constraint `Between` and will be formatted in the message using the [Message formatters](#message-formatters). If you need a custom formatter, see [Creating a custom formatter](#creating-a-custom-formatter).

See the [sample](valiktor-samples/valiktor-sample-custom-constraint)

### Validating RESTful APIs

Implementing validation on REST APIs is not always so easy, so developers end up not doing it right. But the fact is that validations are extremely important to maintaining the integrity and consistency of the API, as well as maintaining the responses clear by helping the client identifying and fixing the issues.

#### Spring support

Valiktor provides integration with Spring WebMvc and Spring WebFlux (reactive approach) to make validating easier. The module `valiktor-spring` has four exception handlers:

Spring WebMvc:

* `ConstraintViolationExceptionHandler`: handles `ConstraintViolationException` from `valiktor-core`.
* `InvalidFormatExceptionHandler`: handles `InvalidFormatException` from `jackson-databind`.
* `MissingKotlinParameterExceptionHandler`: handles `MissingKotlinParameterException` from `jackson-module-kotlin`.

Spring WebFlux:

* `ReactiveConstraintViolationExceptionHandler`: handles `ConstraintViolationException` from `valiktor-core`.
* `ReactiveInvalidFormatExceptionHandler`: handles `InvalidFormatException` from `jackson-databind`.
* `ReactiveMissingKotlinParameterExceptionHandler`: handles `MissingKotlinParameterException` from `jackson-module-kotlin`.

All the exception handlers return the status code `422` ([Unprocessable Entity](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/422)) with the violated constraints in the following payload format:

```json
{
  "errors": [
    {
      "property": "the invalid property name",
      "value": "the invalid value",
      "message": "the internationalization message",
      "constraint": {
        "name": "the constraint name",
        "params": [
          {
            "name": "the param name",
            "value": "the param value"
          }
        ]
      }
    }
  ]
}
```

Valiktor also use the Spring Locale Resolver to determine the locale that will be used to translate the internationalization messages.

By default, Spring resolves the locale by getting the HTTP header `Accept-Language`, e.g.: `Accept-Language: en`.

#### Spring WebMvc example

Consider this controller:

```kotlin
@RestController
@RequestMapping("/employees")
class EmployeeController {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody employee: Employee): ResponseEntity<Void> {
        validate(employee) {
            validate(Employee::id).isPositive()
            validate(Employee::name).isNotEmpty()
        }
        return ResponseEntity.created(...).build()
    }
}
```

Now, let's make two invalid requests with `cURL`:

* with `Accept-Language: en`:

```bash
curl --header "Accept-Language: en" \
  --header "Content-Type: application/json" \
  --request POST \ 
  --data '{"id":0,"name":""}' \
  http://localhost:8080/employees
```

Response:

```json
{
  "errors": [
    {
      "property": "id",
      "value": 0,
      "message": "Must be greater than 0",
      "constraint": {
        "name": "Greater",
        "params": [
          {
            "name": "value",
            "value": 0
          }
        ]
      }
    },
    {
      "property": "name",
      "value": "",
      "message": "Must not be empty",
      "constraint": {
        "name": "NotEmpty",
        "params": []
      }
    }
  ]
}
```

* with `Accept-Language: pt-BR`:

```bash
curl --header "Accept-Language: pt-BR" \
  --header "Content-Type: application/json" \  
  --request POST \ 
  --data '{"id":0,"name":""}' \
  http://localhost:8080/employees
```

Response:

```json
{
  "errors": [
    {
      "property": "id",
      "value": 0,
      "message": "Deve ser maior que 0",
      "constraint": {
        "name": "Greater",
        "params": [
          {
            "name": "value",
            "value": 0
          }
        ]
      }
    },
    {
      "property": "name",
      "value": "",
      "message": "Não deve ser vazio",
      "constraint": {
        "name": "NotEmpty",
        "params": []
      }
    }
  ]
}
```

Samples: 

* [valiktor-sample-spring-boot-1-webmvc](valiktor-samples/valiktor-sample-spring-boot-1-webmvc)
* [valiktor-sample-spring-boot-2-webmvc](valiktor-samples/valiktor-sample-spring-boot-2-webmvc)

#### Spring WebFlux example

Consider this router using [Kotlin DSL](https://spring.io/blog/2017/08/01/spring-framework-5-kotlin-apis-the-functional-way#functional-routing-with-the-kotlin-dsl-for-spring-webflux):

```kotlin
@Bean
fun router() = router {
    accept(MediaType.APPLICATION_JSON).nest {
        "/employees".nest {
            POST("/") { req ->
                req.bodyToMono(Employee::class.java)
                    .map {
                        validate(it) {
                            validate(Employee::id).isPositive()
                            validate(Employee::name).isNotEmpty()
                        }
                    }
                    .flatMap {
                        ServerResponse.created(...).build()
                    }
            }
        }
    }
}
```

Now, let's make two invalid requests with `cURL`:

* with `Accept-Language: en`:

```bash
curl --header "Accept-Language: en" \
  --header "Content-Type: application/json" \
  --request POST \ 
  --data '{"id":0,"name":""}' \
  http://localhost:8080/employees
```

Response:

```json
{
  "errors": [
    {
      "property": "id",
      "value": 0,
      "message": "Must be greater than 0",
      "constraint": {
        "name": "Greater",
        "params": [
          {
            "name": "value",
            "value": 0
          }
        ]
      }
    },
    {
      "property": "name",
      "value": "",
      "message": "Must not be empty",
      "constraint": {
        "name": "NotEmpty",
        "params": []
      }
    }
  ]
}
```

* with `Accept-Language: pt-BR`:

```bash
curl --header "Accept-Language: pt-BR" \
  --header "Content-Type: application/json" \  
  --request POST \ 
  --data '{"id":0,"name":""}' \
  http://localhost:8080/employees
```

Response:

```json
{
  "errors": [
    {
      "property": "id",
      "value": 0,
      "message": "Deve ser maior que 0",
      "constraint": {
        "name": "Greater",
        "params": [
          {
            "name": "value",
            "value": 0
          }
        ]
      }
    },
    {
      "property": "name",
      "value": "",
      "message": "Não deve ser vazio",
      "constraint": {
        "name": "NotEmpty",
        "params": []
      }
    }
  ]
}
```

Samples:

* [valiktor-sample-spring-boot-2-webflux-controller](valiktor-samples/valiktor-sample-spring-boot-2-webflux-controller)
* [valiktor-sample-spring-boot-2-webflux-functional](valiktor-samples/valiktor-sample-spring-boot-2-webflux-functional)

#### Spring Boot support

For Spring Boot applications, the module `valiktor-spring-boot-starter` provides auto-configuration support for the exception handlers and property support for configuration.

Currently the following properties can be configured:

| Property                | Description                                         |
| ------------------------| --------------------------------------------------- |
| valiktor.baseBundleName | The base bundle name containing the custom messages |

Example with `YAML` format:

```yaml
valiktor:
  base-bundle-name: messages
```

Example with `Properties` format:

```properties
valiktor.baseBundleName=messages
```

## Modules

There are a number of modules in Valiktor, here is a quick overview:

### valiktor-core

[![jar](https://img.shields.io/badge/jar-v0.5.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-core/0.5.0/jar)
[![javadoc](https://img.shields.io/badge/javadoc-v0.5.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-core/0.5.0/javadoc)
[![sources](https://img.shields.io/badge/sources-v0.5.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-core/0.5.0/sources)

The main library providing the validation engine, including:

* 40+ validation constraints
* 200+ validation functions for all standard Kotlin/Java types
* Internationalization support
* Default formatters for array, collection, date and number types

### valiktor-javamoney

[![jar](https://img.shields.io/badge/jar-v0.5.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javamoney/0.5.0/jar) 
[![javadoc](https://img.shields.io/badge/javadoc-v0.5.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javamoney/0.5.0/javadoc) 
[![sources](https://img.shields.io/badge/sources-v0.5.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javamoney/0.5.0/sources)

This module provides support for JavaMoney API types, including: 

* Validation constraints and functions for `MonetaryAmount`
* Default formatter for `MonetaryAmount`

### valiktor-javatime

[![jar](https://img.shields.io/badge/jar-v0.5.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javatime/0.5.0/jar)
[![javadoc](https://img.shields.io/badge/javadoc-v0.5.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javatime/0.5.0/javadoc)
[![sources](https://img.shields.io/badge/sources-v0.5.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javatime/0.5.0/sources)

This module provides support for JavaTime API types, including: 

* Validation constraints and functions for `LocalDate`, `LocalDateTime`, `OffsetDateTime` and `ZonedDateTime`
* Default formatter for all `LocalDate`, `LocalDateTime`, `LocalTime`, `OffsetDateTime`, `OffsetTime` and `ZonedDateTime`

### valiktor-spring

[![jar](https://img.shields.io/badge/jar-v0.5.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring/0.5.0/jar)
[![javadoc](https://img.shields.io/badge/javadoc-v0.5.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring/0.5.0/javadoc)
[![sources](https://img.shields.io/badge/sources-v0.5.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring/0.5.0/sources)

Spring WebMvc and WebFlux integration, including:

* Configuration class to set a custom base bundle name
* Exception handler for `ConstraintViolationException` from Valiktor
* Exception handlers for `InvalidFormatException` and `MissingKotlinParameterException` from Jackson

### valiktor-spring-boot-autoconfigure

[![jar](https://img.shields.io/badge/jar-v0.5.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-autoconfigure/0.5.0/jar)
[![javadoc](https://img.shields.io/badge/javadoc-v0.5.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-autoconfigure/0.5.0/javadoc)
[![sources](https://img.shields.io/badge/sources-v0.5.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-autoconfigure/0.5.0/sources)

Provides auto-configuration support for [valiktor-spring](#valiktor-spring), including:
 
* Configuration class based on properties
* Spring WebMvc exception handlers
* Spring WebFlux exception handlers

### valiktor-spring-boot-starter

[![jar](https://img.shields.io/badge/jar-v0.5.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-starter/0.5.0/jar)
[![javadoc](https://img.shields.io/badge/javadoc-v0.5.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-starter/0.5.0/javadoc)
[![sources](https://img.shields.io/badge/sources-v0.5.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-starter/0.5.0/sources)

Spring Boot Starter library including the modules [valiktor-spring](#valiktor-spring) and [valiktor-spring-boot-autoconfigure](#valiktor-spring-boot-autoconfigure)

## Samples

* [valiktor-sample-hello-world](valiktor-samples/valiktor-sample-hello-world)
* [valiktor-sample-nested-properties](valiktor-samples/valiktor-sample-nested-properties)
* [valiktor-sample-collections](valiktor-samples/valiktor-sample-collections)
* [valiktor-sample-javamoney](valiktor-samples/valiktor-sample-javamoney)
* [valiktor-sample-javatime](valiktor-samples/valiktor-sample-javatime)
* [valiktor-sample-custom-constraint](valiktor-samples/valiktor-sample-custom-constraint)
* [valiktor-sample-custom-formatter](valiktor-samples/valiktor-sample-custom-formatter)
* [valiktor-sample-spring-boot-1-webmvc](valiktor-samples/valiktor-sample-spring-boot-1-webmvc)
* [valiktor-sample-spring-boot-2-webmvc](valiktor-samples/valiktor-sample-spring-boot-2-webmvc)
* [valiktor-sample-spring-boot-2-webflux-controller](valiktor-samples/valiktor-sample-spring-boot-2-webflux-controller)
* [valiktor-sample-spring-boot-2-webflux-functional](valiktor-samples/valiktor-sample-spring-boot-2-webflux-functional)

## Changelog

For latest updates see [CHANGELOG.md](CHANGELOG.md) file.

## Contributing 

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for more details, and the process for submitting pull requests to us.

## License

This project is licensed under the Apache License, Version 2.0 - see the [LICENSE](LICENSE) file for details.