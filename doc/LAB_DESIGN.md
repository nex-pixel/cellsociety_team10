# Cell Society Design Discussion
### Team Number: 10
### Names:
Ryleigh Byrne (rmb96)
Haseeb Chaudhry (hac21)
Young Jun (yj112)
Norah Tan (xt22)


### Discussion Questions

* How does a Cell know what rules to apply for its simulation?

The cell would know what rule to apply for its simulation based on what simulation is being run and whenever the update function is called it will update the simulation based on the hard-coded rule.

* How does a Cell know about its neighbors?

Each Cell will has an instance variable of type HashMap<Integer, Cell> myNeighbors, where the key ranges from 0 to 7 corresponds to each neighbor position (0 represents upper left corner, increasing clockwise)

* How can a Cell update itself without affecting its neighbors update?

The cell would update itself by having the gameModel apply determine what state the cell should be in by using method observe (looking at cell data e.g. neighbors) and then call method change (game rule applied) to update the cell

* What behaviors does the Grid itself have?

The Grid can return the Cell at a specific location

* How can a Grid update all the Cells it contains?

First iterate over each Cell in Grid and call observe(). Then iterate over each Cell in Grid again and call change().

* How is configuration information used to set up a simulation?

The Grid will be initialized to have the dimensions indicated in the .csv file. Each of the Cell in Grid will be initialized according to the status given in the .csv file.

* How is the GUI updated after all the cells have been updated?

View will first call myGame.initialize(CSV_filename) and then be able to access myGame.getGrid()

### Alternate Designs

#### Design Idea #1

* Data Structure #1 and File Format #1

* Data Structure #2 and File Format #2


### CRC Card Classes

This class's purpose or value is to represent a customer's order:
![Order Class CRC Card](images/order_crc_card.png "Order Class")

View:


This class's purpose is to create buttons that will have various actions

|ButtonView| |
|---|---|
|Button makeButton(EventHandler event)         | |
|buttonTriggered()		|SimulatorView|


This class’s purpose is to create a scene for the game and create a simulation

|SimulatorView| |
|---|---|
|Scene createSimulatorScene         |Model|
|void step()         |Model.Update()|

This class’s purpose is to display the buttons and the simulations of the games

|WindowView| |
|---|---|
|void step()         |SimulatorView.step()|
|Scene gameDisplay() |ButtonView |

Controller:

This class's purpose is update the cell’s status based on model/view

|CellController| |
|---|---|
|Point updateLocation         |Controller|
|int getCellStatus    		 |Controller|
|changeCellStatusOnClick    |Controller|

This abstract class’s purpose is to receive and communicate button/click actions from view to model

|ActionController| |
|---|---|
|abstract actionTriggered()	| |
|abstract updateModel()		| |

This class’s purpose is to update model when simulation is pause
|PauseController extends ActionController|
|@override actionTriggered(boolean true/false)	|	|
|@override updateModel()				|Model.update(false)|


This class’s purpose is to update model when simulation is resumed
|PauseController extends ActionController|
|@override actionTriggered(boolean true/false)	|	|
|@override updateModel()				|Model.update(true)|

This class’s purpose is to update model when simulation is resumed
|ResumeController extends ActionController|
|@override actionTriggered(boolean true/false)	|	|
|@override updateModel()				|Model.update(true)|


This class’s purpose is to update model when simulation should be stepped through
|StepController extends ActionController|
|@override actionTriggered(boolean true/false)	|	|
|@override updateModel()				|Model.update(true)|

Model:

(TBD) This class’s purpose is to create the grid for the game by storing new cells and xy positions

|Grid| |
|---|---|
|		| |


(TBD) This class’s purpose is to display the buttons and the simulations of the games

|Cell| |
|---|---|
|void step()         |SimulatorView.step()|


(TBD) This class’s purpose is to simulate the evolution of the game

|Game| |
|---|---|
|void step()         |SimulatorView.step()|



(TBD) This class extends Game class and it’s specific to the Game of Life application

|GameOfLife| |
|---|---|
|void step()         |SimulatorView.step()|




This class's purpose or value is to represent a customer's order:
```java
public class Order {
    // returns whether or not the given items are available to order
    public boolean isInStock (OrderLine items)
    // sums the price of all the given items
    public double getTotalPrice (OrderLine items)
    // returns whether or not the customer's payment is valid
    public boolean isValidPayment (Customer customer)
    // dispatches the items to be ordered to the customer's selected address
    public void deliverTo (OrderLine items, Customer customer)
}
```


### Use Cases

* Apply the rules to a cell: set the next state of a cell to dead by counting its number of neighbors using the Game of Life rules for a cell in the middle (i.e., with all of its neighbors)
```java
public void gameupdate(Grid){
for(each cell: Grid){
int state = observe(cell);
update(state, cell);
	}
}

public int observe(cell){
	ask cell what it’s neighbor’s are upto
	use ALGORITHM to determine new state
	return new state
}
```

* Move to the next generation: update all cells in a simulation from their current state to their next state
```java
Game.update (){
for (Cell c: myGrid.values()) c.observe();
for (Cell c: myGrid.values()) c.changeStatus();
}
```

* Switch simulations: load a new simulation from a data file, replacing the current running simulation with the newly loaded one
```java
SimulatorView newSimulation = new SimulatorView(filePath);
simulationList.set(simulationList.get(oldSimulation), newSimulation);
SimulatorView.gameDisplay;
```

File structure: 

Data

Model
resources
State Data:
ALIVE=1
DEAD=0
components
Grid
board = <Point, Cell>
Note: Point class can be imported
Cell = xy-location, {neighborCells}, isEdge, currentStatus, nextStatus
observe()
changeStatus()
getStatus()
ReadFile(abstract)
ReadCSVFile(file)
read(file)
ReadJSONFile(){
simulations
Game(abstract)
ReadFile myReader;
Grid myGrid;
initialize(file)
myReader = new Read____File()
look at the .tag and call the correct subclass
2D array = myReader.read(file)
myGrid = createGrid(2D array)
Private method createGrid
GameOfLifeModel (child): myGrid
game.update()
cell.observe()
cell.changeStatus()

Controller
Grid
File Controller
Cells = neighborCells, status (color) , location
changeCellOnClick()
ActionController
Button Actions
updateModel()
Tells model to pause, resume, or step through
loadNewConfigurationFile()
Send new file to model

View

SimulatorView
Simulator Information
getInformation()
ActionView
Buttons
Pause(), resume(), stepThrough(), speedUp(), slowDown()
Will send action to controller that will then signal to model update() (or not update, etc)
javaFX step(time)


Main Menu
Select Program
getNewConfigurationFileName()
Select Language
Select Color/Font Scheme
Running more than one model
