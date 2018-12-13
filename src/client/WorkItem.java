package client;
 
import javafx.beans.property.SimpleStringProperty;
 
public class WorkItem {
   private final SimpleStringProperty title = new SimpleStringProperty("");
   private final SimpleStringProperty author = new SimpleStringProperty("");
   private final SimpleStringProperty sprint = new SimpleStringProperty("");
   private final SimpleStringProperty priority = new SimpleStringProperty("");
   private final SimpleStringProperty status = new SimpleStringProperty("");
   private final SimpleStringProperty story = new SimpleStringProperty("");

public WorkItem() {
        this("", "", "", "", "", "");
    }
 
    public WorkItem(String title, String author, String sprint, String priority, String status, String story) {
        setTitle(title);
        setAuthor(author);
        setSprint(sprint);
        setPriority(priority);
        setStatus(status);
        setStory(story);
    }

    public String getTitle() {
        return title.get();
    }
 
    public void setTitle(String t) {
        title.set(t);
    }
        
    public String getAuthor() {
        return author.get();
    }
    
    public void setAuthor(String a) {
        author.set(a);
    }
    
    public String getSprint() {
        return sprint.get();
    }
    
    public void setSprint(String s) {
        sprint.set(s);
    }
    
    public String getPriority() {
        return priority.get();
    }
    
    public void setPriority(String p) {
        priority.set(p);
    }
    
    public String getStatus() {
        return status.get();
    }
    
    public void setStatus(String s) {
        status.set(s);
    }
    
    public String getStory() {
        return story.get();
    }
    
    public void setStory(String s) {
        story.set(s);
    }
}