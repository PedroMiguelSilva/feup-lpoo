package com.damon.merakiapp;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.damon.merakiapp.logic.Category;
import com.damon.merakiapp.logic.DataManager;
import com.damon.merakiapp.logic.Task;
import com.damon.merakiapp.logic.User;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


@RunWith(AndroidJUnit4.class)
public class InstrumentedTests_DataBaseTests {

    private Context appContext = InstrumentationRegistry.getTargetContext();

    //DATA BASE TESTS

    //> User
    @Test
    public void signup(){

        DataManager.getInstance().setContext(appContext);
        DataManager.getInstance().createDataBase();

        DataManager.getInstance().getDataBase().deleteAllTasks();
        DataManager.getInstance().getDataBase().deleteAllCategories();
        DataManager.getInstance().getDataBase().deleteAllUsers();

        User user1 = new User("teste1", "teste1@gmail.com", "teste1p", false);

        assertEquals(1, DataManager.getInstance().getDataBase().addUser(user1));
        assertEquals("teste1",DataManager.getInstance().getDataBase().getUser(1).getName());
        assertEquals("teste1@gmail.com",DataManager.getInstance().getDataBase().getUser(1).getEmail());
        assertEquals("teste1p",DataManager.getInstance().getDataBase().getUser(1).getPassword());

        //adicionar user com mesmo email //TODO email unique
        assertEquals(-1, DataManager.getInstance().getDataBase().addUser(user1));

    }

    @Test
    public void login(){

        DataManager.getInstance().setContext(appContext);
        DataManager.getInstance().createDataBase();

        DataManager.getInstance().getDataBase().deleteAllTasks();
        DataManager.getInstance().getDataBase().deleteAllCategories();
        DataManager.getInstance().getDataBase().deleteAllUsers();

        User user1 = new User("teste1", "teste1@gmail.com", "teste1p", false);

        assertEquals(1, DataManager.getInstance().getDataBase().addUser(user1));
        assertEquals(true, DataManager.getInstance().getDataBase().login(user1.getEmail(), user1.getPassword()));
        assertEquals(1, DataManager.getInstance().getDataBase().getUserLoggedId());

        //failed login
        User user2 = new User("teste2", "teste2@gmail.com", "teste2p", false);
        assertEquals(false, DataManager.getInstance().getDataBase().login(user2.getEmail(), user2.getPassword()));
        assertEquals(1, DataManager.getInstance().getDataBase().getUserLoggedId());

    }

    @Test
    public void updateUser(){

        DataManager.getInstance().setContext(appContext);
        DataManager.getInstance().createDataBase();

        DataManager.getInstance().getDataBase().deleteAllTasks();
        DataManager.getInstance().getDataBase().deleteAllCategories();
        DataManager.getInstance().getDataBase().deleteAllUsers();

        User user1 = new User("teste1", "teste1@gmail.com", "teste1p", false);

        assertEquals(1, DataManager.getInstance().getDataBase().addUser(user1));
        assertEquals("teste1", DataManager.getInstance().getDataBase().getUser(1).getName());

        user1.setName("teste1updated");
        DataManager.getInstance().getDataBase().updateUser(user1);
        assertEquals("teste1updated", DataManager.getInstance().getDataBase().getUser(1).getName());

    }

    //>Category

    @Test
    public void addCategory(){

        DataManager.getInstance().setContext(appContext);
        DataManager.getInstance().createDataBase();

        DataManager.getInstance().getDataBase().deleteAllTasks();
        DataManager.getInstance().getDataBase().deleteAllCategories();
        DataManager.getInstance().getDataBase().deleteAllUsers();

        User user1 = new User("teste1", "teste1@gmail.com", "teste1p", false);
        DataManager.getInstance().getDataBase().addUser(user1);

        Category category1 = new Category("categoria1", 1);
        assertEquals(1, DataManager.getInstance().getDataBase().addCategory(category1));
        assertEquals("categoria1", DataManager.getInstance().getDataBase().getCategory(1).getName());
        assertEquals(1, DataManager.getInstance().getDataBase().getCategory(1).getUserId());

        Category category2 = new Category("categoria2", 1);
        assertEquals(2, DataManager.getInstance().getDataBase().addCategory(category2));
        assertEquals("categoria2", DataManager.getInstance().getDataBase().getCategory(2).getName());
        assertEquals(1, DataManager.getInstance().getDataBase().getCategory(2).getUserId());

    }

