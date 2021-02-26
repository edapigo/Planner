/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scheduler;

import java.sql.Connection;

/**
 *
 * @author Juan Xavier Pita
 */
public class SchedulerDatabase {
    
    private Connection connection;

    public SchedulerDatabase() {
    }
    
    public SchedulerDatabase(Connection connection) {
        this.connection = connection;
    }
}
