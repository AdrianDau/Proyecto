import es.adrian.Launcher;
import es.adrian.logic.Logica;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.control.TableViewMatchers;

import static org.testfx.api.FxAssert.verifyThat;

public class MainTest extends ApplicationTest {
    @Before
    public void setup() throws Exception {
        ApplicationTest.launch(Launcher.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.show();
    }

    @After
    public void finalizar()
    {
        Logica.getInstance().finalizar();
    }

    @Test
    public void testInicial()
    {
        verifyThat("#tableViewMesas", TableViewMatchers.hasNumRows(3));
    }

    @Test
    public void testAddMesa()
    {
        clickOn("#botonAdd");
        clickOn("#localizacionTf");
        write("4");
        clickOn("#capacidadTf");
        write("4");
        clickOn("#botonAceptar");
        verifyThat("#tableViewMesas", TableViewMatchers.hasNumRows(4));
        verifyThat("#tableViewMesas",TableViewMatchers.containsRowAtIndex(3, new String[]{"4","4","4"}));
    }

    @Test
    public void testAddProducto() {
        clickOn("#botonCarta");
        clickOn("#productoTf");
        write("Vino");
        clickOn("#precioTf");
        write("10.50");
        clickOn("#categoriasCb");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#botonAñadirProducto");
        verifyThat("#listaProductos", TableViewMatchers.hasNumRows(4));

    }
}
