package com.javadevjournal.restexample.lab2function;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;

public class Csc {
	private final Sin sin;
	private static final double LOCAL_INF = 1E13;

	public Csc(Sin sin) {
		this.sin = sin;
	}

	public Csc() {
		this.sin = new Sin();
	}

	public double csc(double x, double eps) {
		double sinVal = sin.sin(x, eps);
		if (Double.isNaN(sinVal) || sinVal == 0) return Double.NaN;
		double res = 1 / sinVal;
		if (res > LOCAL_INF) return Double.NaN;
		return res;
	}

	public double writeResultToCSV(double x, double eps, Writer out) {
		double res = csc(x, eps);
		try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
			printer.printRecord(x, res);
		} catch (IOException e) {
			System.out.println("Wrong filename");
		}
		return res;
	}
}
