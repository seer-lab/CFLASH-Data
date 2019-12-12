# <p align="center">CFLASH: Concurrency Faults Localized Automatically using Search Heuristics<br><br>Evaluation Data</p>

<p align="center">
    <img src="https://img.shields.io/github/last-commit/sqrlab/cflash-data.svg?style=flat" alt="last-commit-badge"/>
    <img src="https://img.shields.io/github/issues-raw/sqrlab/cflash-data.svg?style=flat" alt="open-issues-badge"/>
    <img src="https://img.shields.io/github/languages/count/sqrlab/cflash-data.svg?style=flat" alt="languages-badge"/>
    <img src="https://img.shields.io/github/license/sqrlab/cflash-data.svg?style=flat" alt="license-badge"/>
</p>

<br>

This benchmark was created as group of programs to evaluate [CFLASH](https://github.com/sqrlab/cflash) as a tool to automatically localize concurrency bugs in multithreaded programs.



The data was submitted by Ontario Tech Students as part of their laboratory assignments and projects for the [CSCI 4060U: Massively Parallel Programming](http://www.sqrlab.ca/csci4060u/) course. The programs have been anonymized, and formal consent for its use has been granted by the students.



Each project will include a `makefile` under their respective `src/` and `test/` directories, allowing those interested in the benchmark to run and test the programs with ease.

## Description

| Program Name       | LOC  |
| ------------------ | :--- |
| transaction-mech   | 492  |
| banking            | 75   |
| pizza-restaurant   | 156  |
| airplane-ticketing | 93   |
| taxi-dispatcher    | 123  |
| linear-search      | 121  |
| file-search        | 142  |
| account            | 98   |
| parking            | 145  |

For mutation purposes using ConMAn, the bug types used were: `MSP`, `RSK`,` SHCR`,` SKCR` and `SPCR`.