```
____             _                     _       _____ _           _ _                       
|  _ \           | |                   | |     / ____| |         | | |                      
| |_) | __ _  ___| | ___ __   __ _  ___| | __ | |    | |__   __ _| | | ___ _ __   __ _  ___
|  _ < / _` |/ __| |/ / '_ \ / _` |/ __| |/ / | |    | '_ \ / _` | | |/ _ \ '_ \ / _` |/ _ \
| |_) | (_| | (__|   <| |_) | (_| | (__|   <  | |____| | | | (_| | | |  __/ | | | (_| |  __/
|____/ \__,_|\___|_|\_\ .__/ \__,_|\___|_|\_\  \_____|_| |_|\__,_|_|_|\___|_| |_|\__, |\___|
                      | |                                                         __/ |     
                      |_|                                                        |___/      
```

[![Build Status](https://github.com/aabarmin/backpack-coding-challenge/actions/workflows/build.yml/badge.svg)](https://github.com/aabarmin/backpack-coding-challenge/actions/workflows/build.yml) 
[![Check Style](https://github.com/aabarmin/backpack-coding-challenge/actions/workflows/check-style.yml/badge.svg)](https://github.com/aabarmin/backpack-coding-challenge/actions/workflows/check-style.yml)
[![Publish package to GitHub Packages](https://github.com/aabarmin/backpack-coding-challenge/actions/workflows/release.yml/badge.svg)](https://github.com/aabarmin/backpack-coding-challenge/actions/workflows/release.yml)

# Project Structure

The project consists of two parts: 

* `packing-lib` - a module that provides a library for solving the [knapsack problem](https://en.wikipedia.org/wiki/Knapsack_problem). 
  This module intentionally built as a separate component to be used as a dependency.
* `packing-app` - a module that uses the `packing-lib` in order to solve the knapsack problem loading data from the
file and providing output to the stdout.
  
The project can be built by using both Apache Maven and Gradle, `pom.xml` and `build.gradle` files provide the same
dependencies and capabilities. In order to build the project using one or another tool, use `./check_maven.sh` or
`./check_gradle.sh` respectively.

Also, the project has GitHub Actions configured using the `./github/workflows/build.yml` file.

# Building the Project Locally

In order to build the project simply execute the following command: 

```shell
$ ./check_all.sh
```

# Using the Knapsack Problem Solver

The main class which solves the knapsack problem is `KnapsackProblemSolver`. Instance of this class can be created
manually by providing all the dependencies into the constructor:

```java
final KnapsackProblemSolver solver = new KnapsackProblemSolver(
    new PackingRequestReaderImpl(
        new RequestLineReader(
            new MoneyReader()
        )
    ),
    new PackingRequestValidator(),
    new PackingSolverImpl(
        new DynamicPackingSolvingStrategy()
    ),
    new StringPackingResultWriter()
);
```

Another, more convenient and recommended approach is to use `KnapsackProblemSolverBuilder` in the following manner: 

```java
final KnapsackProblemSolver solver = KnapsackProblemSolverBuilder.builder()
    .withPathReader()
    .withStringWriter()
    .withDynamicZeroOneStrategy()
    .build();
```

The library doesn't provide any integration with well-known IoC and DI frameworks like Spring Framework or Google Guice, 
it's pure-java library. 

# Adding the Knapsack Problem Solver as Apache Maven or Gradle dependency

The `packer-lib` is available on GitHub Packages and can be added as dependency to your Apache Maven or Gradle project.
Add the following dependency to your `pom.xml` to add as Apache Maven dependency: 

```xml
<dependency>
  <groupId>com.mobiquity</groupId>
  <artifactId>packer-lib</artifactId>
  <version>${library.version}</version>
</dependency>
```

Add the following to the `build.gradle`: 

```groovy
dependencies {
  implementation 'com.mobiquity:packer-lib:${library.version}'
}
```

# Configured GitHub Actions

There are three GitHub Actions workflows configured in the project: 

* `.github/workflows/build.yml` to check every commit. 
* `.github/workflows/release.yml` to deploy artifacts to GitHub Packages when a new GitHub release is created. 
* `.github/workflows/check-style.yml` to check code style of every commit.
