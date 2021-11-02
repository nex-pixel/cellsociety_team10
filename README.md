Cell Society
====

This project implements a cellular automata simulator.

Names: Haseeb Chaudhry, Young Jun, Norah Tan, Ryleigh Byrne



### Timeline

Start Date: 10/11/2021

Finish Date: 11/2/2021

Hours Spent: 150 Hours total. 12-14 hours per person per week.


### Primary Roles

Haseeb Chaudhry - Backend Implementation:Components, Games; Backend Testing, Documentation, Example File creation, Everything related to backend, Algorithm, Data handling, Structure

Norah Tan - Backend Implementation:Components, Games; Backend Testing, Documentation, Example File creation, Everything related to backend, Algorithm, Data handling, Structure

Ryleigh Byrne - Frontend Implementation: Controller and View classes, refactoring, algorithm, reflection - everything related to backend.

Young Jun - Frontend Implementation: Controller and View classes, refactoring, algorithm, reflection, testing - everything related to backend.


### Resources Used

Stack Overflow Website - provided example of implementation of different features


### Running the Program

Main class: Chooses the language to run the program and instantiates the MainController. The role of Main is kept limited to instantiating the MainController. Running the program is controlled by the controllers.


Data files needed: Data files to run the simulation are dependent on the user however a data file needs to be input in order to run any simulation. Users can create their own data file to input but formatting requires special attention. Outside of the data files, all listed resource files are needed.

Features implemented:
- Run simulation of Game of life
- Run simulation of Spreading Fire
- Run simulation of Segregation
- Run simulation of Percolation
- Run simulation of Wator World
- Create a Grid made of Hexagons, Squares or Triangles
- Stop, Play, Step, Slow down, or Speed up the simulation
- Load multiple simulations
- Replace the current simulation with new simulation
- Show information about the simulation
- Have creation of random csv files and simulation possible
- Change languages
- Change edge policy
- Change neighbor mode
- Change themes
- Read in different file types
- Click on cells to change states




### Notes/Assumptions

Assumptions or Simplifications:

- A specific type of file needs to be input in specific format
- Grid is 2D array only
- Regular shapes as defined by Grid Subclasses are used
- User has certain screen format


Interesting data files:

- GameOfLife: Penta-Decathlon, glider
- SpreadingFire: Multiple Burning Trees sparse forest, one burning tree full forest
- WatorWorld: full sea shark fish, one shark fish world
- Segregation: Random test one
- Percolation: Volcano 

Known Bugs:

- Clicking on certain cells changes other cells in Hexagon grid
- Sometimes the load file doesn’t work because it thinks user has chosen all settings


Noteworthy Features:

- Users can create multiple simulations.
- Users can ultra slow down their simulation
- Clicking on cells is dynamic and create unexpected simulation branch off behaviour
- Grid Expansion in SpreadingFire works (if more time existed could be implemented in creating an infinite grid)


### Impressions

The assignment overall was fantastic. It had a certain simplicity to it that oolala didn't initially offer since this required a more data structure handling and abstraction and data manipulation approach which oolala didn’t. However, it did pose challenges such as the number of features required in the amount of time given. They could not be fully tested after implementation due to how short time was given to implement them. Also, the better we got in encapsulation, reflection, lambdas, and other code design features, the harder it got to test frontend methods. Hence, we lack in the number of tests testing the frontend features. The extra challenge of implementing the reflection, lambdas, and interfaces required a lot more extra work. There should be a session for these projects on Saturday where we can get help from a TA. However, we think this project went fantastically compared to oolala since now we had a better grasp of full project implementation.

