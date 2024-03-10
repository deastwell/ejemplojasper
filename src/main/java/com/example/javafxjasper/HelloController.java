package com.example.javafxjasper;

import clase.Alumno;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.swing.JRViewer;
import utils.MYSQLConnection;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import static java.util.GregorianCalendar.AD;

public class HelloController implements Initializable {

    @FXML
    private TableColumn <Alumno, String> columnaSGE;
    @FXML
    private TextField txtSGE;
    @FXML
    private TableColumn<Alumno, String> columnaDI;
    @FXML
    private TableColumn<Alumno, String> columnaPSP;
    @FXML
    private Button btnDescargar;
    @FXML
    private TextField txtAD;
    @FXML
    private TextField txtHLC;
    @FXML
    private TextField txtDI;
    @FXML
    private Button btnInstertar;
    @FXML
    private TableColumn<Alumno, String> columnaEIE;
    @FXML
    private Button btnSalir;
    @FXML
    private TableColumn<Alumno, String> columnaHLC;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtEIE;
    @FXML
    private TableColumn<Alumno, String> columnaApellido;
    @FXML
    private TextField txtPMDM;
    @FXML
    private TableColumn<Alumno, String> columnaAD;
    @FXML
    private TextField txtApellido;
    @FXML
    private TableColumn<Alumno, String> columnaPMDM;
    @FXML
    private TableColumn<Alumno, String>columnaNombre;
    @FXML
    private TableView<Alumno> tabla;
    @FXML
    private TextField txtPSP;
    @FXML
    private Label labelInfo;


    @FXML
    public void insertar(ActionEvent actionEvent) {
        if (!txtAD.getText().isEmpty() && !txtApellido.getText().isEmpty()
                && !txtDI.getText().isEmpty() && !txtEIE.getText().isEmpty()
                && !txtHLC.getText().isEmpty() && !txtPMDM.getText().isEmpty()
                && !txtSGE.getText().isEmpty() && !txtPSP.getText().isEmpty() && !txtNombre.getText().isEmpty()) {

            try {
                // Obtener valores de los campos de texto y convertirlos a enteros
                int notaAD = Integer.parseInt(txtAD.getText());
                int notaDI = Integer.parseInt(txtDI.getText());
                int notaEIE = Integer.parseInt(txtEIE.getText());
                int notaPMDM = Integer.parseInt(txtPMDM.getText());
                int notaHLC = Integer.parseInt(txtHLC.getText());
                int notaPSP = Integer.parseInt(txtPSP.getText());
                int notaSGE = Integer.parseInt(txtSGE.getText());

                // Verificar que las notas estén en el rango [0, 10]
                if (notaAD >= 0 && notaAD <= 10 &&
                        notaDI >= 0 && notaDI <= 10 &&
                        notaEIE >= 0 && notaEIE <= 10 &&
                        notaPMDM >= 0 && notaPMDM <= 10 &&
                        notaHLC >= 0 && notaHLC <= 10 &&
                        notaPSP >= 0 && notaPSP <= 10 &&
                        notaSGE >= 0 && notaSGE <= 10) {

                    // Crear un nuevo objeto Alumno con los valores obtenidos
                    Alumno alumno = new Alumno(txtNombre.getText(), txtApellido.getText(),
                            (byte) notaAD, (byte) notaSGE, (byte) notaPMDM, (byte) notaPSP, (byte) notaEIE, (byte) notaDI, (byte) notaHLC);
                    tabla.getItems().add(alumno);
                } else {
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setHeaderText("Error");
                    alerta.setContentText("Las notas deben de estar en el intervalo [0-10]");
                    alerta.showAndWait();
                }
            } catch (NumberFormatException e) {
                // Si no se pudo convertir algún valor a entero, mostrar un mensaje de error
                Alert alerta = new Alert(Alert.AlertType.ERROR);
                alerta.setHeaderText("Error");
                alerta.setContentText("Asegúrate de ingresar números válidos en los campos de nota");
                alerta.showAndWait();
            }

        } else {
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setHeaderText("Error");
            alerta.setContentText("Por favor, todos los campos deben de estar rellenos");
            alerta.showAndWait();
        }
    }



