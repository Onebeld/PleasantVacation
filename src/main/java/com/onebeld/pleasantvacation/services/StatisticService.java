package com.onebeld.pleasantvacation.services;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.onebeld.pleasantvacation.entities.Trip;
import com.onebeld.pleasantvacation.entities.User;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * A service that provides travel statistics for a particular user.
 */
@Service
public class StatisticService {
    private final ExchangeRateService exchangeRateService;

    private final MessageSource messageSource;

    /**
     * Constructor for initializing dependencies.
     *
     * @param exchangeRateService Service for receiving currency exchange rates
     * @param messageSource       Service for receiving localized messages
     */
    public StatisticService(ExchangeRateService exchangeRateService, MessageSource messageSource) {
        this.exchangeRateService = exchangeRateService;
        this.messageSource = messageSource;
    }

    /**
     * Creates a PDF document containing travel statistics for a specific user.
     *
     * @param locale       Localization used for formatting numbers and messages
     * @param user         User for whom statistics are generated
     * @param outputStream Output stream for recording the PDF document
     * @param trips        List of trips to be included in the statistics
     * @throws IOException If an I/O error occurred while creating the PDF document
     */
    public void createStatisticPdf(Locale locale, User user, OutputStream outputStream, List<Trip> trips) throws IOException {
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(outputStream));
        Document document = new Document(pdfDocument, PageSize.A4, false);

        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);

        // Font creation
        PdfFont pdfFont = PdfFontFactory.createFont(new ClassPathResource("fonts/FreeSans.ttf").getPath(), PdfEncodings.IDENTITY_H);
        document.setFont(pdfFont);

        Style style = new Style();
        style.setFontSize(18);
        style.setBold();

        // Adding an image
        document.add(new Image(ImageDataFactory.create(new ClassPathResource("static/media/images/PleasantVacationDocument.png").getInputStream().readAllBytes())).setHorizontalAlignment(HorizontalAlignment.LEFT));

        document.add(new Paragraph(messageSource.getMessage("trip.statistics.tours", null, locale)).addStyle(style).setMarginTop(20));

        Paragraph tourmanagerParagraph = new Paragraph();
        tourmanagerParagraph.add(messageSource.getMessage("account.tourmanager", null, locale) + ": " + user.getFullName() + "\n");
        tourmanagerParagraph.add(messageSource.getMessage("trip.statistics.toursCount", null, locale) + ": " + trips.size());

        document.add(tourmanagerParagraph);

        document.add(new LineSeparator(new SolidLine()).setMargins(10, 0, 5, 0));

        long totalTicketsCount = 0;
        long totalReviewsCount = 0;
        double totalTicketsPrice = 0.0;

        for (Trip trip : trips) {
            long ticketsCount = trip.getTickets().size();
            double convertedPrice = exchangeRateService.convertCurrency(trip.getPrice());

            totalTicketsCount += ticketsCount;
            totalTicketsPrice += convertedPrice * ticketsCount;
            totalReviewsCount += trip.getReviews().size();

            document.add(new Paragraph(trip.getName()));

            Table table = new Table(UnitValue.createPercentArray(new float[]{50, 50})).useAllAvailableWidth();
            table.setMarginTop(5);
            table.addCell(messageSource.getMessage("trip.price", null, locale));
            table.addCell(numberFormat.format(convertedPrice) + " RUB");

            table.addCell(messageSource.getMessage("trip.statistics.reviews", null, locale));
            table.addCell(Integer.toString(trip.getReviews().size()));

            table.addCell(messageSource.getMessage("trip.statistics.bought", null, locale));
            table.addCell(Long.toString(ticketsCount));

            table.addCell(messageSource.getMessage("trip.statistics.total", null, locale));
            table.addCell(numberFormat.format(convertedPrice * ticketsCount) + " RUB");

            document.add(table);

            if (trip != trips.getLast())
                document.add(new LineSeparator(new DottedLine()).setMargins(15, 0, 0, 0));
        }

        document.add(new LineSeparator(new SolidLine()).setMargins(15, 0, 0, 0));

        document.add(new Paragraph(messageSource.getMessage("trip.statistics.result", null, locale)).addStyle(style));

        Paragraph totalParagraph = new Paragraph();

        totalParagraph.add(messageSource.getMessage("trip.statistics.reviews", null, locale) + ": " + totalReviewsCount + "\n");
        totalParagraph.add(messageSource.getMessage("trip.statistics.bought", null, locale) + ": " + totalTicketsCount + "\n");
        totalParagraph.add(messageSource.getMessage("trip.statistics.total", null, locale) + ": " + numberFormat.format(totalTicketsPrice) + " RUB");
        document.add(totalParagraph);

        float x = 552;
        float y = 15;

        int numberOfPages = pdfDocument.getNumberOfPages();
        for (int i = 1; i <= numberOfPages; i++) {
            Paragraph numberPageParagraph = new Paragraph(messageSource.getMessage("trip.statistics.page", null, locale) +
                    " " + i + " " + messageSource.getMessage("trip.statistics.of", null, locale) + " " + numberOfPages).setFontSize(10);

            document.showTextAligned(numberPageParagraph, x, y, i, TextAlignment.RIGHT, VerticalAlignment.BOTTOM, 0);
        }

        document.close();
    }
}
