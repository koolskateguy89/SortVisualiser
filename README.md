# SortVisualiser

[![Codacy Badge](https://app.codacy.com/project/badge/Grade/7f1307d0b35e4fa48c793b57a9c4217d)](https://www.codacy.com/gh/koolskateguy89/SortVisualiser/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=koolskateguy89/SortVisualiser&amp;utm_campaign=Badge_Grade)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/koolskateguy89/SortVisualiser.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/koolskateguy89/SortVisualiser/alerts/)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/koolskateguy89/SortVisualiser.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/koolskateguy89/SortVisualiser/context:java)
[![GitHub](https://img.shields.io/github/license/koolskateguy89/SortVisualiser)](LICENSE)

## Table of Contents

- [Introduction](#introduction)
- [Known Problems](#known-problems)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installing](#installing)
    - [Building](#building-using-maven)
    - [Running](#running)
- [License](#license)

## Introduction

This is an application that displays the sorting of a list of rectangles according to their heights*, using the following sorts:
- Bubble Sort
- Quick Sort (needs to be fixed)
- Merge Sort (will be added soon)
- Insertion Sort
- Selection Sort

\* simulating sorting a list of doubles

<sub><sup>
Currently sitting on 1562 lines & 1184 loc
</sup></sub>

## Known Problems

1. Merge Sort does not happen

2. Quick Sort is broken:
    1. It usually won't sort random lists fully - the list will be almost sorted but not quite

    2. Quick Sort on a long _in reverse order_ list will be **EXTREMELY** slow

3. Stop button does not function properly

## Getting Started

### Prerequisites

-  [Java 14](https://www.oracle.com/uk/java/technologies/javase-downloads.html)
-  [JavaFX 15](https://openjfx.io/)

### Installing

Clone this repository:
```
git clone https://github.com/koolskateguy89/SortVisualiser.git
```

### Building using Maven

You can build this application using [Maven](https://maven.apache.org). Inside the repository folder, run the command:
```
mvn package
```

### Running

1. Once Maven has finished building, navigate to the `target` folder
2. Run the command `java -jar "Sort-Visualiser.jar"`

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
