package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

/**
 * Utility helpers used across tests for reading test data and taking
 * screenshots.
 *
 * <p>
 * This class contains only static helper methods so it is not intended to be
 * instantiated.
 * </p>
 *
 * Responsibilities: - Read JSON files into lists or maps (used for test data
 * driven tests) - Read Excel (.xlsx) data into list of maps keyed by the header
 * row - Read simple properties files - Take screenshots using a provided
 * WebDriver - Read data from a PostgreSQL database table
 *
 * Notes and assumptions: - File locations are resolved relative to the project
 * working directory (System.getProperty("user.dir") ). - JSON parsing uses
 * Jackson (ObjectMapper). The project must provide the appropriate Jackson
 * artifact on the classpath. - Database access assumes PostgreSQL JDBC URL and
 * credentials are supplied from a properties file named
 * "DBGlobalProperties.properties" placed under the project's resources
 * directory.
 */
public class TestUtils {

	// Base directory for resolving relative resource paths (project root)
	public static final String userDir = System.getProperty("user.dir");

	/**
	 * Read a JSON file in the resources folder and parse it as a List of maps.
	 *
	 * @param fileName name of the JSON file (without ".json") located under
	 *                 "resources" directory
	 * @return parsed content as List<HashMap<String,Object>>
	 * @throws IOException if the file cannot be read or parsed
	 */
	public static Object[][] getDataFromJsonIntoObject(String fileName) throws IOException {
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		String filePath = userDir + File.separator + "resources" + File.separator + fileName + ".json";

		// Read the file content as UTF-8 text
		File jsonFile = new File(filePath);
		String jsonContent = FileUtils.readFileToString(jsonFile, StandardCharsets.UTF_8);

		ObjectMapper mapper = new ObjectMapper();

		// Convert JSON array into a Java List of HashMaps
		data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, Object>>>() {
		});
		
		int i = 0;
		Object [][] object = new Object[data.size()][1];
		for(HashMap<String,Object> map : data) {
			object[i++][0] = map;
		}