    @Test
    public void getUserCategories(){

        DataManager.getInstance().setContext(appContext);
        DataManager.getInstance().createDataBase();

        DataManager.getInstance().getDataBase().deleteAllTasks();
        DataManager.getInstance().getDataBase().deleteAllCategories();
        DataManager.getInstance().getDataBase().deleteAllUsers();

        User user1 = new User("teste1", "teste1@gmail.com", "teste1p", false);
        DataManager.getInstance().getDataBase().addUser(user1);
        User user2 = new User("teste2", "teste2@gmail.com", "teste2p", false);
        DataManager.getInstance().getDataBase().addUser(user2);

        Category category1 = new Category("categoria1", 1);
        Category category2 = new Category("categoria2", 1);
        Category category3 = new Category("categoria3", 2);
        Category category4 = new Category("categoria4", 2);
        DataManager.getInstance().getDataBase().addCategory(category1);
        DataManager.getInstance().getDataBase().addCategory(category2);
        DataManager.getInstance().getDataBase().addCategory(category3);
        DataManager.getInstance().getDataBase().addCategory(category4);


        assertEquals("categoria1", DataManager.getInstance().getDataBase().getUserCategories(1).get(0).getName());
        assertEquals("categoria2", DataManager.getInstance().getDataBase().getUserCategories(1).get(1).getName());
        assertEquals("categoria3", DataManager.getInstance().getDataBase().getUserCategories(2).get(0).getName());
        assertEquals("categoria4", DataManager.getInstance().getDataBase().getUserCategories(2).get(1).getName());

    }

    @Test
    public void updateCategory(){

        DataManager.getInstance().setContext(appContext);
        DataManager.getInstance().createDataBase();

        DataManager.getInstance().getDataBase().deleteAllTasks();
        DataManager.getInstance().getDataBase().deleteAllCategories();
        DataManager.getInstance().getDataBase().deleteAllUsers();

        User user1 = new User("teste1", "teste1@gmail.com", "teste1p", false);
        DataManager.getInstance().getDataBase().addUser(user1);
        User user2 = new User("teste2", "teste2@gmail.com", "teste2p", false);
        DataManager.getInstance().getDataBase().addUser(user2);

        Category category1 = new Category("categoria1", 1);
        Category category2 = new Category("categoria2", 2);
        DataManager.getInstance().getDataBase().addCategory(category1);
        DataManager.getInstance().getDataBase().addCategory(category2);

        assertEquals("categoria1", DataManager.getInstance().getDataBase().getCategory(1).getName());
        assertEquals("categoria2", DataManager.getInstance().getDataBase().getCategory(2).getName());

        category1.setName("categoria1updated");
        category2.setName("categoria2updated");
        DataManager.getInstance().getDataBase().updateCategory(category1);
        DataManager.getInstance().getDataBase().updateCategory(category2);

        assertEquals("categoria1updated", DataManager.getInstance().getDataBase().getCategory(1).getName());
        assertEquals("categoria2updated", DataManager.getInstance().getDataBase().getCategory(2).getName());

    }

    //> Task

