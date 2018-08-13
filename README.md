# Valiktor

> Valiktor is a type-safe, powerful and extensible fluent DSL to validate objects in Kotlin.

[![Build Status](https://travis-ci.org/valiktor/valiktor.svg?branch=master)](https://travis-ci.org/valiktor/valiktor)
[![Coverage Status](https://codecov.io/gh/valiktor/valiktor/branch/master/graph/badge.svg)](https://codecov.io/gh/valiktor/valiktor)
[![Quality Status](https://api.codacy.com/project/badge/Grade/1826622893374838856952b9c013793a)](https://www.codacy.com/app/rodolphocouto/valiktor?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=valiktor/valiktor&amp;utm_campaign=Badge_Grade)
[![Maven Central](https://img.shields.io/maven-central/v/org.valiktor/valiktor-core.svg)](https://search.maven.org/search?q=g:org.valiktor)
[![Apache License](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg)](LICENSE)

Example:

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