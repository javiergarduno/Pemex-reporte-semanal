import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;



public class FileChooser extends JPanel
implements ActionListener {
	JButton selectButton;

	JFileChooser chooser;
	String choosertitle;
	
	private JTextArea logArea;
	private JTextArea userArea;


	public FileChooser() {

		setBorder(BorderFactory.createTitledBorder("Reporte semanal Toiip - Javier Garduño"));

		selectButton = new JButton("Hacer respaldo");
		selectButton.addActionListener(this);
		
        userArea = new JTextArea(5, 50);
        userArea.append("Instrucciones: \nEste programa hace la copia de los archivos del "
        		+ "reporte semanal.\n"
        		+ "Para empezar presione el boton \"Hacer respaldo\"\n"
        		+ "y seleccione la capeta que contiene el reporte semanal");
        userArea.setEditable(false);
        
        logArea = new JTextArea(18, 50);
        logArea.append("Eventos:\n ");
        logArea.setEditable(false);
        
        JScrollPane jp2 = new JScrollPane(userArea);        
        add(jp2, BorderLayout.NORTH);
        
        add(selectButton, BorderLayout.CENTER);
        
        JScrollPane jp = new JScrollPane(logArea);        
        add(jp, BorderLayout.SOUTH);
        
		
                
       // pack();
        // arbitrary size to make vertical scrollbar appear
        //setSize(1204, 768);
       // setLocationByPlatform(true);
        setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		chooser = new JFileChooser(); 
		chooser.setCurrentDirectory(new java.io.File("Y:/SIO"));
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		// disable the "All files" option.
		chooser.setAcceptAllFileFilterUsed(false);

		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 

			/*
      System.out.println("getCurrentDirectory(): " +  chooser.getCurrentDirectory());
      System.out.println("getSelectedFile() : " +  ReportSourceDir);
			 */

			/*move this code to another event or function
			 */      

			Calendar calendar = Calendar.getInstance();				
			String ReportPath = "Respaldo del Reporte semanal -" +
					calendar.get(Calendar.YEAR) + 
					String.format("%02d", calendar.get(Calendar.MONTH)+1) +
					String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)+1) + "-" +
					calendar.get(Calendar.HOUR_OF_DAY)  +
					calendar.get(Calendar.MINUTE) + 
					calendar.get(Calendar.SECOND);
			File ReportDestDir = new File(ReportPath);
			ReportDestDir.mkdir();

			File swfDestDir = new File(ReportDestDir.getPath() + "/SWF");
			swfDestDir.mkdir();

			File anexosDestDir = new File(ReportDestDir.getPath() + "/Anexos");
			anexosDestDir.mkdir();
			
			File ReportSourceDir = chooser.getSelectedFile();

			File swfSourceFile = new File(ReportSourceDir.toString() + "/Reporte semanal.swf");
			
			FileFilter xlfFileFilter = new WildcardFileFilter("Reporte Semanal Del*lunes.xlf");
			File[] xlfSourceFile = ReportSourceDir.listFiles(xlfFileFilter);
			
			File [] layoutsSourceFile = new File[7];
			layoutsSourceFile[0] = new File(ReportSourceDir.toString() + "/Compartido/CONFIABILIDAD/LAYOUT CONFIABILIDAD.xls");	
			layoutsSourceFile[1] = new File(ReportSourceDir.toString() + "/Compartido/MERCADO/LAYOUT GIOER-ECH.xlsx");
			layoutsSourceFile[2] = new File(ReportSourceDir.toString() + "/Compartido/PEP/LAYOUT GSCO-PEP.xlsx");
			layoutsSourceFile[3] = new File(ReportSourceDir.toString() + "/Compartido/PGPB Y PPQ/LAYOUT GSCO-PGPB.xlsx");
			layoutsSourceFile[4] = new File(ReportSourceDir.toString() + "/Compartido/PGPB Y PPQ/LAYOUT GSCO-PPQ.xlsx");
			layoutsSourceFile[5] = new File(ReportSourceDir.toString() + "/Compartido/PMI/LAYOUT PMI.xlsx");
			layoutsSourceFile[6] = new File(ReportSourceDir.toString() + "/Compartido/PREF/LAYOUT GSCO-HSF.xlsx");

			
			
			
			File pdfSourceDir = new File(ReportSourceDir.toString() + "/Compartido");
			FileFilter pdfFileFilter = new WildcardFileFilter("*.pdf");
			File[] pdfSourceFile = pdfSourceDir.listFiles(pdfFileFilter);
			
			try{
				
				System.out.println(new Date() + " Inicia respaldo" );
				
				
				System.out.println("-- SWF");
				FileUtils.copyFileToDirectory(swfSourceFile, swfDestDir);
				System.out.println("Archivo \"" + swfSourceFile + "\" copiado a "  + swfDestDir );
				logText("Archivo " + swfSourceFile );
				
				System.out.println("-- XLF");
				for (int i = 0; i < xlfSourceFile.length; i++) {
					FileUtils.copyFileToDirectory(xlfSourceFile[i], ReportDestDir);
					System.out.println("Archivo \"" + xlfSourceFile[i] + "\" copiado a "  + ReportDestDir );
					logText("Archivo " + xlfSourceFile[i] );
				}

				System.out.println("-- Layouts");
				
				for (int i = 0; i < layoutsSourceFile.length; i++) {
					FileUtils.copyFileToDirectory(layoutsSourceFile[i], ReportDestDir);
					System.out.println("Archivo \"" + layoutsSourceFile[i] + "\" copiado a "  + ReportDestDir );
					logText("Archivo " + layoutsSourceFile[i] );
				}
				
				
				System.out.println("-- PDF");

				for (int i = 0; i < pdfSourceFile.length; i++) {
					FileUtils.copyFileToDirectory(pdfSourceFile[i], anexosDestDir);
					System.out.println("Archivo \"" + pdfSourceFile[i] + "\" copiado a "  + anexosDestDir );
					logText("Copiado " + pdfSourceFile[i]);

				}
				
				System.out.println("Copiado terminado");
				logText("Copiado terminado");
			}
			catch(IOException e1)
			{
				e1.printStackTrace();
				logText("No se copiaron los archivos, asegurece de que la carpeta es la correcta");
			}
			
			/*
			//File dir = new File(".");
			 FileFilter fileFilter = new WildcardFileFilter("*");
			 File[] files = ReportSourceDir.listFiles(fileFilter);
			 for (int i = 0; i < files.length; i++) {
			   System.out.println(files[i]);
			 }
			 */

		}
		else {
			System.out.println("No Selection ");
		}
	}

	public Dimension getPreferredSize(){
		return new Dimension(650, 500);
	}

	public String getDirectoryPath(){
		return "";
	}
	
	public void logText(String text){
    	logArea.append("\n" + text);
    	
    }

	public static void main(String s[]) {
		JFrame frame = new JFrame("");
		FileChooser chooser = new FileChooser();

		//Close windows event
		frame.addWindowListener(
				new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						System.exit(0);
					}
				}
				);

		//Frame properties
		//frame.getContentPane().add(chooser,"Center");
		frame.add(chooser,"North");
		frame.setSize(chooser.getPreferredSize());
		frame.setVisible(true);
	}
}