    @Test
    public void addTask(){

        DataManager.getInstance().setContext(appContext);
        DataManager.getInstance().createDataBase();

        DataManager.getInstance().getDataBase().deleteAllTasks();
        DataManager.getInstance().getDataBase().deleteAllCategories();
        DataManager.getInstance().getDataBase().deleteAllUsers();

        User user1 = new User("teste1", "teste1@gmail.com", "teste1p", false);
        DataManager.getInstance().getDataBase().addUser(user1);

        Category category1 = new Category("categoria1", 1);
        DataManager.getInstance().getDataBase().addCategory(category1);

        Task task1 = new Task("task1", "Today", false, 1);
        assertEquals(1, DataManager.getInstance().getDataBase().addTask(task1));
        assertEquals("task1", DataManager.getInstance().getDataBase().getTask(1).getText());
        assertEquals("Today", DataManager.getInstance().getDataBase().getTask(1).getDate());
        assertEquals(false, DataManager.getInstance().getDataBase().getTask(1).isComplete());
        assertEquals(false, DataManager.getInstance().getDataBase().getTask(1).isArchived() );
        assertEquals(1, DataManager.getInstance().getDataBase().getTask(1).getCategoryId());

        Task task2 = new Task("task2", "Today", false, 1);
        task2.setArchived(true);
        assertEquals(2, DataManager.getInstance().getDataBase().addTask(task2));
        assertEquals(true, DataManager.getInstance().getDataBase().getTask(2).isArchived() );


    }

    @Test
    public void getCategoryTasks(){

        DataManager.getInstance().setContext(appContext);
        DataManager.getInstance().createDataBase();

        DataManager.getInstance().getDataBase().deleteAllTasks();
        DataManager.getInstance().getDataBase().deleteAllCategories();
        DataManager.getInstance().getDataBase().deleteAllUsers();

        User user1 = new User("teste1", "teste1@gmail.com", "teste1p", false);
        DataManager.getInstance().getDataBase().addUser(user1);

        Category category1 = new Category("categoria1", 1);
        Category category2 = new Category("categoria2", 1);
        DataManager.getInstance().getDataBase().addCategory(category1);
        DataManager.getInstance().getDataBase().addCategory(category2);

        Task task1 = new Task("task1", "Today", false, 1);
        Task task2 = new Task("task2", "Today", false, 1);
        Task task3 = new Task("task3", "Today", false, 2);
        Task task4 = new Task("task4", "Today", false, 2);
        DataManager.getInstance().getDataBase().addTask(task1);
        DataManager.getInstance().getDataBase().addTask(task2);
        DataManager.getInstance().getDataBase().addTask(task3);
        DataManager.getInstance().getDataBase().addTask(task4);

        assertEquals("task1", DataManager.getInstance().getDataBase().getCategoryTasks(1).get(0).getText());
        assertEquals("task2", DataManager.getInstance().getDataBase().getCategoryTasks(1).get(1).getText());
        assertEquals("task3", DataManager.getInstance().getDataBase().getCategoryTasks(2).get(0).getText());
        assertEquals("task4", DataManager.getInstance().getDataBase().getCategoryTasks(2).get(1).getText());

    }

