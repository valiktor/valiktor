# Contributing

When contributing to this repository, please first discuss the change you wish to make via issue,
email, or any other method with the owners of this repository before making a change.

Please note we have a [code of conduct](CODE_OF_CONDUCT.md), please follow it in all your interactions with the project.

## Creating an Issue

Remember to include enough information if you're reporting a bug.
Creating an issue to ask a question is fine.

### Bug Report

* Use [this template](.github/ISSUE_TEMPLATE/BUG_REPORT.md)
* Complete all the necessary information

### Feature Request

* Use [this template](.github/ISSUE_TEMPLATE/FEATURE_REQUEST.md)
* Complete all the necessary information

## Creating a Pull Request

We actively welcome your pull requests.

1. Ask about the feature beforehand (or pick one of the open issues)
2. If no issue exists, create an issue for the PR
3. Fork the repo and create your branch from `master`
4. If you've added code that should be tested, add tests
5. If you've changed APIs, update the documentation
6. Ensure the test suite passes
7. Make sure your code lints

## Running Tests

`./gradlew test`

## Building Project Locally

`./gradlew build`

## Continuous Integration

We use two CI tools to build this project for each commit pushed:

* [Travis](https://travis-ci.org/valiktor/valiktor) for Linux environment
* [AppVeyor](https://ci.appveyor.com/project/rodolphocouto/valiktor) for Windows environment

Both tools build the project and run all the tests for these Java environments:

* JDK 8
* JDK 9
* JDK 10
* JDK 11

## License
By contributing to Valiktor, you agree that your contributions will be licensed under its [Apache License, Version 2.0](LICENSE).