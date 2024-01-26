package com.jolly.phonics.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.dom4j.DocumentException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.jolly.phonics.dto.RegisterUserRequestDto;
import com.jolly.phonics.dto.RegisterUserResponseDto;
import com.jolly.phonics.entity.User;
import com.jolly.phonics.repository.UserRepository;
import com.jolly.phonics.service.UserDetailsService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public RegisterUserResponseDto registerUser(RegisterUserRequestDto registerUserRequestDto) {
		
		String password = registerUserRequestDto.getPassword();
    	byte[] base64DecodedPassword = Base64.getDecoder().decode(password);
    	String base64DecodedPasswordStr = new String(base64DecodedPassword, StandardCharsets.UTF_8);
		
		User user = new User();
		user.setUsername(registerUserRequestDto.getEmailId());
		System.out.println("passwordEncoder :"+passwordEncoder.encode(base64DecodedPasswordStr));
		user.setPassword(passwordEncoder.encode(base64DecodedPasswordStr));
		user.setEmail(registerUserRequestDto.getEmailId());
		user.setUserFullName(registerUserRequestDto.getFirstName()+" "+registerUserRequestDto.getLastName());
		return modelMapper.map(userRepository.save(user), RegisterUserResponseDto.class);
	}
	
	public void generatePdf() {
		String pdfFilePath = "D:\\Test\\output_with_html.pdf";
        try {
        	PdfWriter pdfWriter = new PdfWriter(pdfFilePath);
        	PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        	pdfDocument.setDefaultPageSize(PageSize.A4);
        	Document document = new Document(pdfDocument);
        	
        	Resource resource = new ClassPathResource("logo.png");
    		String imagePath = resource.getFile().getAbsolutePath();
    		
    		ImageData imageData = ImageDataFactory.create(imagePath);
    		Image image = new Image(imageData);
    		
    		float x = pdfDocument.getDefaultPageSize().getWidth()/2;
    		float y = pdfDocument.getDefaultPageSize().getHeight()/2;
    		
    		image.setFontSize(10f);
    		image.setFixedPosition(x-150, y-170);
    		image.setOpacity(0.1f);
    		document.add(image);
    				
            // Add content to the document
            addContentToPdf(document);

            document.close();

			System.out.println("PDF with HTML content generated successfully at: " + pdfFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addContentToPdf(Document document) throws DocumentException, IOException {
		
		
		
		float threeCol = 190f;
		float twoCol = 285f;
		float twoCol150 = twoCol+150f;
		float[] twoColumnWidth = {twoCol150, twoCol};
		float[] threeColumnWidth = {threeCol, threeCol, threeCol};
		float[] fullWidth = {threeCol*3};
		Paragraph onesp = new Paragraph("\n");
		
		Table table = new Table(twoColumnWidth);
		table.addCell(new Cell().add("Invoice").setFontSize(20f).setBorder(Border.NO_BORDER).setBold());
		
		Table nestedTable = new Table(new float[]{twoCol/2, twoCol/2});
		nestedTable.addCell(getHeaderTextCell("Invoice No"));
		nestedTable.addCell(getHeaderCellValue("12345"));
		nestedTable.addCell(getHeaderTextCell("Invoice Date"));
		nestedTable.addCell(getHeaderCellValue(getCurrentDate()));
		
		table.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));
		
		Border gb = new SolidBorder(Color.GRAY, 2f);
		Table divider = new Table(fullWidth);
		divider.setBorder(gb);
		document.add(table);
		document.add(onesp);
		document.add(divider);
		document.add(onesp);
		
		
		Table twoColTable = new Table(twoColumnWidth);
		twoColTable.addCell(getBillingAndShippingCell("Billing Information"));
		twoColTable.addCell(getBillingAndShippingCell("Shipping Information"));
		document.add(twoColTable.setMarginBottom(12f));
		
		Table twoColTable2 = new Table(twoColumnWidth);
		twoColTable2.addCell(getCell10fLeft("Company", true));
		twoColTable2.addCell(getCell10fLeft("Name", true));
		twoColTable2.addCell(getCell10fLeft("Codeing Error", false));
		twoColTable2.addCell(getCell10fLeft("Codeing", false));
		document.add(twoColTable2);
		
		Table twoColTable3 = new Table(twoColumnWidth);
		twoColTable3.addCell(getCell10fLeft("Name", true));
		twoColTable3.addCell(getCell10fLeft("Address", true));
		twoColTable3.addCell(getCell10fLeft("Ram", false));
		twoColTable3.addCell(getCell10fLeft("DNV Society", false));
		document.add(twoColTable3);
		
		float[] oneColumnWidth = {twoCol150};
		
		Table oneColTable1 = new Table(oneColumnWidth);
		oneColTable1.addCell(getCell10fLeft("Address", true));
		oneColTable1.addCell(getCell10fLeft("DK", true));
		oneColTable1.addCell(getCell10fLeft("Email", false));
		oneColTable1.addCell(getCell10fLeft("ram@gmail.com", false));
		document.add(oneColTable1.setMarginBottom(10f));
		
		Table tableDivider2 = new Table(fullWidth);
		Border dgb = new DashedBorder(Color.GRAY, 0.5f);
		document.add(tableDivider2.setBorder(dgb));
		Paragraph productPara = new Paragraph("Products");
		document.add(productPara.setBold());
		
		Table threeColTable1 = new Table(threeColumnWidth);
		threeColTable1.setBackgroundColor(Color.BLACK, 0.7f);
		threeColTable1.addCell(new Cell().add("Description").setBold().setFontColor(Color.WHITE).setBorder(Border.NO_BORDER));
		threeColTable1.addCell(new Cell().add("Quantity").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
		threeColTable1.addCell(new Cell().add("Price").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
		document.add(threeColTable1);
		
		List<Product> productList = new ArrayList<Product>();
		productList.add(new Product("Apple", 2, 150));
		productList.add(new Product("Mango", 4, 450));
		productList.add(new Product("Banana", 2, 90));
		productList.add(new Product("Grapes", 2, 200));
		productList.add(new Product("Coconut", 2, 120));
		productList.add(new Product("Cherry", 2, 300));
		
		Table threeColTable2 = new Table(threeColumnWidth);
		float totalSum = 0f;
		for(Product product : productList) {
			float total = product.getQuantity() * product.getPricePerPiece();
			totalSum += total;
			threeColTable2.addCell(new Cell().add(product.getProductName()).setBorder(Border.NO_BORDER).setMarginLeft(10f));
			threeColTable2.addCell(new Cell().add(String.valueOf(product.getQuantity())).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
			threeColTable2.addCell(new Cell().add(String.valueOf(total)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(15f));
		}
		document.add(threeColTable2.setMarginBottom(20f));
		
		float[] oneTwo = {threeCol+125f, threeCol*2};
		Table threeColTable4 = new Table(oneTwo);
		threeColTable4.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
		threeColTable4.addCell(new Cell().add(tableDivider2).setBorder(Border.NO_BORDER));
		document.add(threeColTable4);
		
		
		Table threeColTable3 = new Table(threeColumnWidth);
		threeColTable3.addCell(new Cell().add("").setBorder(Border.NO_BORDER).setMarginLeft(10f));
		threeColTable3.addCell(new Cell().add("Total").setTextAlignment(TextAlignment.CENTER)).setBorder(Border.NO_BORDER);
		threeColTable3.addCell(new Cell().add(String.valueOf(totalSum)).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER).setMarginRight(15f);
		document.add(threeColTable3);
		
		document.add(tableDivider2);
		document.add(new Paragraph("\n"));
		document.add(divider.setBorder(new SolidBorder(Color.GRAY, 1)).setMarginBottom(15f));
		
		Table tb = new Table(fullWidth);
		tb.addCell(new Cell().add("TERMS AND CONDITIONS\n").setBold().setBorder(Border.NO_BORDER));
		
		List<String> tncList = new ArrayList<>();
		tncList.add("1. Terms and conditions");
		tncList.add("2. Terms and conditions");
		for(String tnc : tncList) {
			tb.addCell(new Cell().add(tnc).setBorder(Border.NO_BORDER));
		}
		document.add(tb);
		
		
		document.close();
    }

    private static String getCurrentDate() {
        // You can use a date formatting library or Java's SimpleDateFormat to format the date
        // For simplicity, we're using the current timestamp as a string here
        return String.valueOf(System.currentTimeMillis());
    }
    
    public Cell getHeaderTextCell(String textCellName) {
    	return new Cell().add(textCellName).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }

    public Cell getHeaderCellValue(String textValue) {
    	return new Cell().add(textValue).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
    public Cell getBillingAndShippingCell(String textValue) {
    	return new Cell().add(textValue).setFontSize(12f).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }
    public Cell getCell10fLeft(String textValue, Boolean isBold) {
    	Cell myCell = new Cell().add(textValue).setFontSize(10f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    	return isBold ? myCell.setBold() : myCell;
    }
    
}

class Product{
	private String productName;
	private int quantity;
	private float pricePerPiece;
	public Product(String productName, int quantity, float pricePerPiece) {
		this.productName = productName;
		this.quantity = quantity;
		this.pricePerPiece = pricePerPiece;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getPricePerPiece() {
		return pricePerPiece;
	}
	public void setPricePerPiece(float pricePerPiece) {
		this.pricePerPiece = pricePerPiece;
	}
	
}