    @Test
    public void getUserTasks(){

        DataManager.getInstance().setContext(appContext);
        DataManager.getInstance().createDataBase();

        DataManager.getInstance().getDataBase().deleteAllTasks();
        DataManager.getInstance().getDataBase().deleteAllCategories();
        DataManager.getInstance().getDataBase().deleteAllUsers();

        User user1 = new User("teste1", "teste1@gmail.com", "teste1p", false);
        DataManager.getInstance().getDataBase().addUser(user1);
        User user2 = new User("teste2", "teste2@gmail.com", "teste2p", false);
        DataManager.getInstance().getDataBase().addUser(user2);

        Category category1 = new Category("categoria1", 1);
        Category category2 = new Category("categoria2", 1);
        Category category3 = new Category("categoria2", 2);
        Category category4 = new Category("categoria2", 2);
        DataManager.getInstance().getDataBase().addCategory(category1);
        DataManager.getInstance().getDataBase().addCategory(category2);
        DataManager.getInstance().getDataBase().addCategory(category3);
        DataManager.getInstance().getDataBase().addCategory(category4);

        //Not Archived
        Task task1 = new Task("task1", "Today", false, 1);
        Task task2 = new Task("task2", "Today", false, 2);
        Task task3 = new Task("task3", "Today", false, 3);
        Task task4 = new Task("task4", "Today", false, 4);
        DataManager.getInstance().getDataBase().addTask(task1);
        DataManager.getInstance().getDataBase().addTask(task2);
        DataManager.getInstance().getDataBase().addTask(task3);
        DataManager.getInstance().getDataBase().addTask(task4);

        assertEquals("task1", DataManager.getInstance().getDataBase().getUserTasks(1, false).get(0).getText());
        assertEquals("task2", DataManager.getInstance().getDataBase().getUserTasks(1, false).get(1).getText());
        assertEquals("task3", DataManager.getInstance().getDataBase().getUserTasks(2, false).get(0).getText());
        assertEquals("task4", DataManager.getInstance().getDataBase().getUserTasks(2, false).get(1).getText());

        //Archived
        Task task5 = new Task("task5", "Today", false, 1);
        task5.setArchived(true);
        Task task6 = new Task("task6", "Today", false, 2);
        task6.setArchived(true);
        Task task7 = new Task("task7", "Today", false, 3);
        task7.setArchived(true);
        Task task8 = new Task("task8", "Today", false, 4);
        task8.setArchived(true);

        DataManager.getInstance().getDataBase().addTask(task5);
        DataManager.getInstance().getDataBase().addTask(task6);
        DataManager.getInstance().getDataBase().addTask(task7);
        DataManager.getInstance().getDataBase().addTask(task8);

        assertEquals("task5", DataManager.getInstance().getDataBase().getUserTasks(1, true).get(0).getText());
        assertEquals("task6", DataManager.getInstance().getDataBase().getUserTasks(1, true).get(1).getText());
        assertEquals("task7", DataManager.getInstance().getDataBase().getUserTasks(2, true).get(0).getText());
        assertEquals("task8", DataManager.getInstance().getDataBase().getUserTasks(2, true).get(1).getText());


    }

    @Test
    public void getUserTasksByDay(){

        DataManager.getInstance().setContext(appContext);
        DataManager.getInstance().createDataBase();

        DataManager.getInstance().getDataBase().deleteAllTasks();
        DataManager.getInstance().getDataBase().deleteAllCategories();
        DataManager.getInstance().getDataBase().deleteAllUsers();

        User user1 = new User("teste1", "teste1@gmail.com", "teste1p", false);
        DataManager.getInstance().getDataBase().addUser(user1);

        Category category1 = new Category("categoria1", 1);
        Category category2 = new Category("categoria2", 1);
        DataManager.getInstance().getDataBase().addCategory(category1);
        DataManager.getInstance().getDataBase().addCategory(category2);

        Task task1 = new Task("task1", "Today", false, 1);
        Task task2 = new Task("task2", "Today", false, 1);
        Task task3 = new Task("task3", "Today", false, 2);
        Task task4 = new Task("task4", "Tomorrow", false, 2);
        DataManager.getInstance().getDataBase().addTask(task1);
        DataManager.getInstance().getDataBase().addTask(task2);
        DataManager.getInstance().getDataBase().addTask(task3);
        DataManager.getInstance().getDataBase().addTask(task4);

        assertEquals(3, DataManager.getInstance().getDataBase().getUserTasksByDay(1, "Today").size());
        assertEquals("task1", DataManager.getInstance().getDataBase().getUserTasksByDay(1, "Today").get(0).getText());
        assertEquals("task2", DataManager.getInstance().getDataBase().getUserTasksByDay(1, "Today").get(1).getText());
        assertEquals("task3", DataManager.getInstance().getDataBase().getUserTasksByDay(1, "Today").get(2).getText());
        assertEquals("task4", DataManager.getInstance().getDataBase().getUserTasksByDay(1, "Tomorrow").get(0).getText());

    }

