## Lab Discussion
### Team 10
### Names: hac21, rmb96, yj112, xt22


### Issues in Current Code

* Long method that goes beyond 20 lines of code
* Fix issue with encapsulation and making use of getter methods instead of passing the data structure we are using
* Fix some issues with testing using public methods by changing the methods in a class to private and making use of another format to test such as .equals


#### Method or Class
* Design issues

Grid class: The design issue with this class was encapsulation. We were passing up the myBoard data structure which was a hashmap. Instead we used encapsulation and changed the code to look at specific x and y position passing back the cell for it instead.

* Design issue

GameOfLifeMode: The design issue with this class was encapsulation again. We were doing testing using public toGridArray instead of using the actual structure of the class to create an object and compare it to the expected object. We were also directly grabbing the myBoard in this class breaking encapsulation which we changed to instead use x y locations and asks for cell objects.

#### Method or Class
* Design issues

SimulatorController: The design issue with this class is that it uses switch cases to select which game simulation to create. Fixing this issue will require the use of reflection. We will add properties files and create a method for each game. Then, we will call this method directly rather than using switch statement.

* Design issue

Overall our view classes use buttons to implement a large portion of our functionality. In order to make this more flexible, we will implement a button view super class to generate buttons for both the main menu and the simulator view. The classes will use reflection to generate buttons from a resource file so new buttons and functionalities can be added easily.

### Refactoring Plan

* What are the code's biggest issues?

The biggest issue with the code is having encapsulation. We have code that is incorrectly using the instance variables, instead of grabbing the actual objects or telling the class to give us the property we want to know. Another large issue with the code is that we were making no use of reflection.

* Which issues are easy to fix and which are hard?

The easy to fix issue was again the encapsulation as explained before due to how we just needed to shift the nature of it. The hard to fix issue was reflection. This was a new concept, and required a more comprehensive understanding of the program.

* What is your plan to implement the changes without losing control of the process?

To implement the changes we would most likely create more abstractions such as for the Grid object and have a squareGrid, triangleGrid, and hexagonalGrid.
