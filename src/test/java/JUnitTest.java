import org.junit.*;
import static org.junit.Assert.*;

public class AppTest {

  @Test
  public void function_whatShouldHappenInTest_testAnswer() {
    App app = new App();
    Object whatever = alsoWhatever;
    assertEquals(whatever, app.function(userInput));
  }
}
