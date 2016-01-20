import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Rule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void rootTest() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("To Do List");
  }

  @Test
  public void categoryIsCreatedTest() {
    Category newCategory = new Category("Chores");
    newCategory.save();
    String categoryPath = String.format("http://localhost:4567/categories/%d", newCategory.getId());
    goTo(categoryPath);
    assertThat(pageSource()).contains("Chores");
  }

  @Test
  public void categoryIsDisplayedTest() {
    Category newCategory = new Category("Chores");
    newCategory.save();
    String categoryPath = "http://localhost:4567/categories";
    goTo(categoryPath);
    assertThat(pageSource()).contains("Chores");
  }

  @Test
  public void newTaskIsCreatedTest() {
    Category newCategory = new Category("Chores");
    newCategory.save();
    Task newTask = new Task("Do laundry", newCategory.getId());
    newTask.save();
    String categoryPath = String.format("http://localhost:4567/categories/%d", newCategory.getId());
    goTo(categoryPath);
    assertThat(pageSource()).contains("Do laundry");
  }

  @Test
  public void allTasksAndCategoriesDisplay() {
    Category newCategory = new Category("Chores");
    newCategory.save();
    Task newTask = new Task("Do laundry", newCategory.getId());
    newTask.save();
    Task newTaskTwo = new Task("Clean desk", newCategory.getId());
    newTaskTwo.save();
    String categoryPath = "http://localhost:4567/all-tasks";
    goTo(categoryPath);
    assertThat(pageSource()).contains("Chores", "Do laundry", "Clean desk");
  }



  // @Test
  // public void allTasksAddedWithAllCategories() {
  //   goTo("http://localhost:4567/categories/new");
  //   fill("#name").with("Homework");
  //   submit(".btn");
  //   click("a", withText("View Categories"));
  //   click("a", withText("Homework"));
  //   click("a", withText("Add a new task"));
  //   fill("#description").with("Watch video");
  //   submit(".btn");
  //   click("a", withText("All tasks"));
  //   assertThat(pageSource()).contains("All Tasks");
  // }

}
