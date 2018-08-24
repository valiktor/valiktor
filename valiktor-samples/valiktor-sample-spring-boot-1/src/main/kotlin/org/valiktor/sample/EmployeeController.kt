/*
 * Copyright 2018 https://www.valiktor.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.valiktor.sample

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.MediaType.APPLICATION_XML_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.created
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri

@RestController
@RequestMapping("/employees")
class EmployeeController(val service: EmployeeService) {

    @PostMapping(
        consumes = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE],
        produces = [APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE])
    fun create(@RequestBody employee: Employee): ResponseEntity<Void> {
        service.create(employee)
        return created(fromCurrentRequestUri().path("/{id}").buildAndExpand(employee.id).toUri()).build()
    }
}