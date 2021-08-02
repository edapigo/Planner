/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

public class Account {
    private int userId;
    private String fName;
    private String lName;
    private String user;
    private String pass;
    private String email;

    public Account(int userId, String fName, String lName, String user, String pass, String email) {
        this.userId = userId;
        this.fName = fName;
        this.lName = lName;
        this.user = user;
        this.pass = pass;
        this.email = email;
    }
  
    public int getUserId() {
        return this.userId;
    }
  
    public String getUser() {
        return this.user;
    }
}
