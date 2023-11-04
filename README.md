# Super Mario Bros Genetic Algorithm Solver

## Introduction
This repository contains our project for the Artificial Intelligence course, focusing on the application of Evolutionary Computing techniques. We have developed a genetic algorithm capable of finding optimal solutions for the classic game Super Mario Bros. The project addresses three gameplay modes: Speed Run, Coin Collection, and High Score, each presenting unique challenges that our algorithm aims to tackle.

## Objectives
The main objectives of this project were to:
- Model the knowledge in the domain of a complex problem and its solution space for computational use.
- Generate, optimize, and evaluate valid solutions for the complex problem of Super Mario Bros using an evolutionary perspective.
- Analytically and critically compare different approaches to select the most appropriate one for problem-solving.
- Iteratively and incrementally improve our approach based on past results.

## Gameplay Modes
- **Speed Run**: The goal is to complete the level as fast as possible.
- **Coin**: The aim is to collect as many coins as possible throughout the level.
- **Score**: The objective is to finish the level with the highest possible score.

## Installation and Setup
To run the Super Mario Bros server:

1. Download the VirtualBox VM backup from the provided link on the course's Moodle page.
2. Import the VM into VirtualBox.
3. Change network settings to "Bridged Adapter" and select your active network card.
4. Launch the VM using the password `leiia22`.
5. Open a terminal instance, run `ifconfig`, and note down the VM's IP address.
6. Navigate to `~/MarioServer` and execute `python mario-server.py`.

The server will listen for POST requests on port 8080 at the noted IP address on the route `/process`.

## Usage
To interact with the server, send a POST request with the `Content-Type: application/json` header and a JSON string in the body with the format specified in the documentation.

The `solution` parameter should be an array of integers, each representing a button or combination of buttons to be pressed at a given time.

Refer to the provided tables for the mappings between integers and button presses, and the details on world/stage selection and ROM versions.

## Fitness Functions
The genetic algorithm uses a customizable fitness function, which can be adjusted using weights for each challenge mode. Alternatively, three separate fitness functions may be implemented for the different gameplay modes.

## Leaderboard
Significant or improved solutions should be submitted to the online leaderboard, which serves both as a validation of progress and as an element of evaluation for the project.

## Resources
The provided `.jar` includes useful classes and a method to send requests to the server (`goMarioGo`) and process the response as a Java class instance. Check the accompanying documentation for a deeper understanding of these resources.

The `GettingStarted` project demonstrates how to generate a random solution and submit it to the server for execution.

## Contribution
This project is the result of collaborative work. Contributions and improvements are welcome through pull requests. Please ensure that any major changes are discussed via issues before submission.

## Team Members
This project is a collaborative effort by a dedicated team:

- **Hugo Silva** - [hugosilva12](https://github.com/hugosilva12) 
- **Luís Oliveira**
- **Sérgio Moreira**


## Acknowledgments
Special thanks to the course instructors and assistants for providing guidance and resources to successfully implement this project.
