# SortVisualiser

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/7f1307d0b35e4fa48c793b57a9c4217d)](https://www.codacy.com/gh/koolskateguy89/SortVisualiser/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=koolskateguy89/SortVisualiser&amp;utm_campaign=Badge_Grade)
[![GitHub](https://img.shields.io/github/license/koolskateguy89/SortVisualiser)](LICENSE)

## Table of Contents

- [Introduction](#introduction)
- [Known Problems](#known-problems)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installing](#installing)
    - [Building](#building-using-maven)
    - [Running](#running)
        - [Eclipse](#eclipse)
- [License](#license)

## Introduction

This is an application that visually shows the sorting of a list, using the following sorts:
- Bubble Sort
- Quick Sort (needs to be fixed)
- Merge Sort (will be added soon)
- Insertion Sort
- Selection Sort

<sub><sup>
Currently sitting on 1567 lines & 1257 loc
</sup></sub>

## Known Problems

1. Merge Sort does not happen

2. Quick Sort is broken:
    1. It usually won't sort random lists fully - they'll be almost sorted but not quite
    2. Quick Sort on a long in reverse order list will be **EXTREMELY** slow

3. Stop button does not function properly

## Getting Started

### Prerequisites

-  [Java 14](https://www.oracle.com/uk/java/technologies/javase-downloads.html) (with preview features enabled)
-  [JavaFX 15](https://openjfx.io/)

### Installing

Clone this repository:
```
git clone https://github.com/koolskateguy89/SortVisualiser.git
```

(p.s. if using Eclipse, you can import this as a Java Project, but you will probably have to configure the project build path to include all the prerequisites)

### Building using Maven

You can build this application using [Maven](https://maven.apache.org). Inside the repository folder, run the command:
```
mvn package
```

### Running

If you use Eclipse, see [Eclipse below](#eclipse)

1. Once Maven has finished building, navigate to the `target` folder
2. Run the command `java -jar --enable-preview "Sort-Visualiser-V3.jar"`

#### Eclipse

You can import this repository into Eclipse as a Java Project.

Though to run it, you'll likely have to configure the project build path to ensure it has the required [dependencies](#prerequisites)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
