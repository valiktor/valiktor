# Valiktor

> Valiktor is a type-safe, powerful and extensible fluent DSL to validate objects in Kotlin.

[![Build Status](https://travis-ci.org/valiktor/valiktor.svg?branch=master)](https://travis-ci.org/valiktor/valiktor)
[![Build status](https://ci.appveyor.com/api/projects/status/github/valiktor/valiktor?branch=master&svg=true)](https://ci.appveyor.com/project/rodolphocouto/valiktor)
[![Coverage Status](https://codecov.io/gh/valiktor/valiktor/branch/master/graph/badge.svg)](https://codecov.io/gh/valiktor/valiktor)
[![Quality Status](https://api.codacy.com/project/badge/Grade/1826622893374838856952b9c013793a)](https://www.codacy.com/app/rodolphocouto/valiktor?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=valiktor/valiktor&amp;utm_campaign=Badge_Grade)
[![Maven Central](https://img.shields.io/maven-central/v/org.valiktor/valiktor-core.svg)](https://search.maven.org/search?q=g:org.valiktor)
[![Apache License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](LICENSE)

## Installation

Gradle (Groovy):

```groovy
compile 'org.valiktor:valiktor-core:0.1.0'
```

Gradle (Kotlin DSL):

```groovy
compile("org.valiktor:valiktor-core:0.1.0")
```

Maven:

```xml
<dependency>
  <groupId>org.valiktor</groupId>
  <artifactId>valiktor-core</artifactId>
  <version>0.1.0</version>
</dependency>
```

* For install other modules, see [Modules](#modules).

## Getting Started

```kotlin
import org.valiktor.validate
import org.valiktor.functions.*

data class Employee(val id: Int, 
                    val name: String, 
                    val email: String) {
    init {
        validate(this) {
            validate(Employee::id).isPositive()
            validate(Employee::name).isNotBlank().hasSize(min = 1, max = 80)
            validate(Employee::email).isNotBlank().hasSize(min = 1, max = 50).isEmail()
        }
    }
}
```

* Validates the properties and throws a exception with constraint violations

## Documentation

Work in progress.

## Modules

| Module                                                                                   | Description                              | Artifacts                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| ---------------------------------------------------------------------------------------- | --------------------------------------   | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [valiktor-core](valiktor-core)                                                           | Core module with engine and i18n support | [![jar](https://img.shields.io/badge/jar-v0.1.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-core/0.1.0/jar) [![javadoc](https://img.shields.io/badge/javadoc-v0.1.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-core/0.1.0/javadoc) [![sources](https://img.shields.io/badge/sources-v0.1.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-core/0.1.0/sources)                                                                |
| [valiktor-javamoney](valiktor-javamoney)                                                 | JavaMoney API support                    | [![jar](https://img.shields.io/badge/jar-v0.1.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javamoney/0.1.0/jar) [![javadoc](https://img.shields.io/badge/javadoc-v0.1.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javamoney/0.1.0/javadoc) [![sources](https://img.shields.io/badge/sources-v0.1.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javamoney/0.1.0/sources)                                                 |
| [valiktor-javatime](valiktor-javatime)                                                   | JavaTime API support                     | [![jar](https://img.shields.io/badge/jar-v0.1.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javatime/0.1.0/jar) [![javadoc](https://img.shields.io/badge/javadoc-v0.1.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javatime/0.1.0/javadoc) [![sources](https://img.shields.io/badge/sources-v0.1.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-javatime/0.1.0/sources)                                                    |
| [valiktor-spring](valiktor-spring/valiktor-spring)                                       | SpringMVC support                        | [![jar](https://img.shields.io/badge/jar-v0.1.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring/0.1.0/jar) [![javadoc](https://img.shields.io/badge/javadoc-v0.1.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring/0.1.0/javadoc) [![sources](https://img.shields.io/badge/sources-v0.1.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring/0.1.0/sources)                                                          |
| [valiktor-spring-boot-autoconfigure](valiktor-spring/valiktor-spring-boot-autoconfigure) | Spring Boot Auto Configuration support   | [![jar](https://img.shields.io/badge/jar-v0.1.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-autoconfigure/0.1.0/jar) [![javadoc](https://img.shields.io/badge/javadoc-v0.1.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-autoconfigure/0.1.0/javadoc) [![sources](https://img.shields.io/badge/sources-v0.1.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-autoconfigure/0.1.0/sources) |
| [valiktor-spring-boot-starter](valiktor-spring/valiktor-spring-boot-starter)             | Spring Boot Starter support              | [![jar](https://img.shields.io/badge/jar-v0.1.0-green.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-starter/0.1.0/jar) [![javadoc](https://img.shields.io/badge/javadoc-v0.1.0-blue.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-starter/0.1.0/javadoc) [![sources](https://img.shields.io/badge/sources-v0.1.0-yellow.svg)](https://search.maven.org/artifact/org.valiktor/valiktor-spring-boot-starter/0.1.0/sources)                   |

## Samples

| Project                                                                         | Description           |
| ------------------------------------------------------------------------------- | --------------------- |
| [valiktor-sample-spring-boot-1](valiktor-samples/valiktor-sample-spring-boot-1) | Spring Boot 1 Example |
| [valiktor-sample-spring-boot-2](valiktor-samples/valiktor-sample-spring-boot-2) | Spring Boot 2 Example |

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](https://github.com/valiktor/valiktor/tags).

## Authors

* **[Rodolpho S. Couto](https://github.com/rodolphocouto)**

## License

This project is licensed under the Apache License, Version 2.0 - see the [LICENSE](LICENSE) file for details.