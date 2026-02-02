package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;

public class PoiExcelLogger implements AutoCloseable {

    private final Workbook wb;
    private final Path outputPath;
    private final boolean autoFlush;
    private final ConcurrentHashMap<String, Sheet> sheets = new ConcurrentHashMap<>();
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS z");

    public PoiExcelLogger(String outputDir, String workbookPrefix, boolean autoFlush) {
        try {
            Files.createDirectories(Path.of(outputDir));
            String fileName = workbookPrefix + "_" + System.currentTimeMillis() + ".xlsx";
            this.outputPath = Path.of(outputDir, fileName);
            this.wb = new XSSFWorkbook();
            this.autoFlush = autoFlush;
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Excel logger", e);
        }
    }

    private void ensureSheet(String sheetName) {
        sheets.computeIfAbsent(sheetName, name -> {
            Sheet sh = wb.createSheet(name);
            Row header = sh.createRow(0);
            header.createCell(0).setCellValue("Timestamp");
            header.createCell(1).setCellValue("TestName");
            header.createCell(2).setCellValue("Scenario");
            header.createCell(3).setCellValue("Step");
            header.createCell(4).setCellValue("Level");
            header.createCell(5).setCellValue("Message");
            return sh;
        });
    }

    public synchronized void log(String sheetName, String testName, String scenario, String step, String level, String message) {
        ensureSheet(sheetName);
        Sheet sh = sheets.get(sheetName);
        int nextRow = sh.getLastRowNum() + 1;
        Row row = sh.createRow(nextRow);
        int c = 0;
        row.createCell(c++).setCellValue(ZonedDateTime.now().format(dtf));
        row.createCell(c++).setCellValue(nz(testName));
        row.createCell(c++).setCellValue(nz(scenario));
        row.createCell(c++).setCellValue(nz(step));
        row.createCell(c++).setCellValue(level);
        row.createCell(c++).setCellValue(nz(message));

        if (autoFlush) flush();
    }

    private String nz(String s) {
        return s == null ? "" : s;
    }

    public synchronized void flush() {
        try (OutputStream os = Files.newOutputStream(outputPath)) {
            wb.write(os);
        } catch (Exception e) {
            System.err.println("Excel flush failed: " + e.getMessage());
        }
    }

    @Override
    public synchronized void close() {
        try {
            wb.close();
        } catch (Exception ignored) {}
    }
}
