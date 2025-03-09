package staff_producer;

import java.util.List;


public interface StaffService {
    public void displayStaffs();

    void createStaff();

    void editStaff();

    void assignCourses(String StaffID);

    void deleteStaff(String StaffID);
    
    void searchStaff(String StaffID);
    
    List<Staff> getAllStaffs();
}