		return object;
	}

	/**
	 * Read an Excel (.xlsx) file and convert rows to a List of maps. Each map
	 * represents a row where keys come from the first/header row and values from
	 * the current row.
	 *
	 * @param fileName  name of the Excel file (without ".xlsx") under "resources"
	 * @param sheetName name of the sheet to read (case-insensitive match)
	 * @return list of rows as maps (String->String)
	 * @throws InvalidFormatException if the workbook format is not valid
	 * @throws IOException            if file I/O fails
	 */
	public static List<HashMap<String, String>> getDataFromExcel(String fileName, String sheetName)
			throws InvalidFormatException, IOException {

		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		String filePath = userDir + File.separator + "resources" + File.separator + fileName + ".xlsx";
		File file = new File(filePath);

		// Fail fast if file is not found — caller will see an assertion failure
		Assert.assertTrue(file.exists());

		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = null;

		// Locate the requested sheet by name (case-insensitive)
		int numberOfSheets = workbook.getNumberOfSheets();
		boolean sheetFound = false;
		for (int i = 0; i < numberOfSheets; i++) {
			if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
				sheet = workbook.getSheetAt(i);
				sheetFound = true;
				break;
			}
		}

		// Ensure the requested sheet exists
		Assert.assertTrue(sheetFound);

		HashMap<String, String> mapData = new HashMap<String, String>();
		DataFormatter formatter = new DataFormatter();

		// Note: getLastRowNum() returns the index of the last row (0-based).
		int numberOfRows = sheet.getLastRowNum();
		Row firstRow = sheet.getRow(0); // header row

		// Iterate over each data row (skip header row at index 0)
		for (int i = 1; i <= numberOfRows; i++) {
			Row currentRow = sheet.getRow(i);

			// Defensive: skip if the entire row is null (possible in sparse sheets)
			if (currentRow == null) {
				continue;
			}

			int numberOfCells = currentRow.getLastCellNum();

			// For each row we build a new map
			for (int k = 1; k <= numberOfCells; k++) {
				String key = formatter.formatCellValue(firstRow.getCell(k));
				String value = formatter.formatCellValue(currentRow.getCell(k));
				mapData.put(key, value);
			}
			data.add(mapData);
		}
		return data;
	}

	/**
	 * Read a properties file under resources and return a specific property's
	 * value.
	 *
	 * @param fileName     name of the .properties file (without extension)
	 * @param propertyName key of the property to read
	 * @return property value or null if the key is missing
	 * @throws IOException if the properties file cannot be opened
	 */
	public static String getDataFromProperties(String fileName, String propertyName) throws IOException {
		Properties property = new Properties();
		String filePath = userDir + File.separator + "resources" + File.separator + fileName + ".properties";
		FileInputStream fis = new FileInputStream(new File(filePath));
		property.load(fis);
		return property.getProperty(propertyName);
	}

	/**
	 * Take a screenshot using the provided WebDriver and save it to the project
	 * screenshots directory.
	 *
	 * @param driver   active WebDriver instance that supports screenshots
	 * @param testName base name to use for the screenshot file (a .png will be
	 *                 appended)
	 * @return absolute path to the saved screenshot file
	 * @throws IOException if writing the file fails
	 */
	public static String getScreenshotAt(WebDriver driver, String testName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		String screenshotFilePath = userDir + File.separator + "screenshots" + File.separator + testName + ".png";

		// Capture the screenshot and copy to destination
		File source = ts.getScreenshotAs(OutputType.FILE);
		File destination = new File(screenshotFilePath);
		FileUtils.copyFile(source, destination);
		return screenshotFilePath;
	}

	/**
	 * Read a JSON file that represents a simple property map and return the value
	 * for the provided key.
	 *
	 * @param fileName name of the JSON file under resources (without .json)
	 * @param key      key to lookup in the JSON root object
	 * @return value associated with the key or null if missing
	 * @throws IOException if reading/parsing the file fails
	 */
	public static String getPropertyDataFromJson(String fileName, String key) throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		String filePath = userDir + File.separator + "resources" + File.separator + fileName + ".json";
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);

		ObjectMapper mapper = new ObjectMapper();

		map = mapper.readValue(jsonContent, new TypeReference<HashMap<String, String>>() {
		});

		return map.get(key);

	}

	/**
	 * Query a PostgreSQL database table and return the result as a List of maps
	 * where each map represents a row (columnName -> value).
	 *
	 * Notes: - Database connection parameters are read from
	 * DBGlobalProperties.properties under resources (dbHost, dbPort, dbUser,
	 * dbPassword). - The returned list contains one map object per row. The current
	 * implementation reuses the same map instance across rows which can lead to all
	 * list entries referencing the same map — this mirrors the original behavior
	 * but should be changed if distinct maps per row are required.
	 *
	 * @param dbName  PostgreSQL database name
	 * @param dbTable table to query (no schema qualification is applied)
	 * @return list of rows as maps (columnName -> value)
	 * @throws IOException  when properties file cannot be read
	 * @throws SQLException when JDBC operations fail
	 */
	public static List<HashMap<String, String>> getDataFromDB(String dbName, String dbTable)
			throws IOException, SQLException {
		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> mapData = new HashMap<String, String>();

		// Read DB connection parameters from properties file
		String dbHost = getDataFromProperties("DBGlobalProperties", "dbHost");
		String dbPort = getDataFromProperties("DBGlobalProperties", "dbPort");
		String dbUser = getDataFromProperties("DBGlobalProperties", "dbUser");
		String dbPassword = getDataFromProperties("DBGlobalProperties", "dbPassword");

		// Build a PostgreSQL JDBC URL.
		String dbURL = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;

		String query = "select * from " + dbTable;

		Connection connection = DriverManager.getConnection(dbURL, dbUser, dbPassword);
		Statement statement = connection.createStatement();
		ResultSet result = statement.executeQuery(query);

		// Collect column names from result metadata
		List<String> columnNames = new ArrayList<String>();
		int columnCount = result.getMetaData().getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			columnNames.add(result.getMetaData().getColumnName(i));
		}

		// Iterate over rows and build maps
		while (result.next()) {
			for (String key : columnNames) {
				mapData.put(key, result.getString(key));
			}
			data.add(mapData);
		}

		result.close();
		statement.close();
		connection.close();

		return data;
	}

}