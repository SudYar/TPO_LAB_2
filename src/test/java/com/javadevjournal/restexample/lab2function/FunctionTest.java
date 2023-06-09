package com.javadevjournal.restexample.lab2function;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

class FunctionTest {

	static double functionEps = 0.1;
	double eps = 0.2;

	static Sec secMock;
	static Cos cosMock;
	static Sin sinMock;
	static Ln lnMock;
	static Log logMock;
	static Cot cotMock;
	static Csc cscMock;

	static Reader secIn;
	static Reader cosIn;
	static Reader sinIn;
	static Reader lnIn;
	static Reader log2In;
	static Reader log10In;
	static Reader cotIn;
	static Reader cscIn;


	@BeforeAll
	static void init() throws IOException {
		secMock = Mockito.mock(Sec.class);
		cosMock = Mockito.mock(Cos.class);
		sinMock = Mockito.mock(Sin.class);
		cotMock = Mockito.mock(Cot.class);
		cscMock = Mockito.mock(Csc.class);
		lnMock = Mockito.mock(Ln.class);
		logMock = Mockito.mock(Log.class);
		secIn = new FileReader("src/main/resources/CsvFiles/Inputs/SecIn.csv");
		cosIn = new FileReader("src/main/resources/CsvFiles/Inputs/CosIn.csv");
		sinIn = new FileReader("src/main/resources/CsvFiles/Inputs/SinIn.csv");
		cotIn = new FileReader("src/main/resources/CsvFiles/Inputs/CotIn.csv");
		cscIn = new FileReader("src/main/resources/CsvFiles/Inputs/CscIn.csv");
		lnIn = new FileReader("src/main/resources/CsvFiles/Inputs/LnIn.csv");
		log2In = new FileReader("src/main/resources/CsvFiles/Inputs/Log2In.csv");
		log10In = new FileReader("src/main/resources/CsvFiles/Inputs/Log10In.csv");

		Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(secIn);
		for (CSVRecord record : records) {
			Mockito.when(secMock.sec(Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
		}
		records = CSVFormat.DEFAULT.parse(cosIn);
		for (CSVRecord record : records) {
			Mockito.when(cosMock.cos(Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
		}
		records = CSVFormat.DEFAULT.parse(sinIn);
		for (CSVRecord record : records) {
			Mockito.when(sinMock.sin(Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
		}
		records = CSVFormat.DEFAULT.parse(cotIn);
		for (CSVRecord record : records) {
			Mockito.when(cotMock.cot(Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
		}
		records = CSVFormat.DEFAULT.parse(cscIn);
		for (CSVRecord record : records) {
			Mockito.when(cscMock.csc(Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
		}
		records = CSVFormat.DEFAULT.parse(lnIn);
		for (CSVRecord record : records) {
			Mockito.when(lnMock.ln(Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
		}
		records = CSVFormat.DEFAULT.parse(log2In);
		for (CSVRecord record : records) {
			Mockito.when(logMock.log(2, Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
		}
		records = CSVFormat.DEFAULT.parse(log10In);
		for (CSVRecord record : records) {
			Mockito.when(logMock.log(10, Double.parseDouble(record.get(0)), functionEps)).thenReturn(Double.valueOf(record.get(1)));
		}
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/CsvFiles/Inputs/SystemIn.csv")
	void testSystemWithMocks(double value, double expected) {
		Function function = new Function(sinMock, cosMock, cscMock, secMock, cotMock, logMock);
		Assertions.assertEquals(expected, function.SystemSolve(value, functionEps), eps);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/CsvFiles/Inputs/SystemIn.csv")
	void testWithSin(double value, double expected) {
		Function function = new Function(new Sin(), cosMock, cscMock, secMock, cotMock, logMock);
		Assertions.assertEquals(expected, function.SystemSolve(value, functionEps), eps * 20);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/CsvFiles/Inputs/SystemIn.csv")
	void testWithCos(double value, double expected) {
		Function function = new Function(sinMock, new Cos(sinMock), cscMock, secMock, cotMock, logMock);
		Assertions.assertEquals(expected, function.SystemSolve(value, functionEps), eps);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/CsvFiles/Inputs/SystemIn.csv")
	void testWithCsc(double value, double expected) {
		Function function = new Function(sinMock, cosMock, new Csc(sinMock), secMock, cotMock, logMock);
		Assertions.assertEquals(expected, function.SystemSolve(value, functionEps), eps);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/CsvFiles/Inputs/SystemIn.csv")
	void testWithSec(double value, double expected) {
		Function function = new Function(sinMock, cosMock, cscMock, new Sec(cosMock), cotMock, logMock);
		Assertions.assertEquals(expected, function.SystemSolve(value, functionEps), eps);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/CsvFiles/Inputs/SystemIn.csv")
	void testWithCot(double value, double expected) {
		Function function = new Function(sinMock, cosMock, cscMock, secMock, new Cot(cosMock, sinMock), logMock);
		Assertions.assertEquals(expected, function.SystemSolve(value, functionEps), eps);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/CsvFiles/Inputs/SystemIn.csv")
	void testWithLog(double value, double expected) {
		Function function = new Function(sinMock, cosMock, cscMock, secMock, cotMock, new Log(lnMock));
		Assertions.assertEquals(expected, function.SystemSolve(value, functionEps), eps);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/CsvFiles/Inputs/SystemIn.csv")
	void testWithLn(double value, double expected) {
		Function function = new Function(sinMock, cosMock, cscMock, secMock, cotMock, new Log(new Ln()));
		Assertions.assertEquals(expected, function.SystemSolve(value, functionEps), eps * 20);
	}

	@ParameterizedTest
	@CsvFileSource(resources = "/CsvFiles/Inputs/SystemIn.csv")
	void testWithAll(double value, double expected) {
		Function function = new Function();
		Assertions.assertEquals(expected, function.SystemSolve(value, functionEps), eps * 40);
	}
}