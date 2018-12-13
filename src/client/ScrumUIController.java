package client;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class ScrumUIController {
	
	@FXML private Pane notStartedColumn;
	@FXML private Pane inProgressColumn;
	@FXML private Pane completedColumn;
	@FXML private TextField titleInput;
	@FXML private TextField authorInput;
	@FXML private TextField sprintInput;
	@FXML private TextField priorityInput;
	@FXML private TextField storyInput;
	@FXML private TableView<WorkItem> tableView;
	@FXML private TableColumn<?, ?> sprintColumn;
	@FXML private ObservableList<WorkItem> items;
	@FXML private LineChart<Number, Number> burndown;
	@SuppressWarnings("rawtypes")
	@FXML private XYChart.Series burndownSeries = new XYChart.Series();

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML protected void handleCreateButtonAction(ActionEvent event) { //Gathers information, creates item in all views, and closes popup
		//Adds new item to Work Item list
		items = tableView.getItems();
		WorkItem wi = new WorkItem(titleInput.getText(),
    			authorInput.getText(),
    			sprintInput.getText(),
    			priorityInput.getText(),
    			"Not Started",
    			storyInput.getText());
    	items.add(wi);
    	
    	//Resets create work item form 
    	titleInput.setText("");
    	authorInput.setText("");
    	sprintInput.setText("");
    	priorityInput.setText("");
    	storyInput.setText("");
    	
    	//Creates board object
    	GridPane content = new GridPane();
    	content.getRowConstraints().add(new RowConstraints(30));
    	content.getRowConstraints().add(new RowConstraints(30));
    	content.getRowConstraints().add(new RowConstraints(30));
    	content.getColumnConstraints().add(new ColumnConstraints(50));
    	content.getColumnConstraints().add(new ColumnConstraints(50));
    	content.add(new Label("ID:"), 0, 0);
    	content.add(new Text(Integer.toString(items.indexOf(wi)+1)), 1, 0);
    	content.add(new Label("Author:"), 0, 1);
    	content.add(new Text(wi.getAuthor()), 1, 1);
    	content.add(new Label("Sprint"), 0, 2);
    	content.add(new Text(wi.getSprint()), 1, 2);
    	TitledPane newItem = new TitledPane(wi.getTitle(), content);
    	newItem.setStyle("-fx-border-color: red");
    	newItem.setLayoutX(19);
    	newItem.setLayoutY(53 + getNumNS(items)*160);
    	//Sets board object's drag
    	newItem.setOnMouseDragged(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
            	double x = newItem.getTranslateX();
            	double y = newItem.getTranslateY();
                newItem.setManaged(false);
                newItem.setTranslateX(event.getX() + x);
                newItem.setTranslateY(event.getY() + y);
            }
        });
    	newItem.setOnMouseReleased(new EventHandler <MouseEvent>() {
            public void handle(MouseEvent event) {
            	double x = newItem.getTranslateX();
            	if (wi.getStatus() == "Not Started") {
                	notStartedColumn.getChildren().remove(newItem);
            		if (x < 175) {
                    	newItem.setStyle("-fx-border-color: red");
                        newItem.setTranslateX(19);
                        wi.setStatus("Not Started");
                    	notStartedColumn.getChildren().add(newItem);
            		} else if (x < 350) {
            			newItem.setStyle("-fx-border-color: yellow");
                        newItem.setTranslateX(19);
                        wi.setStatus("In Progress");
                    	inProgressColumn.getChildren().add(newItem);
            		} else {
            			newItem.setStyle("-fx-border-color: green");
                        newItem.setTranslateX(19);
                        wi.setStatus("Completed");
                    	completedColumn.getChildren().add(newItem);
            		}
            	} else if (wi.getStatus() == "In Progress") {
                	inProgressColumn.getChildren().remove(newItem);
            		if (x < -19) {
                    	newItem.setStyle("-fx-border-color: red");
                        newItem.setTranslateX(19);
                        wi.setStatus("Not Started");
                    	notStartedColumn.getChildren().add(newItem);
            		} else if (x > 175) {
            			newItem.setStyle("-fx-border-color: green");
                        newItem.setTranslateX(19);
                        wi.setStatus("Completed");
                    	completedColumn.getChildren().add(newItem);
            		} else {
            			newItem.setStyle("-fx-border-color: yellow");
                        newItem.setTranslateX(19);
                        wi.setStatus("In Progress");
                    	inProgressColumn.getChildren().add(newItem);
            		}
            		
            	} else if (wi.getStatus() == "Completed") {
                	completedColumn.getChildren().remove(newItem);
            		if (x < -175) {
                    	newItem.setStyle("-fx-border-color: red");
                        newItem.setTranslateX(19);
                        wi.setStatus("Not Started");
                    	notStartedColumn.getChildren().add(newItem);
            		} else if (x < -19) {
                    	newItem.setStyle("-fx-border-color: yellow");
                        newItem.setTranslateX(19);
                        wi.setStatus("In Progress");
                    	inProgressColumn.getChildren().add(newItem);
            		} else {
            			newItem.setStyle("-fx-border-color: green");
                        newItem.setTranslateX(19);
                        wi.setStatus("Completed");
                    	completedColumn.getChildren().add(newItem);
            		}
            		
            	}
            }
        });
    	notStartedColumn.getChildren().add(newItem);
    	
    	//Add point to graph
    	burndown.setLegendVisible(false);
    	//Remove previous point at that sprint if necessary
    	for (int i = 0; i < burndownSeries.getData().size(); i++) {
    		XYChart.Data d = (XYChart.Data) burndownSeries.getData().get(i);
    		if (d.getXValue() == wi.getSprint())
    			burndownSeries.getData().remove(i);
    	}
    	burndownSeries.getData().add(new XYChart.Data(Integer.parseInt(wi.getSprint()), getNumSprint(items, wi.getSprint())));
    	burndown.getData().add(burndownSeries);
    }
	
	protected int getNumNS(List<WorkItem> items) {
		int count = -1;
		for (WorkItem item : items ) {
			if (item.getStatus() == "Not Started")
				count++;
		}
		return count;
	}
	
	protected int getNumSprint(List<WorkItem> items, String sprint) {
		int count = 0;
		for (int i = 0; i  < items.size(); i++) {
			System.out.println(items.get(i).getSprint());
			if (items.get(i).getSprint() == sprint)
				System.out.println("HERE");
				count++;
		}
		return count;
	}
}
