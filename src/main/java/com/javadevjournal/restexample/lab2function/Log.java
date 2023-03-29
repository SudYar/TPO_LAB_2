package com.javadevjournal.restexample.lab2function;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.IOException;
import java.io.Writer;

public class Log {

	private final Ln ln;
	private static final double LOCAL_INF = 1E13;

	public Log(Ln ln) {
		this.ln = ln;
	}

	public Log() {
		this.ln = new Ln();
	}

	public double log(double a, double b, double esp) {
		double res = ln.ln(b, esp) / ln.ln(a, esp);
		if (res > LOCAL_INF) return Double.NaN;
		return res;
	}

	public double writeResultToCSV(double a, double x, double eps, Writer out) {
		double res = log(a, x, eps);
		try (CSVPrinter printer = CSVFormat.DEFAULT.print(out)) {
			printer.printRecord(x, res);
		} catch (IOException e) {
			System.out.println("Wrong filename");
		}
		return res;
	}
}