    @Test
    public void updateTask(){

        DataManager.getInstance().setContext(appContext);
        DataManager.getInstance().createDataBase();

        DataManager.getInstance().getDataBase().deleteAllTasks();
        DataManager.getInstance().getDataBase().deleteAllCategories();
        DataManager.getInstance().getDataBase().deleteAllUsers();

        User user1 = new User("teste1", "teste1@gmail.com", "teste1p", false);
        DataManager.getInstance().getDataBase().addUser(user1);

        Category category1 = new Category("categoria1", 1);
        DataManager.getInstance().getDataBase().addCategory(category1);

        Task task1 = new Task("task1", "Today", false, 1);
        DataManager.getInstance().getDataBase().addTask(task1);

        assertEquals("task1", DataManager.getInstance().getDataBase().getTask(1).getText());

        task1.setText("task1updated");
        DataManager.getInstance().getDataBase().updateTask(task1);

        assertEquals("task1updated", DataManager.getInstance().getDataBase().getTask(1).getText());
    }


    //Delete operations
    @Test
    public void deleteTask(){

        DataManager.getInstance().setContext(appContext);
        DataManager.getInstance().createDataBase();

        DataManager.getInstance().getDataBase().deleteAllTasks();
        DataManager.getInstance().getDataBase().deleteAllCategories();
        DataManager.getInstance().getDataBase().deleteAllUsers();

        User user1 = new User("teste1", "teste1@gmail.com", "teste1p", false);
        DataManager.getInstance().getDataBase().addUser(user1);

        Category category1 = new Category("categoria1", 1);
        DataManager.getInstance().getDataBase().addCategory(category1);

        Task task1 = new Task("task1", "Today", false, 1);
        DataManager.getInstance().getDataBase().addTask(task1);

        assertEquals("task1", DataManager.getInstance().getDataBase().getTask(1).getText());

        DataManager.getInstance().getDataBase().deleteTask(task1);

        assertEquals(null, DataManager.getInstance().getDataBase().getTask(1));

    }

    @Test
    public void deleteCategory(){

        DataManager.getInstance().setContext(appContext);
        DataManager.getInstance().createDataBase();

        DataManager.getInstance().getDataBase().deleteAllTasks();
        DataManager.getInstance().getDataBase().deleteAllCategories();
        DataManager.getInstance().getDataBase().deleteAllUsers();

        User user1 = new User("teste1", "teste1@gmail.com", "teste1p", false);
        DataManager.getInstance().getDataBase().addUser(user1);

        Category category1 = new Category("categoria1", 1);
        DataManager.getInstance().getDataBase().addCategory(category1);

        Task task1 = new Task("task1", "Today", false, 1);
        DataManager.getInstance().getDataBase().addTask(task1);

        assertEquals("categoria1", DataManager.getInstance().getDataBase().getCategory(1).getName());
        assertEquals("task1", DataManager.getInstance().getDataBase().getTask(1).getText());

        DataManager.getInstance().getDataBase().deleteCategory(category1);

        assertEquals(null, DataManager.getInstance().getDataBase().getCategory(1));
        assertEquals(null, DataManager.getInstance().getDataBase().getTask(1));

    }

    @Test
    public void deleteUser(){

        DataManager.getInstance().setContext(appContext);
        DataManager.getInstance().createDataBase();

        DataManager.getInstance().getDataBase().deleteAllTasks();
        DataManager.getInstance().getDataBase().deleteAllCategories();
        DataManager.getInstance().getDataBase().deleteAllUsers();

        User user1 = new User("teste1", "teste1@gmail.com", "teste1p", false);
        DataManager.getInstance().getDataBase().addUser(user1);

        Category category1 = new Category("categoria1", 1);
        DataManager.getInstance().getDataBase().addCategory(category1);

        Task task1 = new Task("task1", "Today", false, 1);
        DataManager.getInstance().getDataBase().addTask(task1);

        assertEquals("teste1",DataManager.getInstance().getDataBase().getUser(1).getName());
        assertEquals("categoria1", DataManager.getInstance().getDataBase().getCategory(1).getName());
        assertEquals("task1", DataManager.getInstance().getDataBase().getTask(1).getText());

        DataManager.getInstance().getDataBase().deleteUser(user1);

        assertEquals(null,DataManager.getInstance().getDataBase().getUser(1));
        assertEquals(null, DataManager.getInstance().getDataBase().getCategory(1));
        assertEquals(null, DataManager.getInstance().getDataBase().getTask(1));

    }

}
