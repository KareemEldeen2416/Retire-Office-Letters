/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package windows;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.poi.xwpf.usermodel.*;
import java.io.FileOutputStream;
import java.math.BigInteger;
import javafx.scene.paint.Color;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;

/**
 *
 * @author KareemEldeen
 */
public class ExpertCertificate implements Initializable{
    
    @FXML
    TextField rotba,name,phone,oldness,education;
    @FXML
    DatePicker date;
    @FXML
    Label status;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("E C window opens");
    }
    
    public void createReport(){
        try{
            //Create the document
            XWPFDocument doc = new XWPFDocument();
            
            //Set margin for the page
            CTSectPr sectPr = doc.getDocument().getBody().addNewSectPr();
            CTPageMar pageMar = sectPr.addNewPgMar();
            int margin = 300;
            pageMar.setTop(BigInteger.valueOf(margin));
            pageMar.setBottom(BigInteger.valueOf(margin));
            pageMar.setRight(BigInteger.valueOf(margin));
            pageMar.setLeft(BigInteger.valueOf(margin));
            
            //Set borders for the page
            CTPageBorders pageBorders = sectPr.addNewPgBorders();
            pageBorders.addNewTop().setVal(STBorder.DOUBLE_D);
            pageBorders.addNewBottom().setVal(STBorder.DOUBLE_D);
            pageBorders.addNewRight().setVal(STBorder.DOUBLE_D);
            pageBorders.addNewLeft().setVal(STBorder.DOUBLE_D);
            
            //Writing top right content (four lines of tarweesa)
            XWPFParagraph headerPara = doc.createParagraph();
            headerPara.setAlignment(ParagraphAlignment.RIGHT);
            headerPara.setSpacingBefore(400);
            XWPFRun run = headerPara.createRun();
            run.setFontFamily("Arial");
            run.setFontSize(12);
            run.setBold(true);
            run.setText("ادارة الاشارة");
            run.addBreak();
            run.setText("فرع شئون ضباط");
            run.addBreak();
            run.setText("مكتب خدمة ضباط متقاعدين");
            run.addBreak();
            run.setText("التاريخ: 11/052026");
            run.addBreak();
            run.setText("القيد: 11/05/2026");
            
            //Write the main title
            XWPFParagraph titlePara = doc.createParagraph();
            titlePara.setAlignment(ParagraphAlignment.CENTER);
            titlePara.setSpacingAfter(300);
            XWPFRun titleRun = titlePara.createRun();
            titleRun.setFontFamily("Arial");
            titleRun.setFontSize(16);
            titleRun.setBold(true);
            titleRun.setText("كشف بأسماء الضباط المطلوب لهم استخراج شهادات خبرة و السيرة الذاتية");
            titleRun.setUnderline(UnderlinePatterns.THICK);
            
            //Create table of the officers
            XWPFTable table = doc.createTable(2,7);
            table.setWidth("80%");
            table.setTableAlignment(TableRowAlign.CENTER);
            String [] headers = {"تليفون","تاريخ الإحالة","المؤهل الدراسي","الاسم","رتبة","رقم الأقدمية","م"};
            XWPFTableRow headerRow = table.getRow(0);
            for(int i =0 ;i < headers.length;i++){
                XWPFTableCell cell = headerRow.getCell(i);
                if(cell == null){cell = headerRow.addNewTableCell();}
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                XWPFParagraph p = cell.getParagraphs().get(0);
                p.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun headerRun = p.createRun();
                headerRun.setFontFamily("Arial");
                headerRun.setFontSize(14);
                headerRun.setBold(true);
                headerRun.setText(headers[i]);
            }
            
            addDataRow(table.getRow(1),phone.getText(),String.valueOf(date.getValue()),education.getText(),name.getText(),rotba.getText(),oldness.getText(),"1");
            addSignatureSection(doc);
            
           
           try (FileOutputStream out = new FileOutputStream("firstExp.docx")) {
                doc.write(out);
                doc.close();
                System.out.println("Process is a success");
                status.setText("Successful Process");
                status.setTextFill(Color.GREEN);
            }catch(Exception e){
                System.out.println(e.getCause());
                System.out.println(e.getMessage());
                status.setText("Failed Process");
                status.setTextFill(Color.GREEN);
            } 

            
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
        }
    }
    
    
    private static void addDataRow(XWPFTableRow row,String ... values){
        for(int i = 0 ; i < values.length;i++){
            XWPFTableCell cell = row.getCell(i);
            if(cell == null) cell = row.addNewTableCell();
            cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
            XWPFParagraph p = cell.getParagraphs().get(0);
            p.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun dataRun = p.createRun();
            dataRun.setFontFamily("Arial");
            dataRun.setFontSize(12);
            dataRun.setText(values[i]);
        }
    }
    
    private static void addSignatureSection(XWPFDocument doc){
        XWPFParagraph p = doc.createParagraph();
        p.setSpacingBefore(800);
        p.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun sRun = p.createRun();
        sRun.setFontSize(20);
        sRun.setBold(true);
        sRun.setText("  (                      )   التوقيع ");
        sRun.addBreak();
        sRun.setText("  عقيد/ محمد زكريا السيد عفيفي");
        sRun.addBreak();
        sRun.setText("  رئيس مكتب خدمة المتقاعدين");
        sRun.addBreak();
    }

    
}