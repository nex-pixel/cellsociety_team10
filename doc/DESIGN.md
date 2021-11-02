# Cell Society Design Final
### Ryleigh Byrne, Young Jun, Norah Tan, Haseeb Choudary

## Team Roles and Responsibilities

* Team Member #1: Ryleigh Byrne
  Ryleigh primarily worked on the front-end, specifically the view and controller classes. Ryleigh implemented the factory classes, including the buttonFactory,  sliderFactory, choiceDialogBoxFactory, cssFactory super-classes and sub-classes. Ryleigh also designed and refactored the MainMenuView, SimulatorView, SimulatorController, FileManager, and MainController classes. She also handled the implementation of the resource bundles and how they were used both to display text in the UI, and to generate and handle button and choice dialog events. Ryleigh also wrote the ReflectionHandler class to allow both the backend and frontend to utilize reflection.

* Team Member #2: Young Jun
  Young worked on frontend with Ryleigh. His primary focus were Controller classes and View classes. Young created SimulatorView that correctly runs the simulations and has all the necessary features. Young also worked on connecting frontend and backend, a lot of the work includes codes in Controller classes. Also, he came up with the logic of how to loop grids in general including the hexagon grids.  Young also wrote Cell classes and GridBuilder classes that are necessary in creating the simulation.

* Team Member #3: Norah Tan
  Norah worked on the back-end with Haseeb. We designed the basic structure of each component class (Cell, Grid, FileReader and PropertiesReader) and the cornerstone abstract Game class together. More specifically, I primarily wrote Segregation Model and Percolation Model and the sub-classes of Grid. I also wrote many tests for components and games. I wrote the FileReader abstract class and ReadCSVFile.

* Team Member #4: Haseeb Choudary
  Haseeb worked on the back-end with Norah. We designed the overall handling of data that is passed in and how it is acted upon. The backend was designed to function separately from the frontend where there are no dependencies on front but the frontend asks the backend for data. We created the Grid class and its subclasses for different grid shapes. We created the cell class to store data values of each generation. Then created game classes to apply the game rules to the grid of cells by somewhat splitting each core of the game functionality between each other. I wrote GameOfLifeModel, WatorWorldModel, and SpreadingFireModel. I also wrote the PropertiesReader to read in magic values. We also wrote FileReader with me writing ReadTextFile.

## Design goals

#### What Features are Easy to Add
*Easy to add button and error messages: It is very easy to implement new buttons due to the ButtonFactory class. If one wants to add buttons to the main menu, they simply have to add a button label as a key and a method name as the value to  the buttonEvents resource file and add their new method name to the MainMenuButtonFactory class. The same goes for simulator view. It is also easy to generate new error messages to be displayed to the user using our error classes. By adding a new error message key to the resource bundle, one can generate a new error by calling the GenerateError class and passing a language resource bundle and an error message key.

* Easy to add new cell type
  Easy to add new cell types as we have abstract classes Cell and GridBuilder in the frontend. Hence, to add a cell type, we need to create one class that extends Cell and other class that extends GridBuilder. The backend will have to create backend grid that extends abstract class Grid. However, the code will be mostly similar to the current code

## High-level Design
Game.update() contains two step:
first iterate over all Cells and change only their nextStatus according to the rule of the specific game;
then, iterate over all Cells again to change their currentStatus into their nextStatus.
SquareGrid, TriangleGrid and HexagonGrid all extend from the abstract class Grid to implement the three shapes.
Grid.applyEdgePolicy(int x, int y) can easily implement the three different types of edge policies (finite, toroidal, cylindrical), no matter what grid shape the user chooses, by selectively modulo x or y by the size of their rows and columns.
Grid.applyNeighborMode(Point point) can easily implement the three different types of neighbors (complete, on-edge, bottom-half) according to the grid shape. This is done because we defined magic helper arrays according to grid shape and neighbor mode to locate the relative position of all neighbors for a cell.


#### Core Classes
- Error
- ErrorGenerator
- Controller
- FileManager
- MainController
- SimulatorController
- Frontend:
- ButtonFactory (abstract)
- MainMenuButtonFactory
- SimulatorButtonFactory
- ChoiceDialogBoxFactory (abstract)
- MainMenuChoiceDialogBoxFactory
- SimulatorChoiceDialogBoxFactory
- CSSFactory
- SliderFactory
- MainMenuView
- SimulatorView
- Backend:
- Game (abstract)
- GameOfLifeModel
- SpreadingFireModel
- SegregationModel
- WaTorWorldModel
- PercolationModel
- Grid (abstract)
- SquareGrid
- TriangleGrid
- HexagonGrid
- Cell
 -PropertiesReader
- FileReader (abstract)
- ReadCSVFile
- ReadJSONFile
- ReadTextFile
- Main
- ReflectionHandler


## Assumptions that Affect the Design
We are assuming that the user is setting their own cell colors by choosing a CSS file
Type of file (easy to add support for new file - can be improved)
2D grid only
The cell don’t record previous state

#### Features Affected by Assumptions
Simulations can’t go backwards
Game is only 2D displayed
Only a certain file format works for the simulation to run

## Significant differences from Original Plan
* Using controllers
  We did not fully understand the concept of controllers when we started the project. We changed our design so that Main instantiates controller instead of View.

## New Features HowTo
*Infinitely expanding grid : we have a fully working and tested backend code. We will have to add features to our front-end classes

*Random Cells : we have a fully working and tested backend code. We will have to add features to our front-end classes.

#### Easy to Add Features
Simulations with random grids
Simulations pause on initialization
New Game with rules that may require extraneous variable tracking

#### Other Features not yet Done
* Infinite Grid: only finished backend code
* Random Grid: only finished backend code



