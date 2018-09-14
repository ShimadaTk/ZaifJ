package test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import Zaif.ZaifData;

/**
 *
 * @author shimadatakaya
 * 
 * 
 * Zaifで、それぞれの重さをエクセルに出力するクラス
 */
public class Main_Data {

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {

    	
    	// 現在の日付を取得
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss");

        String outputFilePath = "/Users/shimadatakaya/Desktop/Zaifweight/" + dateFormat.format(date).toString() + "ZaifEx.xlsx";
        Workbook book = null;
        FileOutputStream fout = null;

        try {
            book = new SXSSFWorkbook();

            Font font = book.createFont();
            font.setFontName("ＭＳ ゴシック");
            font.setFontHeightInPoints((short) 9);

            DataFormat format = book.createDataFormat();

            //ヘッダ文字列用のスタイル
            CellStyle style_header = book.createCellStyle();
            style_header.setBorderBottom(BorderStyle.THIN);
            Main_Data.setBorder(style_header, BorderStyle.THIN);
            style_header.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIGHT_CORNFLOWER_BLUE.getIndex());
            style_header.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            style_header.setVerticalAlignment(VerticalAlignment.TOP);
            style_header.setFont(font);

            //文字列用のスタイル
            CellStyle style_string = book.createCellStyle();
            Main_Data.setBorder(style_string, BorderStyle.THIN);
            style_string.setVerticalAlignment(VerticalAlignment.TOP);
            style_string.setFont(font);

            //整数用のスタイル(BTC→JPY)
            CellStyle style_int = book.createCellStyle();
            Main_Data.setBorder(style_int, BorderStyle.THIN);
            style_int.setDataFormat(format.getFormat("0"));
            style_int.setVerticalAlignment(VerticalAlignment.TOP);
            style_int.setFont(font);
            
            //小数用のスタイル(BTC→MONA)
            CellStyle style_BTCMONA = book.createCellStyle();
            Main_Data.setBorder(style_BTCMONA, BorderStyle.THIN);
            style_BTCMONA.setDataFormat(format.getFormat("0.000000000000"));
            style_BTCMONA.setVerticalAlignment(VerticalAlignment.TOP);
            style_BTCMONA.setFont(font);

            //小数用のスタイル(BTC→XEM)
            CellStyle style_BTCXEM = book.createCellStyle();
            Main_Data.setBorder(style_BTCXEM, BorderStyle.THIN);
            style_BTCXEM.setDataFormat(format.getFormat("0.00000000000"));
            style_BTCXEM.setVerticalAlignment(VerticalAlignment.TOP);
            style_BTCXEM.setFont(font);
            
            //小数用のスタイル(JPY→%)
            CellStyle style_JPY = book.createCellStyle();
            Main_Data.setBorder(style_JPY, BorderStyle.THIN);
            style_JPY.setDataFormat(format.getFormat("0.00000000000000000"));
            style_JPY.setVerticalAlignment(VerticalAlignment.TOP);
            style_JPY.setFont(font);

            //小数用のスタイル(MONA→BTC)
            CellStyle style_MONABTC = book.createCellStyle();
            Main_Data.setBorder(style_MONABTC, BorderStyle.THIN);
            style_MONABTC.setDataFormat(format.getFormat("0.000000000"));
            style_MONABTC.setVerticalAlignment(VerticalAlignment.TOP);
            style_MONABTC.setFont(font);
            
          //小数用のスタイル(MONA→JPY)
            CellStyle style_MONAJPY = book.createCellStyle();
            Main_Data.setBorder(style_MONAJPY, BorderStyle.THIN);
            style_MONAJPY.setDataFormat(format.getFormat("0.00"));
            style_MONAJPY.setVerticalAlignment(VerticalAlignment.TOP);
            style_MONAJPY.setFont(font);
            
          //小数用のスタイル(XEM→BTC)
            CellStyle style_XEMBTC = book.createCellStyle();
            Main_Data.setBorder(style_XEMBTC, BorderStyle.THIN);
            style_XEMBTC.setDataFormat(format.getFormat("0.000000000"));
            style_XEMBTC.setVerticalAlignment(VerticalAlignment.TOP);
            style_XEMBTC.setFont(font);
            
          //小数用のスタイル(XEM→JPY)
            CellStyle style_XEMJPY = book.createCellStyle();
            Main_Data.setBorder(style_XEMJPY, BorderStyle.THIN);
            style_XEMJPY.setDataFormat(format.getFormat("0.00000"));
            style_XEMJPY.setVerticalAlignment(VerticalAlignment.TOP);
            style_XEMJPY.setFont(font);

            //日時表示用のスタイル
            CellStyle style_datetime = book.createCellStyle();
            Main_Data.setBorder(style_datetime, BorderStyle.THIN);
            style_datetime.setDataFormat(format.getFormat("yyyy/mm/dd hh:mm:ss"));
            style_datetime.setVerticalAlignment(VerticalAlignment.TOP);
            style_datetime.setFont(font);
            
          //日時表示用のスタイル(秒)
            CellStyle style_datetime_ss = book.createCellStyle();
            Main_Data.setBorder(style_datetime_ss, BorderStyle.THIN);
            style_datetime_ss.setDataFormat(format.getFormat("mm:ss"));
            style_datetime_ss.setVerticalAlignment(VerticalAlignment.TOP);
            style_datetime_ss.setFont(font);

            Row row;
            int rowNumber;
            Cell cell;
            int colNumber;

            //シートの作成(3シート作ってみる)
            Sheet sheet;

            for (int i = 0; i < 1; i++) {
                sheet = book.createSheet();
                if (sheet instanceof SXSSFSheet) {
                    ((SXSSFSheet) sheet).trackAllColumnsForAutoSizing();
                }

                //シート名称の設定
                book.setSheetName(i, "シート" + (i + 1));

                //ヘッダ行の作成
                rowNumber = 0;
                colNumber = 0;
                row = sheet.createRow(rowNumber);
                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("更新時間");
                
                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("更新時間(秒)");
                
                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("更新回数"); 
                
                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("BTC→JPY");

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("BTC→MONA");

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("BTC→XEM");

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("JPY→BTC");

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("JPY→MONA");

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("JPY→XEM");

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("MONA→BTC");

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("MONA→JPY");

                cell = row.createCell(colNumber++);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("XEM→BTC");
                
                cell = row.createCell(colNumber);
                cell.setCellStyle(style_header);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("XEM→JPY");

                //ウィンドウ枠の固定
                sheet.createFreezePane(1, 1);

                //ヘッダ行にオートフィルタの設定
                sheet.setAutoFilter(new CellRangeAddress(0, 0, 0, colNumber));

                //列幅の自動調整
                for (int j = 0; j <= colNumber; j++) {
                    sheet.autoSizeColumn(j, true);
                }

                ZaifData data = new ZaifData();
                //データ行の生成(一日分のデータを作ってみる)
                for (int j = 0; j < 1440; j++) {
                	data.getBTC().exchange();
                	data.getJPY().exchange();
                	data.getMONA().exchange();
                	data.getXEM().exchange();
                    rowNumber++;
                    colNumber = 0;
                    row = sheet.createRow(rowNumber);
                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_datetime);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(new Date());
                    
                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_datetime_ss);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(new Date());

                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_int);
                    cell.setCellType(CellType.NUMERIC);
                    cell.setCellValue(j + 1);

                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_int);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getBTCPrice().get("JPY"));

                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_BTCMONA);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getBTCPrice().get("MONA"));

                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_BTCXEM);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getBTCPrice().get("XEM"));

                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_JPY);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getJPYPrice().get("BTC"));

                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_JPY);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getJPYPrice().get("MONA"));

                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_JPY);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getJPYPrice().get("XEM"));

                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_MONABTC);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getMONAPrice().get("BTC"));
                    
                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_MONAJPY);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getMONAPrice().get("JPY"));
                    
                    cell = row.createCell(colNumber++);
                    cell.setCellStyle(style_XEMBTC);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getXEMPrice().get("BTC"));
                    
                    cell = row.createCell(colNumber);
                    cell.setCellStyle(style_XEMJPY);
                    cell.setCellType(CellType.STRING);
                    cell.setCellValue(data.getXEMPrice().get("JPY"));
                    
                  //ここで待ち時間を決める
                   Thread.sleep(60000);
                    
                    //列幅の自動調整
                    for (int k = 0; k <= colNumber; k++) {
                        sheet.autoSizeColumn(k, true);
                    }                    
                }
            }

            

            //ファイル出力
            fout = new FileOutputStream(outputFilePath);
            book.write(fout);
        }
        finally {
            if (fout != null) {
                try {
                    fout.close();
                }
                catch (IOException e) {
                }
            }
            if (book != null) {
                try {
                    /*
                        SXSSFWorkbookはメモリ空間を節約する代わりにテンポラリファイルを大量に生成するため、
                        不要になった段階でdisposeしてテンポラリファイルを削除する必要がある
                     */
                    ((SXSSFWorkbook) book).dispose();
                }
                catch (Exception e) {
                }
            }
        }
    }

    private static void setBorder(CellStyle style, BorderStyle border) {
        style.setBorderBottom(border);
        style.setBorderTop(border);
        style.setBorderLeft(border);
        style.setBorderRight(border);
    }

    private final static String[] LIST_ALPHA = {
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    private static String getExcelColumnString(int column) {
        String result = "";

        if (column >= 0) {
            if (column / Main_Data.LIST_ALPHA.length > 0) {
                result += getExcelColumnString(column / Main_Data.LIST_ALPHA.length - 1);
            }
            result += Main_Data.LIST_ALPHA[column % Main_Data.LIST_ALPHA.length];
        }

        return result;
    }
}