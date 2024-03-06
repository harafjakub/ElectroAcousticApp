package InvoicesWindows;

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static DatabaseUse.ConnectDB.executeSelectQuery;

public class GeneratePdf {
    public static void Generate(JTable jTable) throws FileNotFoundException, SQLException {
        DefaultTableModel tableModel = (DefaultTableModel) jTable.getModel();
        int selectedRow = jTable.getSelectedRow();

        Object invoiceID = tableModel.getValueAt(selectedRow, 0);

        ResultSet resultSet = executeSelectQuery("SELECT electroacoustics_db.Invoices.Date, electroacoustics_db.Customers.Name, electroacoustics_db.Customers.Address FROM electroacoustics_db.Invoices INNER JOIN electroacoustics_db.Installations ON electroacoustics_db.Invoices.InstallationID = electroacoustics_db.Installations.InstallationID INNER JOIN electroacoustics_db.Customers ON electroacoustics_db.Installations.CustomerID = electroacoustics_db.Customers.CustomerID WHERE electroacoustics_db.Invoices.InvoiceID = "+invoiceID);
        String dateString = "", name = "", address = "";
        if (resultSet.next()) {
            dateString = resultSet.getString("Date");
            name = resultSet.getString("Name");
            address = resultSet.getString("Address");
        }
        LocalDate date = LocalDate.parse(dateString);
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        String path = "FS-"+invoiceID+"-"+date.toString().substring(0,4)+".pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);
        float threecol=190f;
        float twocol=285f;
        float twocol150=twocol+150f;
        float twocolumnWidth[]={twocol150,twocol};
        float threeColumnWidth[]={threecol,threecol,threecol};
        float fullwidth[]={threecol*3};
        Paragraph onesp = new Paragraph("\n");

        Table table = new Table(twocolumnWidth);
        table.addCell(new Cell().add("Faktura").setFontSize(20f).setBorder(Border.NO_BORDER).setBold());
        Table nestedtable = new Table(new float[]{twocol/2, twocol/2});
        // Table nestedtable = new Table(new float[]{twocol/2, twocol/2});
        nestedtable.addCell(getHeaderTextCell("Faktura nr:"));
        nestedtable.addCell(getHeaderTextCellValue("FS/"+invoiceID+"/"+date.toString().substring(0,4)));
        nestedtable.addCell(getHeaderTextCell("Data:"));
        nestedtable.addCell(getHeaderTextCellValue(formattedDate));

        table.addCell(new Cell().add(nestedtable).setBorder(Border.NO_BORDER));

        Border gb = new SolidBorder(Color.GRAY,2f);
        Table divider = new Table(fullwidth);
        divider.setBorder(gb);

        document.add(table);
        document.add(onesp);
        document.add(divider);
        document.add(onesp);

        Table twoColTable = new Table(twocolumnWidth);
        twoColTable.addCell(getBillingShippingCell("Sprzedawca"));
        twoColTable.addCell(getBillingShippingCell("Nabywca"));
        document.add(twoColTable.setMarginBottom(12f));

        Table twoColTable2 = new Table(twocolumnWidth);
        twoColTable2.addCell(getCell10fLeft("Firma", true));
        twoColTable2.addCell(getCell10fLeft("Nazwa", true));
        twoColTable2.addCell(getCell10fLeft("JH Electro-acoustics", false));
        twoColTable2.addCell(getCell10fLeft(name, false));
        document.add(twoColTable2);

        Table twoColTable3 = new Table(twocolumnWidth);
        twoColTable3.addCell(getCell10fLeft("Nazwa", true));
        twoColTable3.addCell(getCell10fLeft("Adres", true));
        twoColTable3.addCell(getCell10fLeft("Jakub Haraf", false));
        twoColTable3.addCell(getCell10fLeft(address, false));
        document.add(twoColTable3);

        float oneColumnwidth[] = {twocol150};
        Table oneColTable1 = new Table(oneColumnwidth);
        oneColTable1.addCell(getCell10fLeft("Adres", true));
        oneColTable1.addCell(getCell10fLeft("Opole 42-441 ul.Wyzwolenia 4", false));
        oneColTable1.addCell(getCell10fLeft("Email", true));
        oneColTable1.addCell(getCell10fLeft("harafjakub@gmail.com", false));
        document.add(oneColTable1.setMarginBottom(10f));


        Table tableDivider2 = new Table(fullwidth);
        Border dgb = new DashedBorder(Color.GRAY,0.5f);
        document.add(tableDivider2.setBorder(dgb));
        Paragraph productParagraph = new Paragraph("Produkty");

        document.add(productParagraph.setBold());
        Table threeColTable1 = new Table(threeColumnWidth);
        threeColTable1.setBackgroundColor(Color.BLACK, 0.7f);

        threeColTable1.addCell(new Cell().add("Opis").setBold().setFontColor(Color.WHITE).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add("Ilosc").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add("Cena").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(15f));
        document.add(threeColTable1);

        ResultSet resultSet2 = executeSelectQuery("SELECT electroacoustics_db.Orders.Quantity, electroacoustics_db.Products.Name, electroacoustics_db.Products.Price FROM electroacoustics_db.Invoices INNER JOIN electroacoustics_db.Installations ON electroacoustics_db.Invoices.InstallationID = electroacoustics_db.Installations.InstallationID INNER JOIN electroacoustics_db.Orders ON electroacoustics_db.Orders.InstallationID = electroacoustics_db.Installations.InstallationID INNER JOIN electroacoustics_db.Products ON electroacoustics_db.Products.ProductID = electroacoustics_db.Orders.ProductID WHERE electroacoustics_db.Invoices.InvoiceID = "+invoiceID);
        List<Product> productList = new ArrayList<>();
        while (resultSet2.next()) {
            String productName = resultSet2.getString("Name");
            int quantity = resultSet2.getInt("Quantity");
            float price = resultSet2.getFloat("Price");
            Product product = new Product(productName,quantity,price);
            productList.add(product);
        }

        Table threeColTable2=new Table(threeColumnWidth);
        float totalSum=getTotalSum(productList);
        for (Product product:productList)
        {
            float total=product.getQuantity()*product.getPriceperpeice();
            threeColTable2.addCell(new Cell().add(product.getPname().orElse("")).setBorder(Border.NO_BORDER)).setMarginLeft(10f);
            threeColTable2.addCell(new Cell().add(String.valueOf(product.getQuantity())).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            threeColTable2.addCell(new Cell().add(String.valueOf(total)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);
        }
        document.add(threeColTable2.setMarginBottom(20f));
        float onetwo[]={threecol+125f,threecol*2};
        Table threeColTable4=new Table(onetwo);
        threeColTable4.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        threeColTable4.addCell(new Cell().add(tableDivider2).setBorder(Border.NO_BORDER));
        document.add(threeColTable4);

        Table threeColTable3=new Table(threeColumnWidth);
        threeColTable3.addCell(new Cell().add("").setBorder(Border.NO_BORDER)).setMarginLeft(10f);
        threeColTable3.addCell(new Cell().add("Suma").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable3.addCell(new Cell().add(String.valueOf(totalSum)+" zl").setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER)).setMarginRight(15f);

        document.add(threeColTable3);
        document.add(tableDivider2);
        document.add(new Paragraph("\n"));
        document.add(divider.setBorder(new SolidBorder(Color.GRAY,1)).setMarginBottom(15f));

        document.close();
    }
    static float getTotalSum(List<Product> productList) {
        return  (float)productList.stream().mapToLong((p)-> (long) (p.getQuantity()*p.getPriceperpeice())).sum();
    }
    static Cell getHeaderTextCell(String textValue){
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }
    static Cell getHeaderTextCellValue(String textValue){
        return new Cell().add(textValue).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
    static Cell getBillingShippingCell(String textValue){
        return new Cell().add(textValue).setFontSize(12f).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
    static Cell getCell10fLeft(String textValue, Boolean isBold){
        Cell myCell = new Cell().add(textValue).setFontSize(10f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold ? myCell.setBold():myCell;
    }

}

