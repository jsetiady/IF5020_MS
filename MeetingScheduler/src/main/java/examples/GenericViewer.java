package examples;

public class GenericViewer<T> {
    T meetingObject;

    // constructor   
    public GenericViewer(T meetingObject) {
        this.meetingObject = meetingObject;
    }

    // setter dan getter
    public T getMeetingObject() {
        return meetingObject;
    }

    public void setMeetingObject(T meetingObject) {
        this.meetingObject = meetingObject;
    }
    
    //additional method
    public void printListMeetingObject(T meetingObject) {
    	System.out.println("tes");
    }
    
    public void printDetailMeetingObject(T meetingObject) {
    	
    }
    
    
}
