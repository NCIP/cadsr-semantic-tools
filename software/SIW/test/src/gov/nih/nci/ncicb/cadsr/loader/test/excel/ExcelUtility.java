/*L
 * Copyright Oracle Inc, SAIC, SAIC-F
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-semantic-tools/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.loader.test.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtility
{
    /**
     * return label of list cell , as oppoed to its int value
     * @param sheet
     * @param row
     * @param col
     * @return
     */
    public static String getLabel(HSSFSheet sheet, int row, short col)
    {
        HSSFRow hssfRow = getRow(sheet, row);

        if (hssfRow == null)
        {
            return null;
        }

        HSSFCell cell = getRow(sheet, row).getCell(col);

        if (isNull(cell))
        {
            return null;
        }
        return cell.getStringCellValue();
    }

    public static String getString(HSSFSheet sheet, int row, short col)
    {
        HSSFRow hssfRow = getRow(sheet, row);

        if (hssfRow == null)
        {
            return null;
        }

        HSSFCell cell = getRow(sheet, row).getCell(col);

        if (isNull(cell))
        {
            return null;
        }
        return cell.getStringCellValue();
    }

    public static int getInt(HSSFSheet sheet, int row, short col)
    {
        HSSFRow hssfRow = getRow(sheet, row);

        if (hssfRow == null)
        {
            return 0;
        }

        HSSFCell cell = getRow(sheet, row).getCell(col);

        if (isNull(cell))
        {
            return 0;
        }
        return (int) Math.round(cell.getNumericCellValue());
    }

    public static Integer getInteger(HSSFSheet sheet, int row, short col)
    {
        HSSFRow hssfRow = getRow(sheet, row);

        if (hssfRow == null)
        {
            return null;
        }

        HSSFCell cell = getRow(sheet, row).getCell(col);

        if (isNull(cell))
        {
            return null;
        }
        return new Integer((int) Math.round(cell.getNumericCellValue()));
    }

    public static Long getLong(HSSFSheet sheet, int row, short col)
    {
        HSSFRow hssfRow = getRow(sheet, row);

        if (hssfRow == null)
        {
            return null;
        }

        HSSFCell cell = getRow(sheet, row).getCell(col);

        if (isNull(cell))
        {
            return null;
        }
        long round = Math.round(cell.getNumericCellValue());
        return new Long(round);
    }

    public static Boolean getBoolean(HSSFSheet sheet, int row, short col)
    {
        HSSFRow hssfRow = getRow(sheet, row);

        if (hssfRow == null)
        {
            return null;
        }

        HSSFCell cell = getRow(sheet, row).getCell(col);

        if (isNull(cell))
        {
            return null;
        }
        return new Boolean(cell.getBooleanCellValue());
    }

    public static Double getDouble(HSSFSheet sheet, int row, short col)
    {
        HSSFRow hssfRow = getRow(sheet, row);

        if (hssfRow == null)
        {
            return null;
        }

        HSSFCell cell = getRow(sheet, row).getCell(col);

        if (isNull(cell))
        {
            return null;
        }
        return new Double(cell.getNumericCellValue());
    }

    public static Float getFloat(HSSFSheet sheet, int row, short col)
    {
        HSSFRow hssfRow = getRow(sheet, row);

        if (hssfRow == null)
        {
            return null;
        }

        HSSFCell cell = getRow(sheet, row).getCell(col);

        if (isNull(cell))
        {
            return null;
        }
        return new Float(cell.getNumericCellValue());
    }

    public static Date getDate(HSSFSheet sheet, int row, short col)
    {
        HSSFRow hssfRow = getRow(sheet, row);

        if (hssfRow == null)
        {
            return null;
        }

        HSSFCell cell = getRow(sheet, row).getCell(col);

        if (isNull(cell))
        {
            return null;
        }
        try
        {
            return cell.getDateCellValue();
        }
        catch (Throwable t)
        {
            t.printStackTrace();
            log.error("Unable to read date from " + row + "," + col, t);
            return null;
        }
    }

    public static Object getObject(HSSFSheet sheet, int row, short col)
    {
        HSSFRow hssfRow = getRow(sheet, row);

        if (hssfRow == null)
        {
            return null;
        }
        HSSFCell cell = getRow(sheet, row).getCell(col);

        if (cell == null)
        {
            return null;
        }
        try
        {
            String val = cell.getStringCellValue();
            if (val != null && val.equalsIgnoreCase("(null)"))
            {
                return null;
            }
        }
        catch (Exception t)
        {
        }

        int type = cell.getCellType();
        switch (type)
        {
            case HSSFCell.CELL_TYPE_BLANK:
                return "";
            case HSSFCell.CELL_TYPE_BOOLEAN:
                return new Boolean(cell.getBooleanCellValue());
            case HSSFCell.CELL_TYPE_ERROR:
                return new Byte(cell.getErrorCellValue());
            case HSSFCell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case HSSFCell.CELL_TYPE_NUMERIC:
                return new Double(cell.getNumericCellValue());
            case HSSFCell.CELL_TYPE_STRING:
                return cell.getStringCellValue();
            default :
                return null;
        }
    }

    public static HSSFRow getRow(HSSFSheet sheet, int row)
    {
        HSSFRow hssfRow = sheet.getRow(row);

        return hssfRow;
    }

    public static boolean isNull(HSSFCell cell)
    {
        return cell == null || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK;
    }

    public static boolean getIsBold(HSSFWorkbook workbook, HSSFSheet sheet, int row, short col)
    {
        HSSFCell cell = sheet.getRow(row).getCell(col);
        HSSFFont font = workbook.getFontAt(cell.getCellStyle().getFontIndex());

        return font.getBoldweight() == HSSFFont.BOLDWEIGHT_BOLD;
    }

    public static boolean getIsItalic(HSSFWorkbook workbook, HSSFSheet sheet, int row, short col)
    {
        HSSFCell cell = sheet.getRow(row).getCell(col);
        HSSFFont font = workbook.getFontAt(cell.getCellStyle().getFontIndex());

        return font.getItalic();
    }

    public static short getFontColor(HSSFWorkbook workbook, HSSFSheet sheet, int row, short col)
    {
        HSSFCell cell = sheet.getRow(row).getCell(col);
        HSSFFont font = workbook.getFontAt(cell.getCellStyle().getFontIndex());

        return font.getColor();
    }

    public static Double getFormulaResultAsDouble(HSSFCell cell)
    {
        String val = cell.getStringCellValue();

        throw new RuntimeException("FormaulaString:" + val);
    }

    public static Integer getFormulaResultAsInteger(HSSFCell cell)
    {
        return null;
    }

    public static HSSFWorkbook createWorkbook(InputStream fileStream)
            throws IOException
    {
        HSSFWorkbook wb = new HSSFWorkbook(fileStream);

        return wb;
    }

    public static HSSFWorkbook createWorkbook(String xlSheet)
            throws IOException
    {
        FileInputStream fileStream = new FileInputStream(xlSheet);
        HSSFWorkbook wb = new HSSFWorkbook(fileStream);

        return wb;
    }

    public static HSSFSheet createHSSFSheet(HSSFWorkbook wb, String sheetname)
    {
        HSSFSheet hssfsheet = wb.getSheet(sheetname);

        return hssfsheet;
    }

    /////////////////////
    ////write functions
    ////////////////////
    public static HSSFWorkbook createWorkbook()
    {
        return new HSSFWorkbook();
    }

    public static void writeExcelFile(String filename, HSSFWorkbook book) throws FileNotFoundException, IOException
    {
        if (book == null)
        {
            return;
        }
        book.write(new FileOutputStream(new File(filename)));
    }


    public static HSSFRow createRow(HSSFSheet sheet, int row)
    {
        return sheet.createRow(row);
    }

    public static HSSFCell createCell(HSSFSheet sheet, int row, short col)
    {
        if (sheet == null)
        {
            return null;
        }
        HSSFRow rowc = sheet.getRow(row);
        if (rowc == null)
        {
            rowc = createRow(sheet, row);
        }
        if (rowc != null)
        {
            HSSFCell cell = rowc.getCell(col);
            if (cell != null)
            {
                return cell;
            }
            return rowc.createCell(col);
        }
        return null;
    }

    public static void setCellValue(HSSFCell cell, boolean value)
    {
        if (cell == null)
        {
            return;
        }
        cell.setCellValue(value);
    }


    public static void setCellValue(HSSFCell cell, java.util.Calendar value)
    {
        if (cell == null)
        {
            return;
        }
        cell.setCellValue(value);
    }


    public static void setCellValue(HSSFCell cell, java.util.Date value)
    {
        if (cell == null)
        {
            return;
        }
        cell.setCellValue(value);
    }


    public static void setCellValue(HSSFCell cell, double value)
    {
        if (cell == null)
        {
            return;
        }
        cell.setCellValue(value);
    }


    public static void setCellValue(HSSFCell cell, java.lang.String value)
    {
        if (cell == null)
        {
            return;
        }
        cell.setCellValue(value);
    }


    public static void setCellValue(HSSFSheet sheet, int row, short col, boolean value)
    {
        HSSFCell cell = createCell(sheet, row, col);
        setCellValue(cell, value);
    }


    public static void setCellValue(HSSFSheet sheet, int row, short col, java.util.Calendar value)
    {
        HSSFCell cell = createCell(sheet, row, col);
        setCellValue(cell, value);
    }


    public static void setCellValue(HSSFSheet sheet, int row, short col, java.util.Date value)
    {
        HSSFCell cell = createCell(sheet, row, col);
        setCellValue(cell, value);
    }

    public static void setCellValue(HSSFSheet sheet, int row, short col, double value)
    {
        HSSFCell cell = createCell(sheet, row, col);
        setCellValue(cell, value);
    }

    public static void setCellValue(HSSFSheet sheet, int row, short col, java.lang.String value)
    {
        HSSFCell cell = createCell(sheet, row, col);
        setCellValue(cell, value);
    }


    private static Log log = LogFactory.getLog(ExcelUtility.class);
}