    @FXML
    public void salir(ActionEvent actionEvent) {
    }

    @FXML
    public void descargar(ActionEvent actionEvent) {

        Connection conexion = MYSQLConnection.getConexion();
        try {
            JasperPrint jasper = JasperFillManager.fillReport("examenprueba1.jasper", new HashMap<>(),conexion);
            JRViewer visor = new JRViewer(jasper);
            JFrame ventana = new JFrame("Listado notas: ");
            ventana.getContentPane().add(visor);
            ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
            ventana.pack();
            ventana.setVisible(true);
            JRPdfExporter exp=new JRPdfExporter();
            exp.setExporterInput(new SimpleExporterInput(jasper));
            exp.setExporterOutput(new SimpleOutputStreamExporterOutput("Notas.pdf"));
            exp.setConfiguration(new SimplePdfExporterConfiguration());
            exp.exportReport();
        } catch (JRException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        columnaNombre.setCellValueFactory((alumno)->{
            String nombreAlumno = alumno.getValue().getNombre();
            return new SimpleStringProperty(nombreAlumno);
        });

        columnaAD.setCellValueFactory((alumno)->{
            String ADAlumno = String.valueOf(alumno.getValue().getAD());
            return new SimpleStringProperty(ADAlumno);
        });

        columnaApellido.setCellValueFactory((alumno)->{
            String apellidoAlumno = alumno.getValue().getApellido();
            return new SimpleStringProperty(apellidoAlumno);
        });

        columnaSGE.setCellValueFactory((alumno)->{
            String SGEAlumno = String.valueOf(alumno.getValue().getSGE());
            return new SimpleStringProperty(SGEAlumno);
        });

        columnaDI.setCellValueFactory((alumno)->{
            String DIAlumno = String.valueOf(alumno.getValue().getDI());
            return new SimpleStringProperty(DIAlumno);
        });

        columnaEIE.setCellValueFactory((alumno)->{
            String EIEAlumno = String.valueOf(alumno.getValue().getEIE());
            return new SimpleStringProperty(EIEAlumno);
        });

        columnaPMDM.setCellValueFactory((alumno)->{
            String PMDMAlumno = String.valueOf(alumno.getValue().getPMDM());
            return new SimpleStringProperty(PMDMAlumno);
        });

        columnaNombre.setCellValueFactory((alumno)->{
            String nombreAlumno = alumno.getValue().getNombre();
            return new SimpleStringProperty(nombreAlumno);
        });

        columnaPSP.setCellValueFactory((alumno)->{
            String PSPAlumno = String.valueOf(alumno.getValue().getPSP());
            return new SimpleStringProperty(PSPAlumno);
        });

        columnaHLC.setCellValueFactory((alumno)->{
            String HLCAlumno = String.valueOf(alumno.getValue().getHLC());
            return new SimpleStringProperty(HLCAlumno);
        });

        tabla.getSelectionModel().selectedItemProperty().addListener((observableValue, alumno, t1) ->{
            Alumno alumnoseleccionado = t1;

            int notaAD = alumnoseleccionado.getAD();
            int notaDI = alumnoseleccionado.getDI();
            int notaEIE = alumnoseleccionado.getEIE();
            int notaPMDM = alumnoseleccionado.getPMDM();
            int notaHLC = alumnoseleccionado.getHLC();
            int notaPSP = alumnoseleccionado.getPSP();
            int notaSGE = alumnoseleccionado.getSGE();

            ArrayList<Integer>notas=new ArrayList<>();
            notas.add(notaAD);
            notas.add(notaDI);
            notas.add(notaEIE);
            notas.add(notaSGE);
            notas.add(notaPMDM);
            notas.add(notaPSP);
            notas.add((notaHLC));
            Integer sumatorio = 0;

            Integer contador = 0;
            for (Integer nota:notas){


                if(nota<5){
                    contador++;

                }

            }

            if(contador==0){
                for(Integer nota:notas){
                    sumatorio+=nota;
                }
                Double media = (double) (sumatorio/notas.size());
                labelInfo.setText("La nota media es: "+String.valueOf(media));
            }else{
                labelInfo.setText("Tiene suspensa: "+contador);
            }

        } );

    }
}